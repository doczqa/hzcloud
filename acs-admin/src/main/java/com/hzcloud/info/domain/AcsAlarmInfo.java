package com.hzcloud.info.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 报警信息对象 acs_alarm_info
 * 
 * @author zhangfan
 * @date 2021-05-17
 */
public class AcsAlarmInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long alarmId;

    /** 报警时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "报警时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date alarmTime;

    /** 报警类型 */
    @Excel(name = "报警类型")
    private String alarmType;

    /** 控制器id */
    private Long controllerId;

    /** 控制器 */
    @Excel(name = "控制器")
    private String controllerName;

    /** 控制器IP */
    @Excel(name = "控制器IP")
    private String controllerIp;

    /** 门名称 */
    @Excel(name = "门名称")
    private String doorName;

    /** 门端口 */
    @Excel(name = "门端口")
    private Long doorPin;

    /** 详情 */
    @Excel(name = "详情")
    private String alarmDetail;

    /** 用户 */
    @Excel(name = "用户")
    private String userName;

    /** 卡号 */
    @Excel(name = "卡号")
    private String cardNumber;

    public void setAlarmId(Long alarmId) 
    {
        this.alarmId = alarmId;
    }

    public Long getAlarmId() 
    {
        return alarmId;
    }
    public void setAlarmTime(Date alarmTime) 
    {
        this.alarmTime = alarmTime;
    }

    public Date getAlarmTime() 
    {
        return alarmTime;
    }
    public void setAlarmType(String alarmType) 
    {
        this.alarmType = alarmType;
    }

    public String getAlarmType() 
    {
        return alarmType;
    }

    public void setControllerId(Long controllerId){
        this.controllerId = controllerId;
    }

    public Long getControllerId(){
        return controllerId;
    } 

    public void setControllerName(String controllerName) 
    {
        this.controllerName = controllerName;
    }

    public String getControllerName() 
    {
        return controllerName;
    }
    public void setControllerIp(String controllerIp) 
    {
        this.controllerIp = controllerIp;
    }

    public String getControllerIp() 
    {
        return controllerIp;
    }
    public void setDoorName(String doorName) 
    {
        this.doorName = doorName;
    }

    public String getDoorName() 
    {
        return doorName;
    }
    public void setDoorPin(Long doorPin) 
    {
        this.doorPin = doorPin;
    }

    public Long getDoorPin() 
    {
        return doorPin;
    }
    public void setAlarmDetail(String alarmDetail) 
    {
        this.alarmDetail = alarmDetail;
    }

    public String getAlarmDetail() 
    {
        return alarmDetail;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setCardNumber(String cardNumber) 
    {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() 
    {
        return cardNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("alarmId", getAlarmId())
            .append("alarmTime", getAlarmTime())
            .append("alarmType", getAlarmType())
            .append("controllerId",getControllerId())
            .append("controllerName", getControllerName())
            .append("controllerIp", getControllerIp())
            .append("doorName", getDoorName())
            .append("doorPin", getDoorPin())
            .append("alarmDetail", getAlarmDetail())
            .append("userName", getUserName())
            .append("cardNumber", getCardNumber())
            .toString();
    }
}
