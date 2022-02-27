package com.hzcloud.device.syris;

import java.util.ArrayList;
import java.util.List;

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
import com.hzcloud.device.syris.bo.V8AddControllerInfo;
import com.hzcloud.device.syris.bo.V8DeviceTopology;
import com.hzcloud.device.syris.bo.V8DevicesInfo;
import com.hzcloud.device.syris.bo.V8DeviceTopology.Controller_map;
import com.hzcloud.device.syris.bo.V8DevicesInfo.Connection;
import com.hzcloud.device.syris.bo.V8DevicesInfo.Device;
import com.hzcloud.device.syris.utils.utils;

public class V8Controller implements AcsDeviceController {
    //控制器标识符
    private String identifier;

    private V8 v8 = new V8();

    @Override
    public AcsDeviceResult Login(String ip, String username, String pw, int port, String identifier) {
        AcsDeviceResult result = new AcsDeviceResult(false,"");
        if(identifier!= null && identifier !="")
        {
            this.identifier = identifier;
            result.setCode(true);
            result.setMessage(this.identifier);
            return result;
        }
        Long ipLong = utils.getIpLong(ip);
        if (ipLong == (long)0){
            return result;
        }

        //判断V8中间件是否连接
        String gatewayIdentifier = V8.getGatewayIdentifier();
        if(gatewayIdentifier == null || gatewayIdentifier == ""){
            if(!V8.Init()){
                return result;
            }
        }
        //assgin device
        V8DevicesInfo devicesInfo = new V8DevicesInfo();
        List<Device> devicelist = new ArrayList<Device>();
        Connection connection = devicesInfo.new Connection();
        Device device = devicesInfo.new Device();
        connection.setAddress(ipLong);
        connection.setPort(port);
        connection.setMode(0);
        connection.setIdentification(0);
        device.setCloud_gateway_identifier(V8.getGatewayIdentifier());
        device.setConnection(connection);
        devicelist.add(device);
        devicesInfo.setDevices(devicelist);
        String deviceid = v8.DeviceAssgin(devicesInfo);
        if(deviceid == ""){
            return result;
        }
        if(!v8.DeviceReload()){
            return result;
        }
        //获取拓扑
        V8DeviceTopology topology = v8.DeviceTopology(deviceid);
        List<Controller_map> controllermaps = topology.getController_maps();
        Controller_map map = controllermaps.get(0);
        String controller_map_id = map.getIdentifier();
        //添加控制器
        V8AddControllerInfo addInfo = new V8AddControllerInfo();
        addInfo.setController_map_identifier(controller_map_id);
        String controllerId =  v8.AddController(addInfo);
        if(controllerId == ""){
            return result;
        }
        result.setCode(true);
        result.setMessage(controllerId);
        return result;
    }

    @Override
    public void Logout() {
    }

    @Override
    public AcsDeviceResult SetUser(AcsDeviceUserInfo userInfo) {
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
        return new AcsDeviceResult(true,"用户下发成功");
    }

    @Override
    public AcsDeviceResult SetCard(AcsDeviceCardInfo cardInfo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AcsDeviceResult SetFace(AcsDeviceFaceInfo faceInfo) {
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
        return new AcsDeviceResult(true,"人脸下发成功");
    }

    @Override
    public AcsDeviceResult DelUser(AcsDeviceUserInfo userInfo) {
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
        return new AcsDeviceResult(true,"用户删除成功");
    }

    @Override
    public AcsDeviceResult DelCard(AcsDeviceCardInfo cardInfo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AcsDeviceResult EnableUser(AcsDeviceUserInfo userInfo) {
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
        return new AcsDeviceResult(true,"用户使能成功");
    }

    @Override
    public AcsDeviceResult EnableCard(AcsDeviceCardInfo cardInfo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AcsDeviceResult CancelUser(AcsDeviceUserInfo userInfo) {
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
        return new AcsDeviceResult(true,"用户注销成功");
    }

    @Override
    public AcsDeviceResult CancelCard(AcsDeviceCardInfo userInfo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AcsDeviceResult CancelFace(AcsDeviceFaceInfo faceInfo) {
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
        return new AcsDeviceResult(true,"人脸注销成功");
    }

    @Override
    public AcsDeviceResult DoorControl(AcsDeviceCommandInfo cmd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AcsDeviceResult SetupAlarmChan() {
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
        return new AcsDeviceResult(true,"驻防成功");
    }

    @Override
    public AcsDeviceResult CloseAlarmChan() {
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
        return new AcsDeviceResult(true,"撤防成功");
    }

    @Override
    public AcsDeviceResult CheckConnect(String controllerIndex) {
        AcsDeviceResult result = new AcsDeviceResult(false,"控制器未连接");
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
		boolean ret = v8.GetControllerStatus(this.identifier);
        if(ret){
            result.setCode(true);
            result.setMessage("控制器已连接");
        }
        return result;
    }

    @Override
    public AcsDeviceResult SetWeekTemplate(AcsDeviceWeekTemplateInfo weekTemplate) {
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
        return new AcsDeviceResult(true,"设置周计划成功");
    }

    @Override
    public AcsDeviceResult SetHolidayGroup(AcsDeviceHolidayGroupInfo holidayGroup) {
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
        return new AcsDeviceResult(true,"设置假日组成功");
    }

    @Override
    public AcsDeviceResult SetHolidayPlan(AcsDeviceHolidayPlanInfo[] holidayPlan) {
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
        return new AcsDeviceResult(true,"设置假日计划成功");
    }

    @Override
    public AcsDeviceResult setAppgroup(AcsDeviceAppGroupSettingInfo[] settingList) {
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
        return new AcsDeviceResult(true,"设置应用群组成功");
    }

    @Override
    public AcsDeviceResult setTimezone(AcsDeviceTimeZoneInfo[] timeZoneList) {
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
        return new AcsDeviceResult(true,"设置时区成功");
    }

    @Override
    public AcsDeviceResult setInterval(AcsDeviceTimeIntervalInfo[] timeIntervalList) {
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
        return new AcsDeviceResult(true,"设置时段成功");
    }

    @Override
    public AcsDeviceResult setHoliday(AcsDeviceHolidayInfo[] holidayList) {
        if(this.identifier == null || this.identifier == ""){
            return new AcsDeviceResult(false,"控制器未托管");
        }
        return new AcsDeviceResult(true,"设置假日成功");
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
