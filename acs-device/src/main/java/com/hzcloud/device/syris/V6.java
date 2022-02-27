package com.hzcloud.device.syris;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.hzcloud.device.bo.AcsDeviceResult;
import com.hzcloud.device.syris.utils.Crc16;
import com.hzcloud.device.syris.utils.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class V6 {
    private Socket v6Socket;
    private String  ip;
    private Lock lock =new ReentrantLock(); 

    private static final String SOH = "01";
    private static final String ETX = "02";
    private static final String END = "0D";
    
    public void setV6Socket(Socket socket){
        this.v6Socket = socket;
    }

    public Socket getV6Socket(){
        return v6Socket;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public String getIp(){
        return ip;
    }

    public  boolean ConnectSocket(String ip, int port) {
		try {
			this.v6Socket= new Socket();
            this.v6Socket.connect(new InetSocketAddress(ip,port),10000);
            if (this.v6Socket != null){
                this.v6Socket.setSoTimeout(10000);
                this.ip = ip;
                return true;
            }
		} catch (UnknownHostException e) {
			log.error("系统异常:",e);
            this.v6Socket = null;
            return false;
		} catch (IOException e) {
			log.error("系统异常:",e);
            this.v6Socket = null;
            return false;
		}
        return false;
	}

    public  byte[] SendAndReceiveCmd(byte[] cmd) {
        lock.lock();
        if(this.v6Socket == null){
            return null;
        } 
		try {
			OutputStream os = this.v6Socket.getOutputStream();
			os.write(cmd);
			InputStream is = this.v6Socket.getInputStream();
			byte[] readData = new byte[1024];
            while(true){
                int b = 0;
                int i = 0;
                while(( b = is.read()) != -1) {
                readData[i] = (byte)b;
                i ++;
                if(b == 0x0d) {
                    break;
                }
                }
                if (i > 12) {
                    if((readData[5] == cmd[5]) &&(readData[6] == cmd[6])){
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        buffer.put(readData, 0, i);
                        byte[] array = buffer.array();
                        return array; 
                    }
                    Arrays.fill(readData,(byte)0);
                }
            }
		} catch (IOException e) {
			log.error("系统异常:",e);
            try {
                this.v6Socket.close();
                this.v6Socket = null;
            } catch (IOException e1) {
                log.error("系统异常:",e1);
            }
            return null;
		}finally{
            lock.unlock();
        }
        
	}

    //获取时间
    public AcsDeviceResult getData(String controllerId){
        AcsDeviceResult result = new AcsDeviceResult(false, "获取时间失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("41"); //FC1:A
        sb.append("42"); //FC2:B
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        result.setCode(true);
        result.setMessage("获取时间成功");
        return result;
    }

    //重启控制器
    public AcsDeviceResult rebootController(String controllerId){
        AcsDeviceResult result = new AcsDeviceResult(false, "重启控制器失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("41"); //FC1:A
        sb.append("52"); //FC2:R
        sb.append("3030"); //"00"重启
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("重启控制器成功");
        return result;
    }

    //设置所有卡片APB状态
    public AcsDeviceResult setAPBAllCard(String  controllerId,int apb){
        AcsDeviceResult result = new AcsDeviceResult(false, "设置卡片APB状态失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("44"); //FC1:D
        sb.append("45"); //FC2:E
        sb.append("3"+apb); //"0"不检查APB "1"检查APB "2"所有卡片之APB等级为0
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("设置卡片APB状态成功");
        return result;
    }

    //读取所有门状态
    public AcsDeviceResult getDoorStatus(String  controllerId){
        AcsDeviceResult result = new AcsDeviceResult(false, "获取所有门状态失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("44"); //FC1:D
        sb.append("48"); //FC2:H
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage(new String(Arrays.copyOfRange(recdata, 7, 11)));
        return result;
    }

    //读取所有门APB状态
    public AcsDeviceResult getAPBStatus(String  controllerId){
        AcsDeviceResult result = new AcsDeviceResult(false, "获取所有门APB状态失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("44"); //FC1:D
        sb.append("49"); //FC2:I
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage(new String(Arrays.copyOfRange(recdata, 7, 11)));
        return result;
    }

    //设置所有门APB状态
    public AcsDeviceResult setAPBStatus(String  controllerId, String APBStatus){
        AcsDeviceResult result = new AcsDeviceResult(false, "设置所有门APB状态失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(APBStatus.length() != 4){
            result.setMessage("APB状态不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("44"); //FC1:D
        sb.append("49"); //FC2:I
        sb.append(utils.StringToHex(APBStatus));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("设置所有门APB状态成功");
        return result;
    }

    //获取应用群组其他设定
    public AcsDeviceResult getOtherSetting(String  controllerId, String appId){
        AcsDeviceResult result = new AcsDeviceResult(false, "获取应用群组其他设定失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(appId.length() != 2){
            result.setMessage("应用群组id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("45"); //FC1:E
        sb.append("41"); //FC2:A
        sb.append(utils.StringToHex(appId));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage(new String(Arrays.copyOfRange(recdata, 7, 26)));
        return result;
    }

    //设置应用群组其他设定
    public AcsDeviceResult setOtherSetting(String  controllerId, String appId,String reader,String out,String secPinTZ,String perPinTZ){
        AcsDeviceResult result = new AcsDeviceResult(false, "设置应用群组其他设定失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(appId.length() != 2){
            result.setMessage("应用群组id不合规");
            return result;
        }
        if(reader.length() != 2){
            result.setMessage("可通行读卡器设置不合规");
            return result;
        }
        if(out.length() != 1){
            result.setMessage("时区外直接外出设置不合规");
            return result;
        }
        if(secPinTZ.length() != 8){
            result.setMessage("安全密码检查时区设置不合规");
            return result;
        }
        if(perPinTZ.length() != 8){
            result.setMessage("个人密码时区设置不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("45"); //FC1:E
        sb.append("41"); //FC2:A
        sb.append(utils.StringToHex(appId));
        sb.append(utils.StringToHex(reader));
        sb.append(utils.StringToHex(out));
        sb.append(utils.StringToHex(secPinTZ));
        sb.append(utils.StringToHex(perPinTZ));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("设置应用群组其他设定成功");
        return result;
    }

    //获取应用群组假日设定
    public AcsDeviceResult getholidaySetting(String  controllerId, String appId){
        AcsDeviceResult result = new AcsDeviceResult(false, "获取应用群组假日设定失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(appId.length() != 2){
            result.setMessage("应用群组id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("45"); //FC1:E
        sb.append("42"); //FC2:B
        sb.append(utils.StringToHex(appId));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage(new String(Arrays.copyOfRange(recdata, 7, 79)));
        return result;
    }

    //设置应用群组假日设定
    public AcsDeviceResult setholidaySetting(String  controllerId, String appId,String TZ,String out){
        AcsDeviceResult result = new AcsDeviceResult(false, "设置应用群组其他设定失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(appId.length() != 2){
            result.setMessage("应用群组id不合规");
            return result;
        }
        if(TZ.length() != 48){
            result.setMessage("假日时区设置不合规");
            return result;
        }
        if(out.length() != 24){
            result.setMessage("时区外直接外出设置不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("45"); //FC1:E
        sb.append("42"); //FC2:B
        sb.append(utils.StringToHex(appId));
        sb.append(utils.StringToHex(TZ));
        sb.append(utils.StringToHex(out));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("设置应用群组假日设定成功");
        return result;
    }

    //获取应用群组周计划设定
    public AcsDeviceResult getweekSetting(String  controllerId, String appId){
        AcsDeviceResult result = new AcsDeviceResult(false, "获取应用群组周计划设定失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(appId.length() != 2){
            result.setMessage("应用群组id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("45"); //FC1:E
        sb.append("43"); //FC2:C
        sb.append(utils.StringToHex(appId));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage(new String(Arrays.copyOfRange(recdata, 7, 28)));
        return result;
    }

    //设置应用群组周计划设定
    public AcsDeviceResult setweekSetting(String  controllerId, String appId,String TZ,String out){
        AcsDeviceResult result = new AcsDeviceResult(false, "设置应用群组其他设定失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(appId.length() != 2){
            result.setMessage("应用群组id不合规");
            return result;
        }
        if(TZ.length() != 14){
            result.setMessage("周计划设置不合规");
            return result;
        }
        if(out.length() != 7){
            result.setMessage("时区外直接外出设置不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("45"); //FC1:E
        sb.append("43"); //FC2:C
        sb.append(utils.StringToHex(appId));
        sb.append(utils.StringToHex(TZ));
        sb.append(utils.StringToHex(out));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("设置应用群组假日设定成功");
        return result;
    }

    //获取应用群组电梯设定
    public AcsDeviceResult getelevSetting(String  controllerId, String appId){
        AcsDeviceResult result = new AcsDeviceResult(false, "获取应用群组电梯设定失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(appId.length() != 2){
            result.setMessage("应用群组id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("45"); //FC1:E
        sb.append("45"); //FC2:E
        sb.append(utils.StringToHex(appId));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage(new String(Arrays.copyOfRange(recdata, 7, 71)));
        return result;
    }

    //设置应用群组电梯设定
    public AcsDeviceResult setelevSetting(String  controllerId, String appId,String s){
        AcsDeviceResult result = new AcsDeviceResult(false, "设置应用群组其他设定失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(appId.length() != 2){
            result.setMessage("应用群组id不合规");
            return result;
        }
        if(s.length() != 64){
            result.setMessage("电梯设置不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("45"); //FC1:E
        sb.append("45"); //FC2:E
        sb.append(utils.StringToHex(appId));
        sb.append(utils.StringToHex(s));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("设置应用群组电梯设定成功");
        return result;
    }

    //设置时间段
    public AcsDeviceResult setInterval(String  controllerId, String intervalId,String HHMM,String hhmm){
        AcsDeviceResult result = new AcsDeviceResult(false, "设置时间段失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(intervalId.length() != 2){
            result.setMessage("时间段id不合规");
            return result;
        }
        if(HHMM.length() != 4){
            result.setMessage("时段开始不合规");
            return result;
        }
        if(hhmm.length() != 4){
            result.setMessage("时段结束不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("46"); //FC1:F
        sb.append("41"); //FC2:A
        sb.append(utils.StringToHex(intervalId));
        sb.append(utils.StringToHex(HHMM));
        sb.append(utils.StringToHex(hhmm));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("设置时间段成功");
        return result;
    }

    //设置时区
    public AcsDeviceResult setTimeZoom(String  controllerId, String zoomId,String TZ){
        AcsDeviceResult result = new AcsDeviceResult(false, "设置时区失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(zoomId.length() != 2){
            result.setMessage("时区id不合规");
            return result;
        }
        if(TZ.length() != 6){
            result.setMessage("时区设置不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("46"); //FC1:F
        sb.append("44"); //FC2:D
        sb.append(utils.StringToHex(zoomId));
        sb.append(utils.StringToHex(TZ));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("设置时区成功");
        return result;
    }

    //获取假日
    public AcsDeviceResult getHoliday(String  controllerId, String month){
        AcsDeviceResult result = new AcsDeviceResult(false, "获取假日失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(month.length() != 2){
            result.setMessage("月份不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("47"); //FC1:G
        sb.append("41"); //FC2:A
        sb.append(utils.StringToHex(month));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage(new String(Arrays.copyOfRange(recdata, 7, 38)));
        return result;
    }

    //设置假日
    public AcsDeviceResult setHoliday(String  controllerId, String month,String holiday){
        AcsDeviceResult result = new AcsDeviceResult(false, "设置假日失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(month.length() != 2){
            result.setMessage("月份不合规");
            return result;
        }
        if(holiday.length() != 31){
            result.setMessage("假日设置不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("47"); //FC1:G
        sb.append("41"); //FC2:A
        sb.append(utils.StringToHex(month));
        sb.append(utils.StringToHex(holiday));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("设置假日成功");
        return result;
    }

    //添加卡片
    public AcsDeviceResult insertOneCard(String  controllerId, String cardIndex,String cardId,String appId,String type,String pin,String APB){
        AcsDeviceResult result = new AcsDeviceResult(false, "添加卡片失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(cardIndex.length() != 4){
            result.setMessage("卡片流水号不合规");
            return result;
        }
        if(cardId.length() != 16){
            result.setMessage("卡片不合规");
            return result;
        }
        if(appId.length() != 2){
            result.setMessage("应用群组不合规");
            return result;
        }
        if(type.length() != 1){
            result.setMessage("卡片类型不合规");
            return result;
        }
        if(pin.length() != 4){
            result.setMessage("个人密码不合规");
            return result;
        }
        if(APB.length() != 1){
            result.setMessage("APB不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("48"); //FC1:H
        sb.append("41"); //FC2:A
        sb.append(utils.StringToHex(cardIndex));
        sb.append(utils.StringToHex(cardId));
        sb.append(utils.StringToHex(appId));
        sb.append(utils.StringToHex(type));
        sb.append(utils.StringToHex(pin));
        sb.append(utils.StringToHex(APB));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        log.info("v6 开始下发卡片");
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        log.info("v6 接到下发卡片返回");
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("添加卡片成功");
        return result;
    }

    //查询卡片
    public AcsDeviceResult getOneCard(String  controllerId, String cardIndex){
        AcsDeviceResult result = new AcsDeviceResult(false, "查询卡片失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(cardIndex.length() != 4){
            result.setMessage("卡片流水号不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("48"); //FC1:H
        sb.append("42"); //FC2:B
        sb.append(utils.StringToHex(cardIndex));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        if(recdata[12] == 0x0D){
            result.setMessage("");
        }else{
            result.setMessage(new String(Arrays.copyOfRange(recdata, 7, 31)));
        }
        return result;
    }

    //使能卡片
    public AcsDeviceResult enableOneCard(String  controllerId, String cardIndex,String flag){
        AcsDeviceResult result = new AcsDeviceResult(false, "使能卡片失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(cardIndex.length() != 4){
            result.setMessage("卡片流水号不合规");
            return result;
        }
        if(flag.length() != 1){
            result.setMessage("使能标志不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("48"); //FC1:H
        sb.append("43"); //FC2:C
        sb.append(utils.StringToHex(cardIndex));
        sb.append(utils.StringToHex(flag));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("使能卡片成功");
        return result;
    }

    public AcsDeviceResult setCardNumOneCard(String controllerId, String cardIndex, String cardNum){
        AcsDeviceResult result = new AcsDeviceResult(false, "修改卡片卡号失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(cardIndex.length() != 4){
            result.setMessage("卡片流水号不合规");
            return result;
        }
        if(cardNum.length() != 16){
            result.setMessage("卡片不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("48"); //FC1:H
        sb.append("45"); //FC2:E
        sb.append(utils.StringToHex(cardIndex));
        sb.append(utils.StringToHex(cardNum));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("修改卡片卡号成功");
        return result;
    }

    //卡片设置应用群组
    public AcsDeviceResult setAppOneCard(String  controllerId, String cardIndex,String appId){
        AcsDeviceResult result = new AcsDeviceResult(false, "卡片设置应用群组失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(cardIndex.length() != 4){
            result.setMessage("卡片流水号不合规");
            return result;
        }
        if(appId.length() != 2){
            result.setMessage("应用群组id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("48"); //FC1:H
        sb.append("47"); //FC2:G
        sb.append(utils.StringToHex(cardIndex));
        sb.append(utils.StringToHex(appId));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("卡片设置应用群组成功");
        return result;
    }

    //卡片设置APB
    public AcsDeviceResult setAPBOneCard(String  controllerId, String cardIndex,String APB){
        AcsDeviceResult result = new AcsDeviceResult(false, "卡片设置APB失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(cardIndex.length() != 4){
            result.setMessage("卡片流水号不合规");
            return result;
        }
        if(APB.length() != 1){
            result.setMessage("APB不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("48"); //FC1:H
        sb.append("48"); //FC2:H
        sb.append(utils.StringToHex(cardIndex));
        sb.append(utils.StringToHex(APB));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("卡片设置APB成功");
        return result;
    }

    //卡片设置APB
    public AcsDeviceResult setAPBCheckOneCard(String  controllerId, String cardIndex,String flag){
        AcsDeviceResult result = new AcsDeviceResult(false, "卡片设置APB检查失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(cardIndex.length() != 4){
            result.setMessage("卡片流水号不合规");
            return result;
        }
        if(flag.length() != 1){
            result.setMessage("检查APB设置不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("48"); //FC1:H
        sb.append("49"); //FC2:I
        sb.append(utils.StringToHex(cardIndex));
        sb.append(utils.StringToHex(flag));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("卡片设置APB检查成功");
        return result;
    }

    //删除卡片
    public AcsDeviceResult deleteOneCard(String  controllerId, String cardIndex){
        AcsDeviceResult result = new AcsDeviceResult(false, "删除卡片失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(cardIndex.length() != 4){
            result.setMessage("卡片流水号不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("48"); //FC1:H
        sb.append("4A"); //FC2:J
        sb.append(utils.StringToHex(cardIndex));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("删除卡片成功");
        return result;
    }

    //查询记录
    public AcsDeviceResult getOneRecord(String  controllerId){
        AcsDeviceResult result = new AcsDeviceResult(false, "查询记录失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("49"); //FC1:I
        sb.append("41"); //FC2:A
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage(new String(Arrays.copyOfRange(recdata, 7, 35)));
        return result;
    }

    //删除记录
    public AcsDeviceResult deleteOneRecord(String  controllerId){
        AcsDeviceResult result = new AcsDeviceResult(false, "删除记录失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("49"); //FC1:I
        sb.append("42"); //FC2:B
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("删除记录成功");
        return result;
    }

    //查询记录总数
    public AcsDeviceResult getRecordNum(String  controllerId){
        AcsDeviceResult result = new AcsDeviceResult(false, "查询记录总数失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("49"); //FC1:I
        sb.append("44"); //FC2:D
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage(new String(Arrays.copyOfRange(recdata, 7, 11)));
        return result;
    }

    //查询记录总数
    public AcsDeviceResult getRecordAndTotal(String  controllerId){
        AcsDeviceResult result = new AcsDeviceResult(false, "查询记录总数失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("49"); //FC1:I
        sb.append("45"); //FC2:E
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        if(recdata[16] ==0x0D){
            result.setMessage(new String(Arrays.copyOfRange(recdata,7, 11)));
        }else{
            result.setMessage(new String(Arrays.copyOfRange(recdata, 7, 35)));
        }
        return result;
    }

    //查询无效卡号
    public AcsDeviceResult getCardNumNoInSystem(String  controllerId,String nn){
        AcsDeviceResult result = new AcsDeviceResult(false, "查询无效卡号失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(nn.length() != 2){
            result.setMessage("内码序号不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("49"); //FC1:I
        sb.append("49"); //FC2:I
        sb.append(utils.StringToHex(nn));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage(new String(Arrays.copyOfRange(recdata, 7, 23)));
        return result;
    }

    //查询带索引记录
    public AcsDeviceResult getOneRecordWithInex(String  controllerId){
        AcsDeviceResult result = new AcsDeviceResult(false, "查询带索引记录");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("49"); //FC1:I
        sb.append("4D"); //FC2:M
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage(new String(Arrays.copyOfRange(recdata, 7, 48)));
        return result;
    }

    //按索引删除记录
    public AcsDeviceResult deleteOneRecordWithIndx(String  controllerId,String index){
        AcsDeviceResult result = new AcsDeviceResult(false, "删除记录失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(index.length() != 7){
            result.setMessage("记录索引不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("49"); //FC1:I
        sb.append("4E"); //FC2:N
        sb.append(utils.StringToHex(index));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("删除记录成功");
        return result;
    }

    //直接开门
    public AcsDeviceResult openDoor(String  controllerId,String door,String time){
        AcsDeviceResult result = new AcsDeviceResult(false, "开门失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(door.length() != 1){
            result.setMessage("门区不合规");
            return result;
        }
        if(time.length() != 4){
            result.setMessage("时间不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("4A"); //FC1:J
        sb.append("41"); //FC2:A
        sb.append(utils.StringToHex(door));
        sb.append(utils.StringToHex(time));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("开门成功");
        return result;
    }

    //紧急开门
    public AcsDeviceResult emergencyOpen(String  controllerId,String S,String DDDD){
        AcsDeviceResult result = new AcsDeviceResult(false, "开门失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(S.length() != 1){
            result.setMessage("紧急开门设置不合规");
            return result;
        }
        if(DDDD.length() != 4){
            result.setMessage("门区不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("4A"); //FC1:J
        sb.append("42"); //FC2:B
        sb.append(utils.StringToHex(S));
        sb.append(utils.StringToHex(DDDD));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("开门成功");
        return result;
    }

    public  AcsDeviceResult remoteControl(String controllerId, String door, String S, String time){
        AcsDeviceResult result = new AcsDeviceResult(false, "远端控制失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        if(door.length() != 2){
            result.setMessage("门区不合规");
            return result;
        }
        if(S.length() != 1){
            result.setMessage("动作不合规");
            return result;
        }
        if(time.length() != 4){
            result.setMessage("时间不合规");
            return result;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("4A"); //FC1:J
        sb.append("43"); //FC2:C
        sb.append("30"); //Id:0 控制器本身
        sb.append(utils.StringToHex(door));
        sb.append(utils.StringToHex(S));
        sb.append(utils.StringToHex(time));
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("远端控制成功");
        return result;
    }

    //恢复控制
    public AcsDeviceResult resumeDoor(String  controllerId){
        AcsDeviceResult result = new AcsDeviceResult(false, "恢复控制失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("4A"); //FC1:J
        sb.append("44"); //FC2:D
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("恢复控制成功");
        return result;
    }

    //查询记录及门状态
    public AcsDeviceResult getOneRecordAndDoorStatus(String  controllerId){
        AcsDeviceResult result = new AcsDeviceResult(false, "查询记录及门状态失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("50"); //FC1:P
        sb.append("4A"); //FC2:J
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        if(recdata[34] == 0x0D){
            result.setMessage(new String(Arrays.copyOfRange(recdata, 7, 29)));
        }else{
            result.setMessage(new String(Arrays.copyOfRange(recdata, 7, 53)));
        }
        return result;
    }

    //删除记录
    public AcsDeviceResult deleteOneRecordWithDoorStatus(String  controllerId){
        AcsDeviceResult result = new AcsDeviceResult(false, "删除记录失败");
        if(controllerId.length() != 4){
            result.setMessage("控制器id不合规");
            return result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(SOH);
        sb.append(utils.StringToHex(controllerId));
        sb.append("50"); //FC1:P
        sb.append("4B"); //FC2:K
        sb.append(ETX);
        sb.append(utils.StringToHex(Crc16.getCRC3(utils.HexToByteArr(sb.toString()))));
        sb.append(END);
        byte[] recdata = SendAndReceiveCmd(utils.HexToByteArr(sb.toString()));
        if (recdata == null){
            result.setMessage("未收到控制器返回");
            return result;
        }
        if(recdata[7] == 0x0E){
            result.setMessage(utils.V6Error(recdata[8]));
            return result;
        }
        result.setCode(true);
        result.setMessage("删除记录成功");
        return result;
    }
}
