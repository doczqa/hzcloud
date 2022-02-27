package com.hzcloud.card.bo;

import java.util.Date;

import lombok.Data;

@Data
public class AcsUserImportInfo {
    /** 部门ID */
    private Long deptId;

    /** 用户姓名 */
    private String nickName;

    /** 证件类型 */
    private String idType;

    /** 证件号 */
    private String idNumber;

    /** 身份类型 */
    private Long identityId;

    /** 手机号码 */
    private String phonenumber;

    /** 用户性别 */
    private String sex;

    /** 地址 */
    private String address;

    /** 卡号 */
    private String cardNumber;

    /**有效期结束*/
    private Date expirationEndTime;
}
