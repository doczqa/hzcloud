package com.hzcloud.system.domain.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String nickName;
    /**
     * 用户状态
     */
    private String status;
    /**
     * 部门
     */
    private String deptName;
    /**
     * 岗位
     */
    private String postName;
    /**
     * 人脸信息
     */
    private String faceInfo;
    /**
     * 卡片索引
     */
    private Long cardId;
    /**
     * 卡号
     */
    private String cardNumber;
    /**
     * 有效期起
     */
    private Date expirationStartTime;
    /**
     * 有效期止
     */
    private Date expirationEndTime;

    private String cardIndex;

    /**
     * 卡状态
     */
    private String cardStatus;

    /**
     * 证件类型
     */
    private String idType;

    /**
     * 证件号
     */
    private String idNumber;

}
