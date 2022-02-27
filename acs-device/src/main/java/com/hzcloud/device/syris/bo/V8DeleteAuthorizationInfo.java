package com.hzcloud.device.syris.bo;

import java.util.List;

public class V8DeleteAuthorizationInfo {

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
    }

}
