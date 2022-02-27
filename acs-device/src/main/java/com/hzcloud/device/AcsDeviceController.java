package com.hzcloud.device;

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

public interface AcsDeviceController
{
    /**
     * 登录
     * @param ip 控制器IP地址
     * @param username  用户名
     * @param pw    密码
     * @param port  端口
     * @return
     */
    AcsDeviceResult Login(String ip, String username, String pw, int port,String identifier);
    //登出
    void Logout();
    //下发用户在单个门上的权限
    AcsDeviceResult SetUser(AcsDeviceUserInfo userInfo);
    //下发卡片在单个门上的权限
    AcsDeviceResult SetCard(AcsDeviceCardInfo cardInfo);
    //下发人脸
    AcsDeviceResult SetFace(AcsDeviceFaceInfo faceInfo);
    //删除用户在单个门上的权限
    AcsDeviceResult DelUser(AcsDeviceUserInfo userInfo);
    //删除卡片在单个门上的权限
    AcsDeviceResult DelCard(AcsDeviceCardInfo cardInfo);
    //使能/失能用户在控制器上的权限
    AcsDeviceResult EnableUser(AcsDeviceUserInfo userInfo);
    //使能/失能卡片在控制器上的权限
    AcsDeviceResult EnableCard(AcsDeviceCardInfo cardInfo);
    //注销用户
    AcsDeviceResult CancelUser(AcsDeviceUserInfo userInfo);
    //注销用户
    AcsDeviceResult CancelCard(AcsDeviceCardInfo userInfo);
    //注销人脸
    AcsDeviceResult CancelFace(AcsDeviceFaceInfo faceInfo);
    //门控制
    AcsDeviceResult DoorControl(AcsDeviceCommandInfo cmd);
    //布防
    AcsDeviceResult SetupAlarmChan();
    //撤防
    AcsDeviceResult CloseAlarmChan();
    //判断是否连接成功
    AcsDeviceResult CheckConnect(String  controllerIndex);
    //下发周计划模板
    AcsDeviceResult SetWeekTemplate(AcsDeviceWeekTemplateInfo weekTemplate);
    //下发假日组
    AcsDeviceResult SetHolidayGroup(AcsDeviceHolidayGroupInfo holidayGroup);
    //下发假日计划
    AcsDeviceResult SetHolidayPlan(AcsDeviceHolidayPlanInfo[] holidayPlan);
    //下发应用群组
    AcsDeviceResult setAppgroup(AcsDeviceAppGroupSettingInfo[] settingList);
    //下发时区
    AcsDeviceResult setTimezone(AcsDeviceTimeZoneInfo[] timeZoneList);
    //下发时段
    AcsDeviceResult setInterval(AcsDeviceTimeIntervalInfo[] timeIntervalList);
    //下发假日
    AcsDeviceResult setHoliday(AcsDeviceHolidayInfo[] holidayList);
    //获取无记录卡号
    AcsDeviceResult getCardNumNoInSystem(String controllerIndex,String nn);
    //获取门状态
    AcsDeviceResult getDoorStatus(String controllerIndex);
    //获取记录流水
    AcsDeviceResult getRecordAndTotal(String controllerIndex);
    //修改卡号
    AcsDeviceResult setCardNumber(String controllerIndex, String cardIndex, String cardNumber);
}