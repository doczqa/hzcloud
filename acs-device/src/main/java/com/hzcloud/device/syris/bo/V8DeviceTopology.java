package com.hzcloud.device.syris.bo;

import java.util.List;

public class V8DeviceTopology{
    private int result;
    private List<Controller_map> controller_maps;
    public void setResult(int result) {
         this.result = result;
     }
     public int getResult() {
         return result;
     }

    public void setController_maps(List<Controller_map> controller_maps) {
         this.controller_maps = controller_maps;
     }
     public List<Controller_map> getController_maps() {
         return controller_maps;
     }

    public class Controller_map {
        private String identifier;
        private String cloud_gateway_identifier;
        private int is_connected;
        private int in_controlled;
        private int model_code;
        private int model_version;
        private long serial_number;
        private int network_identifier;
        private int encryption_key_type;
        private int encryption_key_switch_level;
        private int module_amount;
        private int door_amount;
        private List<Module_map> module_maps;
        private List<Door_map> door_maps;
        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }
        public String getIdentifier() {
            return identifier;
        }

        public void setCloud_gateway_identifier(String cloud_gateway_identifier) {
            this.cloud_gateway_identifier = cloud_gateway_identifier;
        }
        public String getCloud_gateway_identifier() {
            return cloud_gateway_identifier;
        }

        public void setIs_connected(int is_connected) {
            this.is_connected = is_connected;
        }
        public int getIs_connected() {
            return is_connected;
        }

        public void setIn_controlled(int in_controlled) {
            this.in_controlled = in_controlled;
        }
        public int getIn_controlled() {
            return in_controlled;
        }

        public void setModel_code(int model_code) {
            this.model_code = model_code;
        }
        public int getModel_code() {
            return model_code;
        }

        public void setModel_version(int model_version) {
            this.model_version = model_version;
        }
        public int getModel_version() {
            return model_version;
        }

        public void setSerial_number(long serial_number) {
            this.serial_number = serial_number;
        }
        public long getSerial_number() {
            return serial_number;
        }

        public void setNetwork_identifier(int network_identifier) {
            this.network_identifier = network_identifier;
        }
        public int getNetwork_identifier() {
            return network_identifier;
        }

        public void setEncryption_key_type(int encryption_key_type) {
            this.encryption_key_type = encryption_key_type;
        }
        public int getEncryption_key_type() {
            return encryption_key_type;
        }

        public void setEncryption_key_switch_level(int encryption_key_switch_level) {
            this.encryption_key_switch_level = encryption_key_switch_level;
        }
        public int getEncryption_key_switch_level() {
            return encryption_key_switch_level;
        }

        public void setModule_amount(int module_amount) {
            this.module_amount = module_amount;
        }
        public int getModule_amount() {
            return module_amount;
        }

        public void setDoor_amount(int door_amount) {
            this.door_amount = door_amount;
        }
        public int getDoor_amount() {
            return door_amount;
        }

        public void setModule_maps(List<Module_map> module_maps) {
            this.module_maps = module_maps;
        }
        public List<Module_map> getModule_maps() {
            return module_maps;
        }

        public void setDoor_maps(List<Door_map> door_maps) {
            this.door_maps = door_maps;
        }
        public List<Door_map> getDoor_maps() {
            return door_maps;
        }
    }

    public class Door_map {

        private String identifier;
        private int controller_door_index;
        private int in_controlled;
        private List<Module_map> reader_maps;
        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }
        public String getIdentifier() {
            return identifier;
        }

        public void setController_door_index(int controller_door_index) {
            this.controller_door_index = controller_door_index;
        }
        public int getController_door_index() {
            return controller_door_index;
        }

        public void setIn_controlled(int in_controlled) {
            this.in_controlled = in_controlled;
        }
        public int getIn_controlled() {
            return in_controlled;
        }

        public void setReader_maps(List<Module_map> reader_maps) {
            this.reader_maps = reader_maps;
        }
        public List<Module_map> getReader_maps() {
            return reader_maps;
        }
    }

    public class Module_map {

        private String identifier;
        private int controller_module_index;
        private int is_connected;
        private int in_controlled;
        private int model_code;
        private long serial_number;
        private int network_identifier;
        private int function_type;
        private int entry_door_direction;
        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }
        public String getIdentifier() {
            return identifier;
        }

        public void setController_module_index(int controller_module_index) {
            this.controller_module_index = controller_module_index;
        }
        public int getController_module_index() {
            return controller_module_index;
        }

        public void setIs_connected(int is_connected) {
            this.is_connected = is_connected;
        }
        public int getIs_connected() {
            return is_connected;
        }

        public void setIn_controlled(int in_controlled) {
            this.in_controlled = in_controlled;
        }
        public int getIn_controlled() {
            return in_controlled;
        }

        public void setModel_code(int model_code) {
            this.model_code = model_code;
        }
        public int getModel_code() {
            return model_code;
        }

        public void setSerial_number(long serial_number) {
            this.serial_number = serial_number;
        }
        public long getSerial_number() {
            return serial_number;
        }

        public void setNetwork_identifier(int network_identifier) {
            this.network_identifier = network_identifier;
        }
        public int getNetwork_identifier() {
            return network_identifier;
        }

        public void setFunction_type(int function_type) {
            this.function_type = function_type;
        }
        public int getFunction_type() {
            return function_type;
        }

        public void setEntry_door_direction(int entry_door_direction) {
            this.entry_door_direction = entry_door_direction;
        }
        public int getEntry_door_direction() {
            return entry_door_direction;
        }

    }
}