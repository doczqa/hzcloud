package com.hzcloud.device.syris.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class utils {
    static public Long getIpLong(String IPStr){
        String[] ipslices = IPStr.split(".");
        if(ipslices.length != 4){
            return (long)0;
        }
        long ip =  (Long.valueOf(ipslices[3]).longValue() << 24) +  (Long.valueOf(ipslices[2]).longValue() << 16) +  (Long.valueOf(ipslices[1]).longValue() << 8) + Long.valueOf(ipslices[0]).longValue();
        return ip;
    }

    static public Map<Integer ,List<Integer>> HolidayDateHandler(Date startDate,Date endDate){
        Map<Integer ,List<Integer>> dateMap = new HashMap<Integer ,List<Integer>>();
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(startDate);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(endDate);
        while (startDate.before(calEnd.getTime())) {
            int month = calEnd.get(Calendar.MONTH) + 1;
            int day = calEnd.get(Calendar.DATE);
            if(!dateMap.containsKey(month)) {
                List<Integer> dateList = new ArrayList<Integer>();
                dateMap.put(month, dateList);
            }
            dateMap.get(month).add(day);
            calEnd.add(Calendar.DAY_OF_MONTH, -1);
        }
        return dateMap;
    }

    static public Long getTimeStamp(Date date){
        if (date == null){
            return (long)0;
        }
        Long stampms = date.getTime() - (new Date( 2000 -1900,0,1)).getTime();
        String timestamp = String.valueOf(stampms/1000);  
        return Long.valueOf(timestamp);
    }

    // 判断奇数或偶数，位运算，最后一位是1则为奇数，为0是偶数
    public static int isOdd(int num) {
        return num & 1;
    }

    //Hex字符串转byte
    public static byte HexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }
    
    public static byte[] HexToByteArr(String inHex) {
        byte[] result;
        int hexlen = inHex.length();
        if (isOdd(hexlen) == 1) {
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = HexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    public static String StringToHex(String str) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            strBuilder.append(Byte2Hex(Byte.valueOf(str.getBytes()[i])));
        }
        return strBuilder.toString();
    }

    //1字节转2个Hex字符
    public static String Byte2Hex(Byte inByte) {
        return String.format("%02x", new Object[]{inByte}).toUpperCase();
    }

    public static String V6Error(byte b){
        switch(b){
        case 'A':
            return "传送无效控制码";
        case 'B':
            return "传送资料或资料格式错误";
        case 'C':
            return "资料库错误信息";
        case 'D':
            return "CRC错误";
        case 'E':
            return "内存错误";
        case 'F':
            return "设定或读取保护中资料";
        case 'G':
            return "其它错误";
        default:
            return "未知错误";
        }
    }
}
