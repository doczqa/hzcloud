package com.hzcloud.device.syris.bo;

import java.util.List;

public class V8HolidayInfo {

    private String identifier;
    private Holiday holiday;
    public void setIdentifier(String identifier) {
         this.identifier = identifier;
     }
     public String getIdentifier() {
         return identifier;
     }

    public void setHoliday(Holiday holiday) {
         this.holiday = holiday;
     }
    public Holiday getHoliday() {
         return holiday;
     }

  public class Holiday {
  
      private List<Integer> holiday_flags_list;
      public void setHoliday_flags_list(List<Integer> holiday_flags_list) {
           this.holiday_flags_list = holiday_flags_list;
       }
       public List<Integer> getHoliday_flags_list() {
           return holiday_flags_list;
       }
  
  }
}
