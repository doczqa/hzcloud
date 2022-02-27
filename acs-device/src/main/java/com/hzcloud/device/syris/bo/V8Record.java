package com.hzcloud.device.syris.bo;

public class V8Record {

    private int index;
    private int timestamp;
    private int format;
    private int status;
    private int tag_state;
    private int the_datagram;
    private int controller_door_index;
    private int controller_module_index;
    private int controller_module_channel;
    private long controller_serial_number;
    private String door_map_identifier;
    private String module_map_identifier;
    private String controller_map_identifier;
    private String card_unique_identifier;
    public void setIndex(int index) {
         this.index = index;
     }
     public int getIndex() {
         return index;
     }

    public void setTimestamp(int timestamp) {
         this.timestamp = timestamp;
     }
     public int getTimestamp() {
         return timestamp;
     }

    public void setFormat(int format) {
         this.format = format;
     }
     public int getFormat() {
         return format;
     }

    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setTag_state(int tag_state) {
         this.tag_state = tag_state;
     }
     public int getTag_state() {
         return tag_state;
     }

    public void setThe_datagram(int the_datagram) {
         this.the_datagram = the_datagram;
     }
     public int getThe_datagram() {
         return the_datagram;
     }

    public void setController_door_index(int controller_door_index) {
         this.controller_door_index = controller_door_index;
     }
     public int getController_door_index() {
         return controller_door_index;
     }

    public void setController_module_index(int controller_module_index) {
         this.controller_module_index = controller_module_index;
     }
     public int getController_module_index() {
         return controller_module_index;
     }

    public void setController_module_channel(int controller_module_channel) {
         this.controller_module_channel = controller_module_channel;
     }
     public int getController_module_channel() {
         return controller_module_channel;
     }

    public void setController_serial_number(long controller_serial_number) {
         this.controller_serial_number = controller_serial_number;
     }
     public long getController_serial_number() {
         return controller_serial_number;
     }

    public void setDoor_map_identifier(String door_map_identifier) {
         this.door_map_identifier = door_map_identifier;
     }
     public String getDoor_map_identifier() {
         return door_map_identifier;
     }

    public void setModule_map_identifier(String module_map_identifier) {
         this.module_map_identifier = module_map_identifier;
     }
     public String getModule_map_identifier() {
         return module_map_identifier;
     }

    public void setController_map_identifier(String controller_map_identifier) {
         this.controller_map_identifier = controller_map_identifier;
     }
     public String getController_map_identifier() {
         return controller_map_identifier;
     }

    public void setCard_unique_identifier(String card_unique_identifier) {
         this.card_unique_identifier = card_unique_identifier;
     }
     public String getCard_unique_identifier() {
         return card_unique_identifier;
     }
}
