package com.hzcloud.device.enums;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzcloud.device.AcsDeviceController;
import com.hzcloud.device.bo.AcsDeviceAppGroupSettingInfo;
import com.hzcloud.device.bo.AcsDeviceCardInfo;
import com.hzcloud.device.bo.AcsDeviceFaceInfo;
import com.hzcloud.device.bo.AcsDeviceHolidayGroupInfo;
import com.hzcloud.device.bo.AcsDeviceHolidayInfo;
import com.hzcloud.device.bo.AcsDeviceHolidayPlanInfo;
import com.hzcloud.device.bo.AcsDeviceResult;
import com.hzcloud.device.bo.AcsDeviceTimeIntervalInfo;
import com.hzcloud.device.bo.AcsDeviceTimeZoneInfo;
import com.hzcloud.device.bo.AcsDeviceUserInfo;
import com.hzcloud.device.bo.AcsDeviceWeekTemplateInfo;
import com.hzcloud.device.bo.DeleteUserData;
import com.hzcloud.device.bo.IssueUserData;
import com.hzcloud.device.bo.Payload;
import com.hzcloud.device.bo.QueueResult;
import com.hzcloud.device.config.DeviceConStatus;
import com.hzcloud.group.domain.AcsHolidayPlan;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public enum ControllerCommandEnum {

    
    /**
     * 下发人员信息
     */
    ISSUED_USER_INFO(1) {
        @Override
        public QueueResult run(Payload payload) {
            log.info("下发人员信息");
            AcsDeviceResult result = new AcsDeviceResult(false,"下发人员信息失败");
            if (payload != null && payload instanceof Payload) { 
                result = issuedUserInfo(payload);    
            }
            if (result.isCode()){
                return new QueueResult("0", result.getMessage());
            }
            return new QueueResult("1",result.getMessage());
        }
    },
    /**
     * 修改使能人员信息
     */
    ENABLE_USER_INFO(2) {
        @Override
        public QueueResult run(Payload payload) {
            log.debug("修改使能人员信息");
            AcsDeviceResult result = new AcsDeviceResult(false,"使能人员信息失败");
            if (payload != null && payload instanceof Payload) { 
                result = EnableUserInfo(payload);    
            }
            if (result.isCode()){
                return new QueueResult("0", result.getMessage());
            }
            return new QueueResult("1",result.getMessage());
        }
    },
    /**
     * 删除人员信息
     */
    DELETE_USER_INFO(3) {
        @Override
        public QueueResult run(Payload payload) {
            log.debug("删除人员信息");
            AcsDeviceResult result = new AcsDeviceResult(false,"删除人员信息失败");
            if (payload != null && payload instanceof Payload) { 
                result = deleteUserInfo(payload);    
            }
            if (result.isCode()){
                return new QueueResult("0", result.getMessage());
            }
            return new QueueResult("1",result.getMessage());
        }
    },
    /**
     * 注销人员信息
     */
    CANCEL_USER_INFO(4) {
        @Override
        public QueueResult run(Payload payload) {
            log.debug("注销人员信息");
            AcsDeviceResult result = new AcsDeviceResult(false,"注销人员信息失败");
            if (payload != null && payload instanceof Payload) { 
                result = cancelUserInfo(payload);    
            }
            if (result.isCode()){
                return new QueueResult("0", result.getMessage());
            }
            return new QueueResult("1",result.getMessage());
        }
    },
    /**
     * 新增排班模板
     */
    ADD_TEMPLATE(5) {
        @Override
        public QueueResult run(Payload payload) {
            log.debug("下发周计划模板");
            AcsDeviceResult result = new AcsDeviceResult(false,"下发周计划模板失败");
            if (payload != null && payload instanceof Payload) { 
                result = IssuedWeekTemplate(payload);    
            }
            if (result.isCode()){
                return new QueueResult("0", result.getMessage());
            }
            return new QueueResult("1",result.getMessage());
        }
    },
    /**
     * 修改排班模板
     */
    EDIT_TEMPLATE(6) {
        @Override
        public QueueResult run(Payload o) {
            log.debug("修改排班模板");
            return new QueueResult("0", "");
        }
    },
    /**
     * 删除排版模板
     */
    DELETE_TEMPLATE(7) {
        @Override
        public QueueResult run(Payload o) {
            log.debug("删除排版模板");
            return new QueueResult("0", "");
        }
    },
    /**
     * 新增假日计划
     */
    ADD_HOLIDAY_PLAN(8) {
        @Override
        public QueueResult run(Payload payload) {
            log.debug("下发假日计划");
            AcsDeviceResult result = new AcsDeviceResult(false,"下发假日计划失败");
            if (payload != null && payload instanceof Payload) { 
                result = IssuedHolidayPlan(payload);    
            }
            if (result.isCode()){
                return new QueueResult("0", result.getMessage());
            }
            return new QueueResult("1",result.getMessage());
        }
    },
    /**
     * 修改假日计划
     */
    EDIT_HOLIDAY_PLAN(9) {
        @Override
        public QueueResult run(Payload o) {
            log.debug("修改假日计划");
            return new QueueResult("0", "");
        }
    },
    /**
     * 删除假日计划
     */
    DELETE_HOLIDAY_PLAN(10) {
        @Override
        public QueueResult run(Payload o) {
            log.debug("删除假日计划");
            return new QueueResult("0", "");
        }
    },
    /**
     * 新增假日组
     */
    ADD_HOLIDAY_GROUP(11) {
        @Override
        public QueueResult run(Payload payload) {
            log.debug("下发假日组");
            AcsDeviceResult result = new AcsDeviceResult(false,"下发假日组失败");
            if (payload != null && payload instanceof Payload) { 
                result = IssuedHolidayGroup(payload);    
            }
            if (result.isCode()){
                return new QueueResult("0", result.getMessage());
            }
            return new QueueResult("1",result.getMessage());
        }
    },
    /**
     * 修改假日组
     */
    EDIT_HOLIDAY_GROUP(12) {
        @Override
        public QueueResult run(Payload o) {
            log.debug("修改假日组");
            return new QueueResult("0", "");
        }
    },
    /**
     * 删除假日组
     */
    DELETE_HOLIDAY_GROUP(13) {
        @Override
        public QueueResult run(Payload o) {
            log.debug("删除假日组");
            return new QueueResult("0", "");
        }
    },
    /**
     * 下载应用群组
     */
    ISSUED_APPGROUP(14){
        @Override
        public QueueResult run(Payload payload) {
            log.debug("下发应用群组");
            AcsDeviceResult result = new AcsDeviceResult(false,"下发应用群组失败");
            if (payload != null && payload instanceof Payload) { 
                result = IssuedAppGroup(payload);    
            }
            if (result.isCode()){
                return new QueueResult("0", result.getMessage());
            }
            return new QueueResult("1",result.getMessage());
        }
    },
    /**
     * 下载时间设置
     */
    ISSUED_DATEANDTIME(15){
        @Override
        public QueueResult run(Payload payload) {
            log.debug("下发时间设置");
            AcsDeviceResult result = new AcsDeviceResult(false,"下发时间设置失败");
            if (payload != null && payload instanceof Payload) { 
                result = IssuedDateAndTime(payload);    
            }
            if (result.isCode()){
                return new QueueResult("0", result.getMessage());
            }
            return new QueueResult("1",result.getMessage());
        }
    },
    SET_CARD_NUMBER(16){
        @Override
        public QueueResult run(Payload payload) {
            log.debug("设置卡号");
            AcsDeviceResult result = new AcsDeviceResult(false,"设置卡号失败");
            if (payload != null && payload instanceof Payload) { 
                result = SetCardNumber(payload);    
            }
            if (result.isCode()){
                return new QueueResult("0", result.getMessage());
            }
            return new QueueResult("1",result.getMessage());
        }
    },
    /**
     * 未知
     */
    UNKNOWN(999) {
        @Override
        public QueueResult run(Payload o) {
            log.debug("未知命令");
            return new QueueResult("0", "");
        }
    }
    ;

    public int command;

    public abstract QueueResult run(Payload o);

    ControllerCommandEnum(int command) {
        this.command = command;
    }

    /**
     * 获取枚举类型
     * @param command
     * @return
     */
    public static ControllerCommandEnum getInstance(int command) {
        for (ControllerCommandEnum commandEnum : ControllerCommandEnum.values()) {
            if (commandEnum.command == command) {
                return commandEnum;
            }
        }
        return null;
    }

    /**
     * 判断参数合法性
     */
    public static boolean isValidName(Integer command) {
        for (ControllerCommandEnum commandEnum : ControllerCommandEnum.values()) {
            if (commandEnum.command == command) {
                return true;
            }
        }
        return false;
    }

    /**
     * 下发人员在单个门上权限
     */
    private static  AcsDeviceResult issuedUserInfo(Payload payload){
        log.info("下发一个人员");
        Long controllerId = payload.getControllerId();
        String ip = payload.getIp();
        Integer doorPin = payload.getDoorPin();
        ObjectMapper objectMapper = new ObjectMapper();
        IssueUserData userData = objectMapper.convertValue(payload.getData(), IssueUserData.class);

        AcsDeviceUserInfo userinfo = new AcsDeviceUserInfo();
        userinfo.setUserName(userData.getUserName());
        userinfo.setUserNumber(userData.getUserId()); 
        userinfo.setCardNumber(userData.getCardNumber());
        userinfo.setBeginTime(userData.getBeginTime()); 
        userinfo.setEndTime(userData.getEndTime()); 
        userinfo.setEnable(userData.getEnable());
        //userinfo.setPlanTemplateNumber(userData.getPlanTemplateNumber()); 
        userinfo.setDoorRight(doorPin); 
        
        //卡片数据转换
        AcsDeviceCardInfo cardinfo = new AcsDeviceCardInfo();
        cardinfo.setControllerIndex(userData.getControllerIndex());
        cardinfo.setCardIndex(userData.getCardIndex());
        String prefix = "";
        for(int i=0; i != 16-userData.getCardNumber().length(); i++){
            prefix += "0"; 
        }
        cardinfo.setCardNumber(prefix + userData.getCardNumber());
        cardinfo.setDoorRight(doorPin);
        cardinfo.setEnable(userData.getEnable()?"1":"0");
        cardinfo.setAPB("0");
        //人脸数据转换
        AcsDeviceFaceInfo faceinfo = new AcsDeviceFaceInfo();
        faceinfo.setCardNumber(userData.getCardNumber()); 
        faceinfo.setFaceFilePath(userData.getFaceInfo()); 
        
        AcsDeviceController controller = DeviceConStatus.getDeviceController(ip);
        //下发用户
        AcsDeviceResult result = controller.SetUser(userinfo);
        if (!result.isCode())
        {
            return result;
        }
        result = controller.SetCard(cardinfo);
        if (!result.isCode())
        {
            return result;
        } 
        if(faceinfo.getFaceFilePath() != "" && faceinfo.getFaceFilePath() != null){
            result = controller.SetFace(faceinfo);
            if(!result.isCode()){
                return result;
            }
        }
        log.info("下发一个人员成功");
        result.setMessage("下发人员成功");
        return result;
    }

    /**
     * 删除人员在单个门上的权限
     */
    private static  AcsDeviceResult deleteUserInfo(Payload payload){
        Long controllerId = payload.getControllerId();
        String ip = payload.getIp();
        Integer doorPin = payload.getDoorPin();
        ObjectMapper objectMapper = new ObjectMapper();
        IssueUserData userData = objectMapper.convertValue(payload.getData(), IssueUserData.class);

        AcsDeviceUserInfo userinfo = new AcsDeviceUserInfo();
        userinfo.setCardNumber(userData.getCardNumber()); 
        userinfo.setDoorRight(doorPin); 
        userinfo.setEnable(false); 
        
        //卡片数据转换
        AcsDeviceCardInfo cardinfo = new AcsDeviceCardInfo();
        cardinfo.setControllerIndex(userData.getControllerIndex());
        cardinfo.setCardIndex(userData.getCardIndex());
        String prefix = "";
        for(int i=0; i != 16-userData.getCardNumber().length(); i++){
            prefix += "0"; 
        }
        cardinfo.setCardNumber(prefix + userData.getCardNumber());
        cardinfo.setDoorRight(doorPin);
        cardinfo.setEnable(userData.getEnable()?"1":"0");
        cardinfo.setAPB("0");
        
        AcsDeviceController controller = DeviceConStatus.getDeviceController(ip);
        //删除用户
        AcsDeviceResult result = controller.DelCard(cardinfo);
        if(!result.isCode()){
            return result;
        }
        result = controller.DelUser(userinfo);
        if(!result.isCode()){
            return result;
        }
        result.setMessage("删除用户成功");
        return result;
    }

    /**
     * 修改使能人员信息
     */
    private static  AcsDeviceResult EnableUserInfo(Payload payload){
        Long controllerId = payload.getControllerId();
        String ip = payload.getIp();
        ObjectMapper objectMapper = new ObjectMapper();
        IssueUserData userData = objectMapper.convertValue(payload.getData(), IssueUserData.class);

        AcsDeviceUserInfo userinfo = new AcsDeviceUserInfo();
        userinfo.setUserName(userData.getUserName());
        userinfo.setUserNumber(userData.getUserId()); 
        userinfo.setCardNumber(userData.getCardNumber());
        userinfo.setBeginTime(userData.getBeginTime()); 
        userinfo.setEndTime(userData.getEndTime()); 
        userinfo.setEnable(userData.getEnable());
        //userinfo.setPlanTemplateNumber(userData.getPlanTemplateNumber()); 
        
        //卡片数据转换
        AcsDeviceCardInfo cardinfo = new AcsDeviceCardInfo();
        cardinfo.setControllerIndex(userData.getControllerIndex());
        cardinfo.setCardIndex(userData.getCardIndex());
        cardinfo.setEnable(userData.getEnable()?"1":"0");
        //人脸数据转换
        AcsDeviceFaceInfo faceinfo = new AcsDeviceFaceInfo();
        faceinfo.setCardNumber(userData.getCardNumber()); 
        faceinfo.setFaceFilePath(userData.getFaceInfo()); 
        
        AcsDeviceController controller = DeviceConStatus.getDeviceController(ip);
        //使能用户
        AcsDeviceResult result = controller.EnableUser(userinfo);
        if(!result.isCode())
        {
            return result;
        }
        result = controller.EnableCard(cardinfo);
        if(!result.isCode())
        {
            return result;
        }
        result.setMessage("使能用户成功");
        return result;
    }

    /**
     * 注销人员信息
     */
    private static AcsDeviceResult cancelUserInfo(Payload payload){
        Long controllerId = payload.getControllerId();
        String ip = payload.getIp();
        ObjectMapper objectMapper = new ObjectMapper();
        DeleteUserData userData = objectMapper.convertValue(payload.getData(), DeleteUserData.class);

        AcsDeviceUserInfo userinfo = new AcsDeviceUserInfo();
        userinfo.setCardNumber(userData.getCardNumber()); 
        AcsDeviceCardInfo cardinfo = new AcsDeviceCardInfo();
        cardinfo.setControllerIndex(userData.getControllerIndex());
        cardinfo.setCardIndex(userData.getCardIndex());
        cardinfo.setCardNumber(userData.getCardNumber());
        AcsDeviceFaceInfo faceinfo = new AcsDeviceFaceInfo();
        faceinfo.setCardNumber(userData.getCardNumber());
        
        AcsDeviceController controller = DeviceConStatus.getDeviceController(ip);
        //删除用户
        AcsDeviceResult result =  new AcsDeviceResult();
        if(faceinfo.getFaceFilePath() != "" && faceinfo.getFaceFilePath() != null){
            result = controller.CancelFace(faceinfo);
            if (!result.isCode()){
                return result;
            }
        }   
        result = controller.CancelCard(cardinfo);
        if(!result.isCode()){
            return result;
        }
        result = controller.CancelUser(userinfo);
        if(!result.isCode()){
            return result;
        }
        result.setMessage("注销用户成功");
        return result;
    }

    private  static AcsDeviceResult IssuedWeekTemplate(Payload payload){
        AcsDeviceResult result =  new AcsDeviceResult(false,"下发周计划模板失败");
        Long controllerId = payload.getControllerId();
        String ip = payload.getIp();
        AcsDeviceController controller = DeviceConStatus.getDeviceController(ip);
        JSONArray jsonArray =  (JSONArray) JSONObject.toJSON(payload.getData());
        int len = jsonArray.size();
        ObjectMapper objectMapper = new ObjectMapper();
        for(int i = 0; i!= len; i++){
            AcsDeviceWeekTemplateInfo weekTemplateInfo = objectMapper.convertValue(jsonArray.get(i),AcsDeviceWeekTemplateInfo.class);
            result =  controller.SetWeekTemplate(weekTemplateInfo);
            if(!result.isCode()){
                return result;
            }
        }   
        return result;
    }

    private static AcsDeviceResult IssuedHolidayPlan(Payload payload){
        Long controllerId = payload.getControllerId();
        String ip = payload.getIp();
        AcsDeviceController controller = DeviceConStatus.getDeviceController(ip);
        JSONArray jsonArray =  (JSONArray) JSONObject.toJSON(payload.getData());
        int len = jsonArray.size();
        ObjectMapper objectMapper = new ObjectMapper();
        AcsDeviceHolidayPlanInfo[] holidayPlans = new AcsDeviceHolidayPlanInfo[len];
        for(int i = 0; i!= len; i++){
            holidayPlans[i] = objectMapper.convertValue(jsonArray.get(i), AcsDeviceHolidayPlanInfo.class);
        }
        return controller.SetHolidayPlan(holidayPlans);
    }
    
    private  static AcsDeviceResult IssuedHolidayGroup(Payload payload){
        AcsDeviceResult result =  new AcsDeviceResult(false,"下发假日组失败");
        Long controllerId = payload.getControllerId();
        String ip = payload.getIp();
        AcsDeviceController controller = DeviceConStatus.getDeviceController(ip);
        JSONArray jsonArray =  (JSONArray) JSONObject.toJSON(payload.getData());
        int len = jsonArray.size();
        ObjectMapper objectMapper = new ObjectMapper();
        for(int i = 0; i!= len; i++){
            AcsDeviceHolidayGroupInfo holidayGroup = objectMapper.convertValue(jsonArray.get(i), AcsDeviceHolidayGroupInfo.class);
            result =  controller.SetHolidayGroup(holidayGroup);
            if(!result.isCode()){
                return result;
            }
        }
        return result;
    }

    private  static AcsDeviceResult IssuedAppGroup(Payload payload){
        Long controllerId = payload.getControllerId();
        String ip = payload.getIp();
        AcsDeviceController controller = DeviceConStatus.getDeviceController(ip);
        JSONArray jsonArray =  (JSONArray) JSONObject.toJSON(payload.getData());
        int len = jsonArray.size();
        ObjectMapper objectMapper = new ObjectMapper();
        AcsDeviceAppGroupSettingInfo[] appgroups = new AcsDeviceAppGroupSettingInfo[len];
        for(int i = 0; i!= len; i++){
            appgroups[i] = objectMapper.convertValue(jsonArray.get(i), AcsDeviceAppGroupSettingInfo.class);
        }
        return controller.setAppgroup(appgroups);
    }

    private  static AcsDeviceResult IssuedDateAndTime(Payload payload){
        AcsDeviceResult result =  new AcsDeviceResult(false,"下发时间设置失败");
        Long controllerId = payload.getControllerId();
        String ip = payload.getIp();
        AcsDeviceController controller = DeviceConStatus.getDeviceController(ip);
        JSONObject jsono = (JSONObject) JSONObject.toJSON(payload.getData());
        JSONArray timezoneArray =  (JSONArray) JSONObject.toJSON(jsono.get("timezone"));
        int len = timezoneArray.size();
        ObjectMapper objectMapper = new ObjectMapper();
        AcsDeviceTimeZoneInfo[] timezones = new AcsDeviceTimeZoneInfo[len];
        for(int i = 0; i!= len; i++){
            timezones[i] = objectMapper.convertValue(timezoneArray.get(i), AcsDeviceTimeZoneInfo.class);
        }
        result = controller.setTimezone(timezones);
        if(!result.isCode()){
            return result;
        }
        JSONArray timeIntervalArray =  (JSONArray) JSONObject.toJSON(jsono.get("timeInterval"));
        len = timeIntervalArray.size();
        AcsDeviceTimeIntervalInfo[] timeIntervals = new AcsDeviceTimeIntervalInfo[len];
        for(int i = 0; i!= len; i++){
            timeIntervals[i] = objectMapper.convertValue(timeIntervalArray.get(i), AcsDeviceTimeIntervalInfo.class);
        }
        result = controller.setInterval(timeIntervals);
        if(!result.isCode()){
            return result;
        }
        JSONArray holidayArray =  (JSONArray) JSONObject.toJSON(jsono.get("holiday"));
        len = holidayArray.size();
        AcsDeviceHolidayInfo[] holidays = new AcsDeviceHolidayInfo[len];
        for(int i = 0; i!= len; i++){
            holidays[i] = objectMapper.convertValue(holidayArray.get(i), AcsDeviceHolidayInfo.class);
        }
        result = controller.setHoliday(holidays);
        if(!result.isCode()){
            return result;
        }
        result.setMessage("时间设置下载成功");
        return result;
    }

    private  static AcsDeviceResult SetCardNumber(Payload payload){
        AcsDeviceResult result =  new AcsDeviceResult(false,"设置卡片失败");
        String ip = payload.getIp();
        AcsDeviceController controller = DeviceConStatus.getDeviceController(ip);
        JSONObject jsono = (JSONObject) JSONObject.toJSON(payload.getData());
        String controllerIndex = jsono.getString("controllerIndex");
        String cardIndex = jsono.getString("cardIndex");
        String cardNumber = jsono.getString("cardNumber");
        result = controller.setCardNumber(controllerIndex, cardIndex, cardNumber);
        if(!result.isCode()){
            return result;
        }
        result.setMessage("时间设置下载成功");
        return result;
    }
}
