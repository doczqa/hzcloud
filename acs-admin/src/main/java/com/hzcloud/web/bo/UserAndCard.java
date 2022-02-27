package com.hzcloud.web.bo;

import java.util.Date;

import lombok.Data;

@Data
public class UserAndCard {

    /** 部门ID */
    private Long deptId;

    /** 用户账号 */
    private String userName;

    /** 用户昵称 */
    private String nickName;

    /** 证件类型 */
    private String idType;

    /** 证件号 */
    private String idNumber;

    /** 身份类型 */
    private Long identityId;

    /** 人脸信息 */
    private String faceInfo;

    /** 手机号码 */
    private String phonenumber;

    /** 用户性别 */
    private String sex;

    /** 地址 */
    private String address;

    /** 密码 */
    private String password;

    /** 角色组 */
    private Long[] roleIds;

    /** 帐号状态（0正常 1停用） */
    private String status;

    /** 卡号 */
    private String cardNumber;

    /**有效期结束*/
    private Date expirationEndTime;

    private String remark;
    /** 卡状态 */
    private String cardStatus;
}
