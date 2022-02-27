package com.hzcloud.device.domain;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 区域和控制器关联 acs_device_zone_controller
 * 
 * @author zhangfan
 */
public class AcsDeviceZoneCon {
       /** 区域ID */
       private Long zoneId;
    
       /** 控制器ID */
       private Long controllerId;
   
       public Long getZoneId()
       {
           return zoneId;
       }
   
       public void setZoneId(Long zoneId)
       {
           this.zoneId = zoneId;
       }
   
       public Long getControllerId()
       {
           return controllerId;
       }
   
       public void setControllerId(Long controllerId)
       {
           this.controllerId = controllerId;
       }
   
       @Override
       public String toString() {
           return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
               .append("zoneId", getZoneId())
               .append("controllerId", getControllerId())
               .toString();
       } 
}