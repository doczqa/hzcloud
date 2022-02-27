package com.hzcloud.device.syris.bo;

import java.util.List;

public class V8AddAuthorizationInfo {

    private String identifier;
    private List<Authorization> authorizations;
    public void setIdentifier(String identifier) {
         this.identifier = identifier;
     }
     public String getIdentifier() {
         return identifier;
     }

    public void setAuthorizations(List<Authorization> authorizations) {
         this.authorizations = authorizations;
     }
     public List<Authorization> getAuthorizations() {
         return authorizations;
     }

    public class Authorization {
    
        private String card_unique_identifier;
        private int door_accessable_flags;
        private int door_accessable_flags_mask;
        private long effective_timestamp;
        private long expire_timestamp;
        private String display_text;
        private String pin_code;
        private int apb_enabled;
        private int apb_level_value;
        private List<Authorized_configuration> authorized_configuration_list;
        public void setCard_unique_identifier(String card_unique_identifier) {
            this.card_unique_identifier = card_unique_identifier;
        }
        public String getCard_unique_identifier() {
            return card_unique_identifier;
        }
    
        public void setDoor_accessable_flags(int door_accessable_flags) {
            this.door_accessable_flags = door_accessable_flags;
        }

        public int getDoor_accessable_flags() {
            return door_accessable_flags;
        }

        public void setDoor_accessable_flags_mask(int door_accessable_flags_mask){
            this.door_accessable_flags_mask = door_accessable_flags_mask;
        }

        public int getDoor_accessable_flags_mask(){
            return door_accessable_flags_mask;
        }
    
        public void setEffective_timestamp(long effective_timestamp) {
            this.effective_timestamp = effective_timestamp;
        }

        public long getEffective_timestamp() {
            return effective_timestamp;
        }
    
        public void setExpire_timestamp(long expire_timestamp) {
            this.expire_timestamp = expire_timestamp;
        }
        public long getExpire_timestamp() {
            return expire_timestamp;
        }
    
        public void setDisplay_text(String display_text) {
            this.display_text = display_text;
        }
        public String getDisplay_text() {
            return display_text;
        }
    
        public void setPin_code(String pin_code) {
            this.pin_code = pin_code;
        }
        public String getPin_code() {
            return pin_code;
        }
    
        public void setApb_enabled(int apb_enabled) {
            this.apb_enabled = apb_enabled;
        }
        public int getApb_enabled() {
            return apb_enabled;
        }
    
        public void setApb_level_value(int apb_level_value) {
            this.apb_level_value = apb_level_value;
        }
        public int getApb_level_value() {
            return apb_level_value;
        }
    
        public void setAuthorized_configuration_list(List<Authorized_configuration> authorized_configuration_list) {
            this.authorized_configuration_list = authorized_configuration_list;
        }
        public List<Authorized_configuration> getAuthorized_configuration_list() {
            return authorized_configuration_list;
        }
    
    }

    public class Authorized_configuration {
    
        private int card_type;
        private int card_level;
        private int card_in_black;
        private int card_disabled;
        private int week_plan_flags;
        private List<Time_range> time_range_list;
        public void setCard_type(int card_type) {
            this.card_type = card_type;
        }
        public int getCard_type() {
            return card_type;
        }
    
        public void setCard_level(int card_level) {
            this.card_level = card_level;
        }
        public int getCard_level() {
            return card_level;
        }
    
        public void setCard_in_black(int card_in_black) {
            this.card_in_black = card_in_black;
        }
        public int getCard_in_black() {
            return card_in_black;
        }
    
        public void setCard_disabled(int card_disabled) {
            this.card_disabled = card_disabled;
        }
        public int getCard_disabled() {
            return card_disabled;
        }
    
        public void setWeek_plan_flags(int week_plan_flags) {
            this.week_plan_flags = week_plan_flags;
        }
        public int getWeek_plan_flags() {
            return week_plan_flags;
        }
    
        public void setTime_range_list(List<Time_range> time_range_list) {
            this.time_range_list = time_range_list;
        }
        public List<Time_range> getTime_range_list() {
            return time_range_list;
        }
    
    }

    public class Time_range {
    
        private int start_timestamp;
        private long endle_timestamp;
        public void setStart_timestamp(int start_timestamp) {
            this.start_timestamp = start_timestamp;
        }
        public int getStart_timestamp() {
            return start_timestamp;
        }
    
        public void setEndle_timestamp(long endle_timestamp) {
            this.endle_timestamp = endle_timestamp;
        }
        public long getEndle_timestamp() {
            return endle_timestamp;
        }
    
    }
}
