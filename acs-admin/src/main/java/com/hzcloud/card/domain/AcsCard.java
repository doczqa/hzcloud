package com.hzcloud.card.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.annotation.Excel.Type;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 卡片管理对象 acs_card
 * 
 * @author zhangfan
 * @date 2021-04-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcsCard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long cardId;

    /** 卡号 */
    @Excel(name = "卡号")
    private String cardNumber;

    /** 用户id */
    private Long userId;

    /** 持卡人 */
    @Excel(name = "持卡人")
    private String userName;

    /** 部门 */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 状态 */
    private String status;

    /** 证件类型 */
    @Excel(name = "证件类型", readConverterExp = "0=居民身份证,1=港澳台居住证,2=外国人居留证,3=学工号")
    private String idType;

    /** 证件号 */
    @Excel(name = "证件号")
    private String idNumber;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 电话 */
    @Excel(name = "电话")
    private String phonenumber;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 身份类型 */
    @Excel(name = "身份类型")
    private Long identityId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd", type = Type.EXPORT)
    private Date createTime;

    /** 创建者 */
    @Excel(name = "创建者", type = Type.EXPORT)
    private String createBy;

    /** 注销时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注销时间", width = 30, dateFormat = "yyyy-MM-dd", type = Type.EXPORT)
    private Date cancellingTime;

    /** 注销者 */
    @Excel(name = "注销者", type = Type.EXPORT)
    private String cancellingBy;

    /**
     *有效期开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expirationStartTime;

    /**
     * 有效期结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expirationEndTime;

    private boolean seen = false;
}
