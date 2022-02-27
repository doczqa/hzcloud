package com.hzcloud.v6.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 基本设置对象 acs_v6_appgroup_setting
 * 
 * @author zhangfan
 * @date 2021-07-16
 */
public class AcsV6AppgroupSetting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 应用群组 */
    private String id;

    /** 控制门区 */
    @Excel(name = "控制门区")
    private String door;

    /**门区描述 */
    private String doorDetail;

    /** 直接外出 */
    @Excel(name = "直接外出")
    private String doorOut;

    /** 安全密码检查时区门区一 */
    @Excel(name = "安全密码检查时区门区一")
    private String secPinTz1;

    /** 安全密码检查时区门区二 */
    @Excel(name = "安全密码检查时区门区二")
    private String secPinTz2;

    /** 安全密码检查时区门区三 */
    @Excel(name = "安全密码检查时区门区三")
    private String secPinTz3;

    /** 安全密码检查时区门区四 */
    @Excel(name = "安全密码检查时区门区四")
    private String secPinTz4;

    /** 个人密码检查时区门区一 */
    @Excel(name = "个人密码检查时区门区一")
    private String perPinTz1;

    /** 个人密码检查时区门区二 */
    @Excel(name = "个人密码检查时区门区二")
    private String perPinTz2;

    /** 个人密码检查时区门区三 */
    @Excel(name = "个人密码检查时区门区三")
    private String perPinTz3;

    /** 个人密码检查时区门区四 */
    @Excel(name = "个人密码检查时区门区四")
    private String perPinTz4;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setDoor(String door) 
    {
        this.door = door;
    }

    public String getDoor() 
    {
        return door;
    }

    public void setDoorDetail(String doorDetail) 
    {
        this.doorDetail = doorDetail;
    }

    public String getDoorDetail() 
    {
        return doorDetail;
    }

    public void setDoorOut(String doorOut) 
    {
        this.doorOut = doorOut;
    }

    public String getDoorOut() 
    {
        return doorOut;
    }
    public void setSecPinTz1(String secPinTz1) 
    {
        this.secPinTz1 = secPinTz1;
    }

    public String getSecPinTz1() 
    {
        return secPinTz1;
    }
    public void setSecPinTz2(String secPinTz2) 
    {
        this.secPinTz2 = secPinTz2;
    }

    public String getSecPinTz2() 
    {
        return secPinTz2;
    }
    public void setSecPinTz3(String secPinTz3) 
    {
        this.secPinTz3 = secPinTz3;
    }

    public String getSecPinTz3() 
    {
        return secPinTz3;
    }
    public void setSecPinTz4(String secPinTz4) 
    {
        this.secPinTz4 = secPinTz4;
    }

    public String getSecPinTz4() 
    {
        return secPinTz4;
    }
    public void setPerPinTz1(String perPinTz1) 
    {
        this.perPinTz1 = perPinTz1;
    }

    public String getPerPinTz1() 
    {
        return perPinTz1;
    }
    public void setPerPinTz2(String perPinTz2) 
    {
        this.perPinTz2 = perPinTz2;
    }

    public String getPerPinTz2() 
    {
        return perPinTz2;
    }
    public void setPerPinTz3(String perPinTz3) 
    {
        this.perPinTz3 = perPinTz3;
    }

    public String getPerPinTz3() 
    {
        return perPinTz3;
    }
    public void setPerPinTz4(String perPinTz4) 
    {
        this.perPinTz4 = perPinTz4;
    }

    public String getPerPinTz4() 
    {
        return perPinTz4;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("door", getDoor())
            .append("doorDetail", getDoorDetail())
            .append("doorOut", getDoorOut())
            .append("secPinTz1", getSecPinTz1())
            .append("secPinTz2", getSecPinTz2())
            .append("secPinTz3", getSecPinTz3())
            .append("secPinTz4", getSecPinTz4())
            .append("perPinTz1", getPerPinTz1())
            .append("perPinTz2", getPerPinTz2())
            .append("perPinTz3", getPerPinTz3())
            .append("perPinTz4", getPerPinTz4())
            .toString();
    }
}
