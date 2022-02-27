package com.hzcloud.device.hikvision;

import java.io.UnsupportedEncodingException;

import com.hzcloud.device.AcsDeviceController;
import com.hzcloud.device.bo.AcsDeviceAppGroupSettingInfo;
import com.hzcloud.device.bo.AcsDeviceCardInfo;
import com.hzcloud.device.bo.AcsDeviceCommandInfo;
import com.hzcloud.device.bo.AcsDeviceFaceInfo;
import com.hzcloud.device.bo.AcsDeviceHolidayGroupInfo;
import com.hzcloud.device.bo.AcsDeviceHolidayInfo;
import com.hzcloud.device.bo.AcsDeviceHolidayPlanInfo;
import com.hzcloud.device.bo.AcsDeviceResult;
import com.hzcloud.device.bo.AcsDeviceTimeIntervalInfo;
import com.hzcloud.device.bo.AcsDeviceTimeZoneInfo;
import com.hzcloud.device.bo.AcsDeviceUserInfo;
import com.hzcloud.device.bo.AcsDeviceWeekTemplateInfo;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class K1T604TM  implements AcsDeviceController{

    public static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;

    private int m_lUserID = -1;//用户句柄
	private int m_lSetCardCfgHandle = -1; //下发卡长连接句柄
	private int m_lSetFaceCfgHandle = -1; //下发人脸长连接句柄

	private int m_dwState = -1; //下发卡数据状态
	private int m_dwFaceState = -1; //下发人脸数据状态
	
	private int m_iCharEncodeType = 0;//设备字符集
    private int m_lAlarmHandle = 0;

	public static HCNetSDK.FMSGCallBack_V31 callBack;//报警回调函数实现

    @Override
    public AcsDeviceResult Login(String ip, String username, String pw, int port, String identifier) {
		AcsDeviceResult result = new AcsDeviceResult(false,"登陆失败");
		if(ip == null || username == null || pw == null){
			result.setMessage("控制器参数错误");
			return result;
		}
		HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();//设备登录信息	
		
        m_strLoginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(ip.getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, ip.length());
        
        m_strLoginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(username.getBytes(), 0, m_strLoginInfo.sUserName, 0, username.length());
        
        m_strLoginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(pw.getBytes(), 0, m_strLoginInfo.sPassword, 0, pw.length());
        
        m_strLoginInfo.wPort = (short)port;        
        m_strLoginInfo.bUseAsynLogin = false; //是否异步登录：0- 否，1- 是        
        m_strLoginInfo.write();
        
        HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();//设备信息
        m_lUserID = hCNetSDK.NET_DVR_Login_V40(m_strLoginInfo, m_strDeviceInfo);
        if (m_lUserID == -1)
		{
			log.error("登录失败，错误码为"+hCNetSDK.NET_DVR_GetLastError());
			result.setMessage("登录失败，错误码为"+hCNetSDK.NET_DVR_GetLastError());
			return result;
		} 
		else
		{
			log.info("登录成功！");
			m_iCharEncodeType = m_strDeviceInfo.byCharEncodeType;
			if (callBack == null)
            {
				log.info("设置回调函数失败!");
            }else{
				Pointer pUser = null;
				if (!hCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(callBack, pUser))
				{
					log.info("设置回调函数失败!");
				}
			}
		}
		result.setCode(true);
		result.setMessage("登录成功！");
		return result;	
    }

    @Override
    public void Logout() {
        hCNetSDK.NET_DVR_Logout(m_lUserID);
    }

    @Override
    public AcsDeviceResult SetUser(AcsDeviceUserInfo userInfo) {
		AcsDeviceResult result =new AcsDeviceResult(false,"下发用户数据失败");
		if (m_lUserID < 0){
			result.setMessage("控制器未连接");
			return result;
		}
		if(userInfo.getCardNumber() == null || userInfo.getCardNumber() == ""){
			result.setMessage("用户卡号为空");
			return result;
		} 
        HCNetSDK.NET_DVR_CARD_COND struCardCond = new HCNetSDK.NET_DVR_CARD_COND();
		struCardCond.read();
		struCardCond.dwSize = struCardCond.size();
		struCardCond.dwCardNum = 1;  //下发张数
		struCardCond.write();
		Pointer ptrStruCond = struCardCond.getPointer();	
		
		m_lSetCardCfgHandle = hCNetSDK.NET_DVR_StartRemoteConfig(m_lUserID, HCNetSDK.NET_DVR_SET_CARD, ptrStruCond, struCardCond.size(),null ,null);
		if (m_lSetCardCfgHandle == -1)
		{
			log.error("建立下发卡长连接失败，错误码为"+hCNetSDK.NET_DVR_GetLastError());
			result.setMessage("建立下发卡长连接失败，错误码为"+hCNetSDK.NET_DVR_GetLastError());
			return result;
		} 
		else
		{
			log.info("建立下发卡长连接成功！");
		}
		
        HCNetSDK.NET_DVR_CARD_RECORD struCardRecord =new HCNetSDK.NET_DVR_CARD_RECORD();

		HCNetSDK.NET_DVR_CARD_STATUS struCardStatus = new HCNetSDK.NET_DVR_CARD_STATUS();
        struCardStatus.read();
        struCardStatus.dwSize = struCardStatus.size();
        struCardStatus.write();
        
        IntByReference pInt = new IntByReference(0);

		struCardRecord.read();
		struCardRecord.dwSize = struCardRecord.size();
		
		for (int j = 0; j < HCNetSDK.ACS_CARD_NO_LEN; j++)
		{
			struCardRecord.byCardNo[j] = 0;
		}
		System.arraycopy(userInfo.getCardNumber().getBytes(), 0, struCardRecord.byCardNo, 0, userInfo.getCardNumber().getBytes().length);

		struCardRecord.byCardType = 1; //普通卡
		struCardRecord.byLeaderCard = 0; //是否为首卡，0-否，1-是
		struCardRecord.byUserType = 0;
		if(userInfo.getEnable()){
			struCardRecord.byDoorRight[0] = 1; //门1有权限
		}else{
			struCardRecord.byDoorRight[0] = 0; //门1无权限
		}
		struCardRecord.wCardRightPlan[0] = userInfo.getPlanTemplateNumber().shortValue();//关联门计划模板，使用了前面配置的计划模板
		
		struCardRecord.struValid.byEnable = (byte) (userInfo.getEnable()? 1 : 0 );    //卡有效期使能，下面是卡有效期从2000-1-1 11:11:11到2030-1-1 11:11:11
		struCardRecord.struValid.struBeginTime.wYear = (short)(userInfo.getBeginTime().getYear() +1900);
		struCardRecord.struValid.struBeginTime.byMonth =(byte)(userInfo.getBeginTime().getMonth() +1);
		struCardRecord.struValid.struBeginTime.byDay = (byte)userInfo.getBeginTime().getDate();
		struCardRecord.struValid.struBeginTime.byHour = (byte)userInfo.getBeginTime().getHours();
		struCardRecord.struValid.struBeginTime.byMinute = (byte)userInfo.getBeginTime().getMinutes();
		struCardRecord.struValid.struBeginTime.bySecond = (byte)userInfo.getBeginTime().getSeconds();
		struCardRecord.struValid.struEndTime.wYear = (short)(userInfo.getEndTime().getYear() +1900);
		struCardRecord.struValid.struEndTime.byMonth = (byte)(userInfo.getEndTime().getMonth() +1);
		struCardRecord.struValid.struEndTime.byDay = (byte)userInfo.getEndTime().getDate();
		struCardRecord.struValid.struEndTime.byHour = (byte)userInfo.getEndTime().getHours();
		struCardRecord.struValid.struEndTime.byMinute = (byte)userInfo.getEndTime().getMinutes();
		struCardRecord.struValid.struEndTime.bySecond = (byte)userInfo.getEndTime().getSeconds();	
		
		struCardRecord.dwEmployeeNo = userInfo.getUserNumber().intValue();//工号
			
		if((m_iCharEncodeType == 0) || (m_iCharEncodeType == 1) || (m_iCharEncodeType == 2) )
		{
			byte[] strCardName;
			try {
				strCardName = userInfo.getUserName().getBytes("GBK");
				for (int j = 0; j < HCNetSDK.NAME_LEN; j++)
				{
					struCardRecord.byName[j] = 0;
				}
				System.arraycopy(strCardName, 0, struCardRecord.byName, 0, strCardName.length);
			} catch (UnsupportedEncodingException e) {
				log.error("系统异常:",e);
				result.setMessage("下发卡号姓名错误");
				return result;
			}  
		}
		
		if(m_iCharEncodeType == 6)
		{
			try {
				byte[] strCardName = userInfo.getUserName().getBytes("UTF-8");  //姓名
				for (int j = 0; j < HCNetSDK.NAME_LEN; j++)
				{
					struCardRecord.byName[j] = 0;
				}
				System.arraycopy(strCardName, 0, struCardRecord.byName, 0, strCardName.length);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				log.error("系统异常:",e);
				result.setMessage("下发卡号姓名错误");
				return result;
			} 
		}
		
		struCardRecord.write();
		
		m_dwState = hCNetSDK.NET_DVR_SendWithRecvRemoteConfig(m_lSetCardCfgHandle, struCardRecord.getPointer(), struCardRecord.size(),struCardStatus.getPointer(), struCardStatus.size(),  pInt);
		struCardStatus.read();
		if(m_dwState == -1){
			log.error("NET_DVR_SendWithRecvRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
			result.setMessage("NET_DVR_SendWithRecvRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
		}            
		else if(m_dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FAILED)
		{
			log.error("下发卡失败, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 错误码：" + struCardStatus.dwErrorCode);
			result.setMessage("下发卡失败, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 错误码：" + struCardStatus.dwErrorCode);
		}
		else if(m_dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION)
		{
			log.error("下发卡异常, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 错误码：" + struCardStatus.dwErrorCode);   
			result.setMessage("下发卡异常, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 错误码：" + struCardStatus.dwErrorCode);   	
		}
		else if(m_dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS)  
		{
			if (struCardStatus.dwErrorCode != 0){
				log.error("下发卡失败,错误码" + struCardStatus.dwErrorCode + ", 卡号：" + new String(struCardStatus.byCardNo).trim());
				result.setMessage("下发卡失败,错误码" + struCardStatus.dwErrorCode + ", 卡号：" + new String(struCardStatus.byCardNo).trim()); 
			}
			else{
				log.info("下发卡成功, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 状态：" + struCardStatus.byStatus);
				result.setMessage("下发卡成功, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 状态：" + struCardStatus.byStatus); 
				result.setCode(true);
			} 
		} 
		else
		{
			log.error("其他状态：" + m_dwState);
			result.setMessage("其他状态：" + m_dwState); 
		}   
        
        if(!hCNetSDK.NET_DVR_StopRemoteConfig(m_lSetCardCfgHandle)){
        	log.error("NET_DVR_StopRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());     
			result.setMessage("NET_DVR_StopRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());    	
        }
        else{
        	log.info("NET_DVR_StopRemoteConfig接口成功");
        }
		return result;
    }

    //K1T604 下发用户和卡片为一个动作，本函数恒成功
    @Override
    public AcsDeviceResult SetCard(AcsDeviceCardInfo cardInfo) {
		if (m_lUserID < 0){
			return new AcsDeviceResult(false,"控制器未连接");
		}
        return new AcsDeviceResult(true,"下发成功");
    }

    @Override
    public AcsDeviceResult SetFace(AcsDeviceFaceInfo faceInfo) {
		AcsDeviceResult result =new AcsDeviceResult(false,"下发用户人脸失败");
		if (m_lUserID < 0){
			result.setMessage("控制器未连接");
			return result;
		}
		if(faceInfo.getCardNumber() == null || faceInfo.getCardNumber() == "" || faceInfo.getFaceFilePath() == null || faceInfo.getFaceFilePath() == ""){
			result.setMessage("无卡号或人脸信息");
			return result;
		}

		/*****************************************
		 * 从本地文件里面读取JPEG图片二进制数据
		 *****************************************/
		FileInputStream picfile = null;
		int picdataLength = 0;
		try{
			picfile = new FileInputStream(new File(faceInfo.getFaceFilePath()));
					
		}
		catch(FileNotFoundException e)
		{
			log.error("系统异常:",e);
			result.setMessage("未找到人脸文件");
			return result;
		}

		try{
			picdataLength = picfile.available();
		}
		catch(IOException e1)
		{
			log.error("系统异常:",e1);
			result.setMessage("人脸文件错误");
			return result;
		}
			if(picdataLength < 0)
		{
			log.error("input file dataSize < 0");
			result.setMessage("人脸文件大小为0");
			return result;
		}
		
        HCNetSDK.NET_DVR_FACE_COND struFaceCond = new HCNetSDK.NET_DVR_FACE_COND();
		struFaceCond.read();
		struFaceCond.dwSize = struFaceCond.size();
		struFaceCond.byCardNo = new byte[32]; //批量下发，该卡号不需要赋值
		struFaceCond.dwFaceNum = 1;  //下发个数
		struFaceCond.dwEnableReaderNo = 1;//人脸读卡器编号
		struFaceCond.write();
		Pointer ptrStruFaceCond = struFaceCond.getPointer();	
		
		m_lSetFaceCfgHandle = hCNetSDK.NET_DVR_StartRemoteConfig(m_lUserID, HCNetSDK.NET_DVR_SET_FACE, ptrStruFaceCond, struFaceCond.size(),null ,null);

		if (m_lSetFaceCfgHandle == -1)
		{
			log.error("建立下发人脸长连接失败，错误码为"+hCNetSDK.NET_DVR_GetLastError());
			result.setMessage("建立下发人脸长连接失败，错误码为"+hCNetSDK.NET_DVR_GetLastError());
			return result;
		} 
		else
		{
			log.info("建立下发人脸长连接成功！");
		}
		
		HCNetSDK.NET_DVR_FACE_STATUS struFaceStatus = new HCNetSDK.NET_DVR_FACE_STATUS();
	    struFaceStatus.read();
	    struFaceStatus.dwSize = struFaceStatus.size();
	    struFaceStatus.write();
	        
	    IntByReference pInt = new IntByReference(0);
		
		HCNetSDK.NET_DVR_FACE_RECORD struFaceRecord = new HCNetSDK.NET_DVR_FACE_RECORD();
		struFaceRecord.read();
		struFaceRecord.dwSize = struFaceRecord.size();
		
		for (int j = 0; j < HCNetSDK.ACS_CARD_NO_LEN; j++)
		{
			struFaceRecord.byCardNo[j] = 0;
		}
		for (int j = 0; j <  faceInfo.getCardNumber().length(); j++)
		{
			struFaceRecord.byCardNo[j] = faceInfo.getCardNumber().getBytes()[j];
		}
		
		HCNetSDK.BYTE_ARRAY ptrpicByte = new HCNetSDK.BYTE_ARRAY(picdataLength);
		try {
			picfile.read(ptrpicByte.byValue);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		ptrpicByte.write();
		struFaceRecord.dwFaceLen  = picdataLength;
		struFaceRecord.pFaceBuffer  = ptrpicByte.getPointer();
		
		struFaceRecord.write();
		
		m_dwFaceState = hCNetSDK.NET_DVR_SendWithRecvRemoteConfig(m_lSetFaceCfgHandle, struFaceRecord.getPointer(), struFaceRecord.size(),struFaceStatus.getPointer(), struFaceStatus.size(),  pInt);
		struFaceStatus.read();
		if(m_dwFaceState == -1){
			log.error("NET_DVR_SendWithRecvRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
			result.setMessage("NET_DVR_SendWithRecvRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
		}            
		else if(m_dwFaceState == HCNetSDK.NET_SDK_CONFIG_STATUS_FAILED)
		{
			log.error("下发人脸失败, 卡号: " + new String(struFaceStatus.byCardNo).trim() + ", 错误码：" + hCNetSDK.NET_DVR_GetLastError());
			result.setMessage("下发人脸失败, 卡号: " + new String(struFaceStatus.byCardNo).trim() + ", 错误码：" + hCNetSDK.NET_DVR_GetLastError());
		}
		else if(m_dwFaceState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION)
		{
			log.error("下发人脸异常, 卡号: " + new String(struFaceStatus.byCardNo).trim() + ", 错误码：" + hCNetSDK.NET_DVR_GetLastError());
			result.setMessage("下发人脸异常, 卡号: " + new String(struFaceStatus.byCardNo).trim() + ", 错误码：" + hCNetSDK.NET_DVR_GetLastError());
		}
		else if(m_dwFaceState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS)  
		{
			if (struFaceStatus.byRecvStatus != 1){
				log.error("下发人脸失败，人脸读卡器状态" + struFaceStatus.byRecvStatus + ", 卡号：" + new String(struFaceStatus.byCardNo).trim());
				result.setMessage("下发人脸失败，人脸读卡器状态" + struFaceStatus.byRecvStatus + ", 卡号：" + new String(struFaceStatus.byCardNo).trim());
			}
			else{
				log.info("下发人脸成功, 卡号: " + new String(struFaceStatus.byCardNo).trim() + ", 状态：" + struFaceStatus.byRecvStatus);
				result.setMessage("下发人脸成功, 卡号: " + new String(struFaceStatus.byCardNo).trim() + ", 状态：" + struFaceStatus.byRecvStatus);
				result.setCode(true);
			} 
		} 
		else
		{
			log.error("其他状态：" + m_dwFaceState);
			result.setMessage("其他状态：" + m_dwFaceState);
		}
        
        if(!hCNetSDK.NET_DVR_StopRemoteConfig(m_lSetFaceCfgHandle)){
        	log.error("NET_DVR_StopRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError()); 
			result.setMessage("NET_DVR_StopRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());       	
        }
        else{
        	log.info("NET_DVR_StopRemoteConfig接口成功");
        }
		return result;
    }

    @Override
    public AcsDeviceResult DoorControl(AcsDeviceCommandInfo cmd) {
		AcsDeviceResult result =new AcsDeviceResult(false,"门控制失败");
		if (m_lUserID == -1)
        {   
            result.setMessage("控制器未连接");
			return result;
        }
		if (cmd.getCmd() == 4){
			result.setMessage("成功");
			result.setCode(true);
			return result;
		}
		boolean ret = hCNetSDK.NET_DVR_ControlGateway(m_lUserID,cmd.getDoor(),cmd.getCmd());
		if (!ret){
			log.error("门控制失败，错误码为"+hCNetSDK.NET_DVR_GetLastError());
			result.setMessage("门控制失败，错误码为"+hCNetSDK.NET_DVR_GetLastError());
		}
		else{
			result.setMessage("门控制成功");
			result.setCode(true);
		}
        return result;
    }

    @Override
    public AcsDeviceResult SetupAlarmChan() {
		AcsDeviceResult result =new AcsDeviceResult(false,"布防失败");
        if (m_lUserID == -1)
        {   
            result.setMessage("控制器未连接");
			return result;
        }
        
        HCNetSDK.NET_DVR_SETUPALARM_PARAM m_strAlarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
        m_strAlarmInfo.dwSize=m_strAlarmInfo.size();
        m_strAlarmInfo.byLevel=1;//智能交通布防优先级：0- 一等级（高），1- 二等级（中），2- 三等级（低）
        m_strAlarmInfo.byAlarmInfoType=1;//智能交通报警信息上传类型：0- 老报警信息（NET_DVR_PLATE_RESULT），1- 新报警信息(NET_ITS_PLATE_RESULT)
        m_strAlarmInfo.byDeployType =0; //布防类型(仅针对门禁主机、人证设备)：0-客户端布防(会断网续传)，1-实时布防(只上传实时数据)
        m_strAlarmInfo.write();
        m_lAlarmHandle = hCNetSDK.NET_DVR_SetupAlarmChan_V41(m_lUserID, m_strAlarmInfo);
        if (m_lAlarmHandle == -1)
        {
            log.error("布防失败，错误号:" +  hCNetSDK.NET_DVR_GetLastError());
			result.setMessage("布防失败，错误号:" +  hCNetSDK.NET_DVR_GetLastError());
            return result;
        }
        log.info( "布防成功");
		result.setMessage("布防成功");
		result.setCode(true);
        return result;
    }

    @Override
    public AcsDeviceResult CloseAlarmChan() {
		AcsDeviceResult result =new AcsDeviceResult(true,"撤防成功");
        if (m_lAlarmHandle > -1)
        {
            if(hCNetSDK.NET_DVR_CloseAlarmChan_V30(m_lAlarmHandle))
            {
				m_lAlarmHandle = -1;
				log.info( "撤防成功");
            }else{
				log.error("撤防失败，错误号:" +  hCNetSDK.NET_DVR_GetLastError());
				result.setMessage("撤防失败，错误号:" +  hCNetSDK.NET_DVR_GetLastError());
				result.setCode(false);
			}
        }
		return result;
    }

	@Override
	public AcsDeviceResult DelUser(AcsDeviceUserInfo userInfo) {
		userInfo.setEnable(false); 
		return SetUser(userInfo);
	}

	@Override
	public AcsDeviceResult DelCard(AcsDeviceCardInfo cardInfo) {
		if (m_lUserID < 0){
			return new AcsDeviceResult(false,"控制器未连接");
		}
        return new AcsDeviceResult(true,"下发成功");
	}

	@Override
	public AcsDeviceResult CheckConnect(String controllerIndex) {
		AcsDeviceResult result = new AcsDeviceResult(false,"控制器未连接");
		if(m_lUserID < 0){
			return result;
		}
		boolean ret =  hCNetSDK.NET_DVR_RemoteControl(m_lUserID, HCNetSDK.NET_DVR_CHECK_USER_STATUS, null, 0);
		if(ret){
			result.setMessage("控制器已连接");
			result.setCode(true);
		}
		return result;
	}

	@Override
	public AcsDeviceResult SetWeekTemplate(AcsDeviceWeekTemplateInfo weekTemplate) {
		AcsDeviceResult result = new AcsDeviceResult(false,"设置周计划模块失败");
		if(m_lUserID < 0){
			result.setMessage("控制器未连接");
			return result;
		}
		
		//设置卡权限计划模板参数
        HCNetSDK.NET_DVR_PLAN_TEMPLATE_COND struPlanCond = new HCNetSDK.NET_DVR_PLAN_TEMPLATE_COND();
        struPlanCond.dwSize = struPlanCond.size();
        struPlanCond.dwPlanTemplateNumber =  weekTemplate.getWeekTemplateNumber().intValue();//计划模板编号，从1开始，最大值从门禁能力集获取
        struPlanCond.wLocalControllerID = 0;//就地控制器序号[1,64]，0表示门禁主机
        struPlanCond.write();
        
        HCNetSDK.NET_DVR_PLAN_TEMPLATE struPlanTemCfg = new HCNetSDK.NET_DVR_PLAN_TEMPLATE();
        struPlanTemCfg.dwSize = struPlanTemCfg.size();
        struPlanTemCfg.byEnable =1; //是否使能：0- 否，1- 是 
        struPlanTemCfg.dwWeekPlanNo =  weekTemplate.getWeekTemplateNumber().intValue();//周计划编号，0表示无效 
		if(weekTemplate.getHolidayGroupNumber() != null){
        	struPlanTemCfg.dwHolidayGroupNo[0] = weekTemplate.getHolidayGroupNumber().intValue();//假日组编号，按值表示，采用紧凑型排列，中间遇到0则后续无效         
		}
        byte[] byTemplateName;
		try {
			byTemplateName = weekTemplate.getWeekTemplateName().getBytes("utf-8");
			//计划模板名称 
	        for (int i = 0; i < HCNetSDK.NAME_LEN; i++)
	        {
	        	struPlanTemCfg.byTemplateName[i] = 0;
	        }
			System.arraycopy(byTemplateName, 0, struPlanTemCfg.byTemplateName, 0, byTemplateName.length);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.error("系统异常:",e);
			result.setMessage("计划模板设置错误");
			return result;
		}  
		
		struPlanTemCfg.write();
		
        IntByReference pInt = new IntByReference(0);
	    Pointer lpStatusList = pInt.getPointer();
	     
        if (false == hCNetSDK.NET_DVR_SetDeviceConfig(m_lUserID, HCNetSDK.NET_DVR_SET_CARD_RIGHT_PLAN_TEMPLATE_V50, 1, struPlanCond.getPointer(), struPlanCond.size(), lpStatusList, struPlanTemCfg.getPointer(), struPlanTemCfg.size()))
	    {
            log.error("NET_DVR_SET_CARD_RIGHT_PLAN_TEMPLATE_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
			result.setMessage("NET_DVR_SET_CARD_RIGHT_PLAN_TEMPLATE_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
            return result;
        }
        log.info("NET_DVR_SET_CARD_RIGHT_PLAN_TEMPLATE_V50成功！");

		//获取卡权限周计划参数		 
		HCNetSDK.NET_DVR_WEEK_PLAN_COND struWeekPlanCond = new HCNetSDK.NET_DVR_WEEK_PLAN_COND();
		struWeekPlanCond.dwSize = struWeekPlanCond.size();
		struWeekPlanCond.dwWeekPlanNumber  = weekTemplate.getWeekTemplateNumber().intValue();
		struWeekPlanCond.wLocalControllerID = 0;

		HCNetSDK.NET_DVR_WEEK_PLAN_CFG struWeekPlanCfg = new HCNetSDK.NET_DVR_WEEK_PLAN_CFG();

		struWeekPlanCond.write();
		struWeekPlanCfg.write();

		Pointer lpCond = struWeekPlanCond.getPointer();
	    Pointer lpInbuferCfg = struWeekPlanCfg.getPointer();

		if (false == hCNetSDK.NET_DVR_GetDeviceConfig(m_lUserID, HCNetSDK.NET_DVR_GET_CARD_RIGHT_WEEK_PLAN_V50, 1, lpCond, struWeekPlanCond.size(), lpStatusList, lpInbuferCfg, struWeekPlanCfg.size()))
		{
	         System.out.println("NET_DVR_GET_CARD_RIGHT_WEEK_PLAN_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
			 result.setMessage("NET_DVR_GET_CARD_RIGHT_WEEK_PLAN_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
	         return result;
	    }
	    struWeekPlanCfg.read();
		
		struWeekPlanCfg.byEnable = 1; //是否使能：0- 否，1- 是 

		for(int i=0;i<7;i++)
	    {
	    	 for(int j=0;j<8;j++)
	    	 {
	            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].byEnable = 0;
	            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struBeginTime.byHour = 0;
	            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struBeginTime.byMinute = 0;
	            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struBeginTime.bySecond = 0;
	            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struEndTime.byHour = 0;
	            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struEndTime.byMinute = 0;
	            struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struEndTime.bySecond = 0;
	         }
	    }

		for (int i = 0; i != 7; i++){
			for (int j = 0; j != 3; j++){
				if (!(Integer.valueOf(weekTemplate.getWeekTime()[i].getTimes()[j].getEndTime().split(":")[0]) == 0 && Integer.valueOf(weekTemplate.getWeekTime()[i].getTimes()[j].getEndTime().split(":")[1]) == 0 && Integer.valueOf(weekTemplate.getWeekTime()[i].getTimes()[j].getEndTime().split(":")[2]) == 0 )){
					//weekTemplate.getWeekTime()[i].getTimes()[j].getEndTime().getHours() ==0 && weekTemplate.getWeekTime()[i].getTimes()[j].getEndTime().getMinutes() == 0 && weekTemplate.getWeekTime()[i].getTimes()[j].getEndTime().getSeconds() == 0)){
					struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].byEnable = 1;
					struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struBeginTime.byHour = Byte.valueOf(weekTemplate.getWeekTime()[i].getTimes()[j].getBeginTime().split(":")[0]);//(byte)weekTemplate.getWeekTime()[i].getTimes()[j].getBeginTime().getHours();
					struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struBeginTime.byMinute = Byte.valueOf(weekTemplate.getWeekTime()[i].getTimes()[j].getBeginTime().split(":")[1]);//(byte)weekTemplate.getWeekTime()[i].getTimes()[j].getBeginTime().getMinutes();
					struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struBeginTime.bySecond = Byte.valueOf(weekTemplate.getWeekTime()[i].getTimes()[j].getBeginTime().split(":")[2]);//(byte)weekTemplate.getWeekTime()[i].getTimes()[j].getBeginTime().getSeconds();
					struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struEndTime.byHour = Byte.valueOf(weekTemplate.getWeekTime()[i].getTimes()[j].getEndTime().split(":")[0]);//(byte)weekTemplate.getWeekTime()[i].getTimes()[j].getEndTime().getHours();
					struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struEndTime.byMinute = Byte.valueOf(weekTemplate.getWeekTime()[i].getTimes()[j].getEndTime().split(":")[1]);//(byte)weekTemplate.getWeekTime()[i].getTimes()[j].getEndTime().getMinutes();
					struWeekPlanCfg.struPlanCfg[i].struPlanCfgDay[j].struTimeSegment.struEndTime.bySecond = Byte.valueOf(weekTemplate.getWeekTime()[i].getTimes()[j].getEndTime().split(":")[2]);//(byte)weekTemplate.getWeekTime()[i].getTimes()[j].getEndTime().getSeconds();
				}
			}
		}

		struWeekPlanCfg.write();
		
		//设置卡权限周计划参数	
		if (false == hCNetSDK.NET_DVR_SetDeviceConfig(m_lUserID, HCNetSDK.NET_DVR_SET_CARD_RIGHT_WEEK_PLAN_V50, 1, lpCond, struWeekPlanCond.size(), lpStatusList, lpInbuferCfg, struWeekPlanCfg.size()))
		{
			log.error("NET_DVR_SET_CARD_RIGHT_WEEK_PLAN_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
			result.setMessage("NET_DVR_SET_CARD_RIGHT_WEEK_PLAN_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
			return result;
		}
		log.info("NET_DVR_SET_CARD_RIGHT_WEEK_PLAN_V50成功！");  
		result.setCode(true);
		result.setMessage("下发周计划模板成功");
		return result;
	}

	@Override
	public AcsDeviceResult SetHolidayGroup(AcsDeviceHolidayGroupInfo holidayGroup) {
		AcsDeviceResult result = new AcsDeviceResult(false,"下发假日组失败");
		if(m_lUserID < 0){
			result.setMessage("控制器未连接");
			return result;
		}

		IntByReference pInt = new IntByReference(0);
	    Pointer lpStatusList = pInt.getPointer();

		HCNetSDK.NET_DVR_HOLIDAY_GROUP_COND strHolidayGroupCond = new HCNetSDK.NET_DVR_HOLIDAY_GROUP_COND();
	    strHolidayGroupCond.dwSize = strHolidayGroupCond.size();
	    strHolidayGroupCond.dwHolidayGroupNumber = holidayGroup.getHolidayGroupNumber().intValue();
	    strHolidayGroupCond.wLocalControllerID = 0;//就地控制器序号[1,64]，0表示门禁主机
	    strHolidayGroupCond.write();

	    HCNetSDK.NET_DVR_HOLIDAY_GROUP_CFG strHolidayGroupCfg = new HCNetSDK.NET_DVR_HOLIDAY_GROUP_CFG();

	    strHolidayGroupCfg.dwSize = strHolidayGroupCfg.size();
	    strHolidayGroupCfg.byEnable =1; //是否使能：0- 否，1- 是 
		for(int i = 0; i != holidayGroup.getHolidayPlanNumbers().length; i++){
	    	strHolidayGroupCfg.dwHolidayPlanNo[i] = (holidayGroup.getHolidayPlanNumbers())[i].intValue();//假日组编号，按值表示，采用紧凑型排列，中间遇到0则后续无效   
		}
        byte[] byGroupName;
		try {
			byGroupName = holidayGroup.getHolidayGroupName().getBytes("GBK");
			//计划模板名称 
	        for (int i = 0; i < HCNetSDK.NAME_LEN; i++)
	        {
	        	strHolidayGroupCfg.byGroupName[i] = 0;
	        }
			System.arraycopy(byGroupName, 0, strHolidayGroupCfg.byGroupName, 0, byGroupName.length);
		} catch (UnsupportedEncodingException e) {
			log.error("系统异常:",e);
			result.setMessage("设置假日组名称错误");
			return result;
		}  
		
		strHolidayGroupCfg.write();
	    
	    //设置卡权限假期组参数
	    if(false == hCNetSDK.NET_DVR_SetDeviceConfig(m_lUserID, HCNetSDK.NET_DVR_SET_CARD_RIGHT_HOLIDAY_GROUP_V50, 1, strHolidayGroupCond.getPointer(), strHolidayGroupCond.size(), lpStatusList, strHolidayGroupCfg.getPointer(), strHolidayGroupCfg.size()))
	    {
	    	log.error("NET_DVR_SET_CARD_RIGHT_HOLIDAY_GROUP_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
			result.setMessage("NET_DVR_SET_CARD_RIGHT_HOLIDAY_GROUP_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
	    	return result;
	    }
	    log.info("NET_DVR_SET_CARD_RIGHT_HOLIDAY_GROUP_V50成功！");
		result.setMessage("下发假日组成功");
		result.setCode(true);
		return result;
	}

	@Override
	public AcsDeviceResult SetHolidayPlan(AcsDeviceHolidayPlanInfo[] holidayPlans) {
		AcsDeviceResult result = new AcsDeviceResult(false,"下发假日计划失败");
		if(m_lUserID < 0){
			result.setMessage("控制器未连接");
			return result;
		}
		IntByReference pInt = new IntByReference(0);
	    Pointer lpStatusList = pInt.getPointer();

		for(int i = 0; i!= holidayPlans.length; i++){

			HCNetSDK.NET_DVR_HOLIDAY_PLAN_COND strHolidayPlanCond = new HCNetSDK.NET_DVR_HOLIDAY_PLAN_COND();
			strHolidayPlanCond.dwSize = strHolidayPlanCond.size();
			strHolidayPlanCond.dwHolidayPlanNumber = holidayPlans[i].getHolidayPlanNumber().intValue();
			strHolidayPlanCond.wLocalControllerID = 0;//就地控制器序号[1,64]，0表示门禁主机
			strHolidayPlanCond.write();

			HCNetSDK.NET_DVR_HOLIDAY_PLAN_CFG strHolidayPlanCfg = new HCNetSDK.NET_DVR_HOLIDAY_PLAN_CFG();
			strHolidayPlanCfg.write();

			strHolidayPlanCfg.dwSize = strHolidayPlanCfg.size();
			strHolidayPlanCfg.byEnable =1; //是否使能：0- 否，1- 是 

			strHolidayPlanCfg.struBeginDate.wYear = (short)(holidayPlans[i].getStartDate().getYear() +1900);
			strHolidayPlanCfg.struBeginDate.byMonth = (byte)(holidayPlans[i].getStartDate().getMonth() +1);
			strHolidayPlanCfg.struBeginDate.byDay = (byte)holidayPlans[i].getStartDate().getDate();
			strHolidayPlanCfg.struEndDate.wYear = (short)(holidayPlans[i].getEndDate().getYear() + 1900);
			strHolidayPlanCfg.struEndDate.byMonth = (byte)(holidayPlans[i].getEndDate().getMonth() + 1);
			strHolidayPlanCfg.struEndDate.byDay = (byte)holidayPlans[i].getEndDate().getDate();

		
	    	 for(int j=0;j<8;j++)
	    	 {
	            strHolidayPlanCfg.struPlanCfg[j].byEnable = 0;
	            strHolidayPlanCfg.struPlanCfg[j].struTimeSegment.struBeginTime.byHour = 0;
	            strHolidayPlanCfg.struPlanCfg[j].struTimeSegment.struBeginTime.byMinute = 0;
	            strHolidayPlanCfg.struPlanCfg[j].struTimeSegment.struBeginTime.bySecond = 0;
	            strHolidayPlanCfg.struPlanCfg[j].struTimeSegment.struEndTime.byHour = 0;
	            strHolidayPlanCfg.struPlanCfg[j].struTimeSegment.struEndTime.byMinute = 0;
	            strHolidayPlanCfg.struPlanCfg[j].struTimeSegment.struEndTime.bySecond = 0;
	         }
	
			for (int j = 0; j != 3; j++){
				if (!(Integer.valueOf(holidayPlans[i].getTimes()[j].getEndTime().split(":")[0]) == 0 && Integer.valueOf(holidayPlans[i].getTimes()[j].getEndTime().split(":")[1]) == 0 && Integer.valueOf(holidayPlans[i].getTimes()[j].getEndTime().split(":")[2]) == 0 )){
				//if (!(holidayPlans[i].getTimes()[j].getEndTime().getHours() == 0 && holidayPlans[i].getTimes()[j].getEndTime().getMinutes() == 0 && holidayPlans[i].getTimes()[j].getEndTime().getSeconds() == 0)){
					strHolidayPlanCfg.struPlanCfg[i].byEnable = 1;
					strHolidayPlanCfg.struPlanCfg[i].struTimeSegment.struBeginTime.byHour = Byte.valueOf(holidayPlans[i].getTimes()[j].getBeginTime().split(":")[0]);//(byte)holidayPlans[i].getTimes()[j].getBeginTime().getHours();
					strHolidayPlanCfg.struPlanCfg[i].struTimeSegment.struBeginTime.byMinute = Byte.valueOf(holidayPlans[i].getTimes()[j].getBeginTime().split(":")[1]);//(byte)holidayPlans[i].getTimes()[j].getBeginTime().getMinutes();
					strHolidayPlanCfg.struPlanCfg[i].struTimeSegment.struBeginTime.bySecond = Byte.valueOf(holidayPlans[i].getTimes()[j].getBeginTime().split(":")[2]);//(byte)holidayPlans[i].getTimes()[j].getBeginTime().getSeconds();
					strHolidayPlanCfg.struPlanCfg[i].struTimeSegment.struEndTime.byHour = Byte.valueOf(holidayPlans[i].getTimes()[j].getEndTime().split(":")[0]);//(byte)holidayPlans[i].getTimes()[j].getEndTime().getHours();
					strHolidayPlanCfg.struPlanCfg[i].struTimeSegment.struEndTime.byMinute = Byte.valueOf(holidayPlans[i].getTimes()[j].getEndTime().split(":")[1]);//(byte)holidayPlans[i].getTimes()[j].getEndTime().getMinutes();
					strHolidayPlanCfg.struPlanCfg[i].struTimeSegment.struEndTime.bySecond = Byte.valueOf(holidayPlans[i].getTimes()[j].getEndTime().split(":")[2]);//(byte)holidayPlans[i].getTimes()[j].getEndTime().getSeconds();
				}
			}

			strHolidayPlanCfg.write();
			
			//设置卡权限假期组参数
			if(false == hCNetSDK.NET_DVR_SetDeviceConfig(m_lUserID, HCNetSDK.NET_DVR_SET_CARD_RIGHT_HOLIDAY_PLAN_V50, 1, strHolidayPlanCond.getPointer(), strHolidayPlanCond.size(), lpStatusList, strHolidayPlanCfg.getPointer(), strHolidayPlanCfg.size()))
			{
				log.error("NET_DVR_SET_CARD_RIGHT_HOLIDAY_PLAN_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
				result.setMessage("NET_DVR_SET_CARD_RIGHT_HOLIDAY_PLAN_V50失败，错误号：" + hCNetSDK.NET_DVR_GetLastError());
				return result;
			}
			log.info("NET_DVR_SET_CARD_RIGHT_HOLIDAY_PLAN_V50成功！");
			result.setCode(true);
			result.setMessage("下发假日计划成功");
		}
		return result;
	}
    
	public static void setCallBack(HCNetSDK.FMSGCallBack_V31 callBack){
		K1T604TM.callBack = callBack;
	}

	@Override
	public AcsDeviceResult EnableUser(AcsDeviceUserInfo userInfo) {
		return SetUser(userInfo);
	}

	@Override
	public AcsDeviceResult EnableCard(AcsDeviceCardInfo cardInfo) {
		if (m_lUserID < 0){
			return new AcsDeviceResult(false,"控制器未连接");
		}
        return new AcsDeviceResult(true,"使能成功");
	}

	@Override
	public AcsDeviceResult CancelUser(AcsDeviceUserInfo userInfo) {
		AcsDeviceResult result = new AcsDeviceResult(false,"注销用户失败");
		if(m_lUserID < 0){
			result.setMessage("控制器未连接");
			return result;
		}
		if(userInfo.getCardNumber() == null || userInfo.getCardNumber() == ""){
			result.setMessage("卡号为空");
			return result;
		}
		HCNetSDK.NET_DVR_CARD_COND struCardCond = new HCNetSDK.NET_DVR_CARD_COND();
		struCardCond.read();
		struCardCond.dwSize = struCardCond.size();
		struCardCond.dwCardNum = 1;  //下发一张
		struCardCond.write();
		Pointer ptrStruCond = struCardCond.getPointer();	
		
		m_lSetCardCfgHandle = hCNetSDK.NET_DVR_StartRemoteConfig(m_lUserID, HCNetSDK.NET_DVR_DEL_CARD, ptrStruCond, struCardCond.size(),null ,null);
		if (m_lSetCardCfgHandle == -1)
		{
			log.error("建立删除卡长连接失败，错误码为"+hCNetSDK.NET_DVR_GetLastError());
			result.setMessage("建立删除卡长连接失败，错误码为"+hCNetSDK.NET_DVR_GetLastError());
			return result;
		} 
		else
		{
			log.info("建立删除卡长连接成功！");
		}
		
		HCNetSDK.NET_DVR_CARD_SEND_DATA struCardData = new HCNetSDK.NET_DVR_CARD_SEND_DATA();   
        HCNetSDK.NET_DVR_CARD_STATUS struCardStatus = new HCNetSDK.NET_DVR_CARD_STATUS();
        struCardStatus.read();
        struCardStatus.dwSize = struCardStatus.size();
        struCardStatus.write();
        
        IntByReference pInt = new IntByReference(0);
			
		struCardData.read();
		struCardData.dwSize = struCardData.size();
		
		for (int i = 0; i < HCNetSDK.ACS_CARD_NO_LEN; i++)
		{
			struCardData.byCardNo[i] = 0;
		}
		for (int i = 0; i <  userInfo.getCardNumber().length(); i++)
		{
			struCardData.byCardNo[i] = userInfo.getCardNumber().getBytes()[i];
		}       
		struCardData.write();
		m_dwState = hCNetSDK.NET_DVR_SendWithRecvRemoteConfig(m_lSetCardCfgHandle, struCardData.getPointer(), struCardData.size(),struCardStatus.getPointer(), struCardStatus.size(),  pInt);
		struCardStatus.read();
		if(m_dwState == -1){
			log.error("NET_DVR_SendWithRecvRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
			result.setMessage("NET_DVR_SendWithRecvRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
		}            
		else if(m_dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_FAILED)
		{
			log.error("删除卡失败, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 错误码：" + struCardStatus.dwErrorCode);
			result.setMessage("删除卡失败, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 错误码：" + struCardStatus.dwErrorCode);
		}
		else if(m_dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_EXCEPTION)
		{
			log.info("删除卡异常, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 错误码：" + struCardStatus.dwErrorCode);
			result.setMessage("删除卡异常, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 错误码：" + struCardStatus.dwErrorCode);
		}
		else if(m_dwState == HCNetSDK.NET_SDK_CONFIG_STATUS_SUCCESS)  
		{
			if (struCardStatus.dwErrorCode != 0){
				log.error("删除卡成功,但是错误码" + struCardStatus.dwErrorCode + ", 卡号：" + new String(struCardStatus.byCardNo).trim());
				result.setMessage("删除卡成功,但是错误码" + struCardStatus.dwErrorCode + ", 卡号：" + new String(struCardStatus.byCardNo).trim());
			}
			else{
				log.info("删除卡成功, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 状态：" + struCardStatus.byStatus);
				result.setMessage("删除卡成功, 卡号: " + new String(struCardStatus.byCardNo).trim() + ", 状态：" + struCardStatus.byStatus);
				result.setCode(true);
			} 
		} 

        if(!hCNetSDK.NET_DVR_StopRemoteConfig(m_lSetCardCfgHandle)){
        	log.error("NET_DVR_StopRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());  
			result.setMessage("NET_DVR_StopRemoteConfig接口调用失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());      	
        }
        else{
        	log.info("NET_DVR_StopRemoteConfig接口成功");
        }
		return result;
	}

	@Override
	public AcsDeviceResult CancelCard(AcsDeviceCardInfo userInfo) {
		if (m_lUserID < 0){
			return new AcsDeviceResult(false,"控制器未连接");
		}
        return new AcsDeviceResult(true,"注销成功");
	}

	@Override
	public AcsDeviceResult CancelFace(AcsDeviceFaceInfo faceInfo) {
		AcsDeviceResult result = new AcsDeviceResult(false,"注销人脸失败");
		if(m_lUserID < 0){
			result.setMessage("控制器未连接");
			return result;
		}

		if(faceInfo.getCardNumber() == null || faceInfo.getCardNumber() == ""){
			result.setMessage("卡号为空");
			return result;
		}
		HCNetSDK.NET_DVR_FACE_PARAM_CTRL struFaceDelCond = new HCNetSDK.NET_DVR_FACE_PARAM_CTRL();
		struFaceDelCond.dwSize = struFaceDelCond.size();
		struFaceDelCond.byMode = 0; //删除方式：0- 按卡号方式删除，1- 按读卡器删除

		struFaceDelCond.struProcessMode.setType(HCNetSDK.NET_DVR_FACE_PARAM_BYCARD.class);
		
		//需要删除人脸关联的卡号
		for (int j = 0; j < HCNetSDK.ACS_CARD_NO_LEN; j++)
		{
			struFaceDelCond.struProcessMode.struByCard.byCardNo[j] = 0;
		}
		System.arraycopy(faceInfo.getCardNumber().getBytes(), 0, struFaceDelCond.struProcessMode.struByCard.byCardNo, 0, faceInfo.getCardNumber().length());
			
		struFaceDelCond.struProcessMode.struByCard.byEnableCardReader[0] = 1; //读卡器
		struFaceDelCond.struProcessMode.struByCard.byFaceID[0] = 1; //人脸ID
		struFaceDelCond.write();
			
		Pointer ptrFaceDelCond = struFaceDelCond.getPointer();	
		
		if(hCNetSDK.NET_DVR_RemoteControl(m_lUserID, HCNetSDK.NET_DVR_DEL_FACE_PARAM_CFG, ptrFaceDelCond, struFaceDelCond.size()))
		{
			log.info("删除人脸成功！");
			result.setMessage("删除人脸成功");
			result.setCode(true);
		} 
		else
		{
			log.error("删除人脸失败，错误码为"+hCNetSDK.NET_DVR_GetLastError());
			result.setMessage("删除人脸失败，错误码为"+hCNetSDK.NET_DVR_GetLastError());
		}	
		return result;
	}

	@Override
	public AcsDeviceResult setAppgroup(AcsDeviceAppGroupSettingInfo[] settingList) {
		if (m_lUserID < 0){
			return new AcsDeviceResult(false,"控制器未连接");
		}
        return new AcsDeviceResult(true,"下发应用群组成功");
	}

	@Override
	public AcsDeviceResult setTimezone(AcsDeviceTimeZoneInfo[] timeZoneList) {
		if (m_lUserID < 0){
			return new AcsDeviceResult(false,"控制器未连接");
		}
        return new AcsDeviceResult(true,"下发时区成功");
	}

	@Override
	public AcsDeviceResult setInterval(AcsDeviceTimeIntervalInfo[] timeIntervalList) {
		if (m_lUserID < 0){
			return new AcsDeviceResult(false,"控制器未连接");
		}
        return new AcsDeviceResult(true,"下发时段成功");
	}

	@Override
	public AcsDeviceResult setHoliday(AcsDeviceHolidayInfo[] holidayList) {
		if (m_lUserID < 0){
			return new AcsDeviceResult(false,"控制器未连接");
		}
        return new AcsDeviceResult(true,"下发假日成功");
	}

	@Override
	public AcsDeviceResult getCardNumNoInSystem(String controllerIndex, String nn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcsDeviceResult getDoorStatus(String controllerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcsDeviceResult getRecordAndTotal(String controllerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcsDeviceResult setCardNumber(String controllerIndex, String cardIndex, String cardNumber) {
		// TODO Auto-generated method stub
		return null;
	}
}
