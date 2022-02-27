package com.hzcloud.device.config;


import java.util.Date;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.hzcloud.card.domain.AcsCard;
import com.hzcloud.card.mapper.AcsCardMapper;
import com.hzcloud.device.domain.AcsDeviceCon;
import com.hzcloud.device.hikvision.HCNetSDK;
import com.hzcloud.device.hikvision.HikAlarmInfo;
import com.hzcloud.device.mapper.AcsDeviceDoorMapper;
import com.hzcloud.device.service.IAcsDeviceConService;
import com.hzcloud.info.domain.AcsAlarmInfo;
import com.hzcloud.info.mapper.AcsAlarmInfoMapper;
import com.sun.jna.Pointer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FMSGCallBack_V31 implements HCNetSDK.FMSGCallBack_V31
{
	@Autowired
    private IAcsDeviceConService acsDeviceConService;

	@Autowired
	private AcsCardMapper acsCardMapper;

	@Autowired
	private AcsAlarmInfoMapper acsAlarmInfoMapper;

	@Autowired
	private AcsDeviceDoorMapper acsDeviceDoorMapper;
	//报警信息回调函数

	public boolean invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser)
	{
		AlarmDataHandle(lCommand, pAlarmer, pAlarmInfo, dwBufLen, pUser);
		return true;
	}

	public void AlarmDataHandle(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser)
    {
		String[] sIP = new String[2];
		//lCommand是传的报警类型
		switch (lCommand)
		{
		    case HCNetSDK.COMM_ALARM_ACS: //门禁主机报警信息
		        HCNetSDK.NET_DVR_ACS_ALARM_INFO strACSInfo = new HCNetSDK.NET_DVR_ACS_ALARM_INFO();
		        strACSInfo.write();
		        Pointer pACSInfo = strACSInfo.getPointer();
		        pACSInfo.write(0, pAlarmInfo.getByteArray(0, strACSInfo.size()), 0, strACSInfo.size());
		        strACSInfo.read();

		        //sAlarmType = sAlarmType + "：门禁主机报警信息，卡号："+  new String(strACSInfo.struAcsEventInfo.byCardNo).trim() + "，卡类型：" +
		        //        strACSInfo.struAcsEventInfo.byCardType + "，报警主类型：" + strACSInfo.dwMajor + "，报警次类型：" + strACSInfo.dwMinor;

		        //报警设备IP地址
		        sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
				log.info(sIP[0]);
				List<AcsDeviceCon> controllerlist = acsDeviceConService.selectConByIp(sIP[0]);
				AcsDeviceCon controller = controllerlist.get(0);
				AcsAlarmInfo alarmInfo =  new AcsAlarmInfo();
				alarmInfo.setControllerIp(sIP[0]);
				alarmInfo.setAlarmType(""+strACSInfo.dwMajor);
				alarmInfo.setAlarmDetail(HikAlarmInfo.GetAlarmDetail(strACSInfo.dwMajor, strACSInfo.dwMinor));
				alarmInfo.setAlarmTime(new Date(strACSInfo.struTime.dwYear - 1900,strACSInfo.struTime.dwMonth - 1,strACSInfo.struTime.dwDay,strACSInfo.struTime.dwHour,strACSInfo.struTime.dwMinute,strACSInfo.struTime.dwSecond));
				alarmInfo.setControllerName(controller.getControllerName());
				if(strACSInfo.struAcsEventInfo.dwDoorNo != 0){
					alarmInfo.setDoorPin((long)strACSInfo.struAcsEventInfo.dwDoorNo);
					alarmInfo.setDoorName(acsDeviceDoorMapper.getDoorNameByControllerIdAndPin(controller.getControllerId(), strACSInfo.struAcsEventInfo.dwDoorNo));
				}

				String cardNum = new String(strACSInfo.struAcsEventInfo.byCardNo).trim();
				if (!cardNum.equals("")){
					alarmInfo.setCardNumber(cardNum);
					AcsCard card = acsCardMapper.selectCardByCardNumber(cardNum);
					if(card != null) {
						alarmInfo.setUserName(card.getUserName());
					}
				}
				acsAlarmInfoMapper.insertAcsAlarmInfo(alarmInfo);

		        // if(strACSInfo.dwPicDataLen>0)
		        // {
		        //     SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		        //     String newName = sf.format(new Date());
		        //     FileOutputStream fout;
		        //     try {
		        //         String filename = ".\\pic\\"+ new String(pAlarmer.sDeviceIP).trim() +
		        //                 "_byCardNo["+ new String(strACSInfo.struAcsEventInfo.byCardNo).trim() +
		        //                 "_"+ newName + "_Acs.jpg";
		        //         fout = new FileOutputStream(filename);
		        //         //将字节写入文件
		        //         long offset = 0;
		        //         ByteBuffer buffers = strACSInfo.pPicData.getByteBuffer(offset, strACSInfo.dwPicDataLen);
		        //         byte [] bytes = new byte[strACSInfo.dwPicDataLen];
		        //         buffers.rewind();
		        //         buffers.get(bytes);
		        //         fout.write(bytes);
		        //         fout.close();
		        //     } catch (FileNotFoundException e) {
		        //         // TODO Auto-generated catch block
		        //         e.printStackTrace();
		        //     } catch (IOException e) {
		        //         // TODO Auto-generated catch block
		        //         e.printStackTrace();
		        //     }
		        // }
		        break;
		}
    }
}