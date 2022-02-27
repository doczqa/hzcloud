package com.hzcloud.device.syris.bo;

import java.util.List;

public class V8DevicesInfo {
    private List<Device> devices;
    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
    public List<Device> getDevices() {
        return devices;
    }

    public class Connection {

	    private int mode;
	    private int port;
	    private long address;
	    private int identification;
	    public void setMode(int mode) {
	         this.mode = mode;
	     }
	     public int getMode() {
	         return mode;
	     }

	    public void setPort(int port) {
	         this.port = port;
	     }
	     public int getPort() {
	         return port;
	     }

	    public void setAddress(long address) {
	         this.address = address;
	     }
	     public long getAddress() {
	         return address;
	     }

	    public void setIdentification(int identification) {
	         this.identification = identification;
	     }
	     public int getIdentification() {
	         return identification;
	     }

	}
	
	public class Device {

	    private String cloud_gateway_identifier;
	    private String alias;
	    private Connection connection;
	    public void setCloud_gateway_identifier(String cloud_gateway_identifier) {
	         this.cloud_gateway_identifier = cloud_gateway_identifier;
	     }
	     public String getCloud_gateway_identifier() {
	         return cloud_gateway_identifier;
	     }

	    public void setAlias(String alias) {
	         this.alias = alias;
	     }
	     public String getAlias() {
	         return alias;
	     }

	    public void setConnection(Connection connection) {
	         this.connection = connection;
	     }
	     public Connection getConnection() {
	         return connection;
	     }

	}
}
