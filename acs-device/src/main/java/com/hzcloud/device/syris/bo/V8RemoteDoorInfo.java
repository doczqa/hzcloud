package com.hzcloud.device.syris.bo;

import java.util.List;

public class V8RemoteDoorInfo {

    private List<Door> doors;
    public void setDoors(List<Door> doors) {
         this.doors = doors;
     }
     public List<Door> getDoors() {
         return doors;
     }

  public class Door {
  
      private String identifier;
      private int type;
      private int time_100_millisecond;
      public void setIdentifier(String identifier) {
           this.identifier = identifier;
       }
       public String getIdentifier() {
           return identifier;
       }
  
      public void setType(int type) {
           this.type = type;
       }
       public int getType() {
           return type;
       }
  
      public void setTime_100_millisecond(int time_100_millisecond) {
           this.time_100_millisecond = time_100_millisecond;
       }
       public int getTime_100_millisecond() {
           return time_100_millisecond;
       }
  
  }
}
