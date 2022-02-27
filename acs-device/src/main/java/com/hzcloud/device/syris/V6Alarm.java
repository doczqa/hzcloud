package com.hzcloud.device.syris;

import lombok.Data;

@Data
public class V6Alarm {
    private String alarmType;
    private String alarmDetail;
    
    public V6Alarm(String flag){
        char marjor = flag.charAt(0);
        char minor = flag.charAt(4);
        switch(marjor){
        case 'A':
            alarmType = "5";
            switch(minor){
            case '1':
                alarmDetail = "进门卡片检查正确";
                break;
            case '2':
                alarmDetail = "出门卡片检查正确";
                break;
            default:
                alarmDetail = "未知";
            }
            break;
        case 'B':
            alarmType = "5";
            switch(minor){
            case '1':
                alarmDetail = "卡片暂停使用";
                break;
            case '2':
                alarmDetail = "无效卡片";
                break;
            case '3':
                alarmDetail = "无效门区";
                break;
            case '4':
                alarmDetail = "无效通行时区";
                break;
            case '5':
                alarmDetail = "重复进入";
                break;
            case '6':
                alarmDetail = "重复外出";
                break;
            case '7':
                alarmDetail = "无效密码输入";
                break;
            default:
                alarmDetail = "未知";
            }
            break;
        case 'C':
            alarmType = "1";
            switch(minor){
            case '1':
                alarmDetail = "防盗警报";
                break;
            case '2':
                alarmDetail = "紧急求救警报";
                break;
            case '3':
                alarmDetail = "火灾警报";
                break;
            case '4':
                alarmDetail = "煤气外泄警报";
                break;
            case '5':
                alarmDetail = "故障警报";
                break;
            case '6':
                alarmDetail = "强行进入警报";
                break;
            case '7':
                alarmDetail = "解除警报";
                break;
            default:
                alarmDetail = "未知";
            }
            break;        
        case 'D':
            alarmType = "3";
            switch(minor){
            case '1':
                alarmDetail = "主卡登入";
                break;
            case '2':
                alarmDetail = "工程师卡登入";
                break;
            case '3':
                alarmDetail = "操作员卡登陆";
                break;
            default:
                alarmDetail = "未知";
            }
            break;  
        case 'E':
            alarmType = "5";
            switch(minor){
            case '1':
                alarmDetail = "开门";
                break;
            case '2':
                alarmDetail = "关门";
                break;            
            default:
                alarmDetail = "未知";
            }
            break; 
        case 'F':
            alarmType = "5";
            switch(minor){
            case '1':
                alarmDetail = "输入点ON";
                break;
            case '2':
                alarmDetail = "输入点OFF";
                break;            
            default:
                alarmDetail = "未知";
            }
            break;
        case 'G':
            alarmType = "2";
            alarmDetail = "错误记录";
            break;
        default:
            alarmType = null;
            alarmDetail = null;
        }
    }
}
