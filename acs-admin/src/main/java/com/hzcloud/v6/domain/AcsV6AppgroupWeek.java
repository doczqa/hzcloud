package com.hzcloud.v6.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 周计划模板对象 acs_v6_appgroup_week
 * 
 * @author zhangfan
 * @date 2021-07-16
 */
public class AcsV6AppgroupWeek extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 应用群组 */
    private String id;

    /** 周日时区 */
    @Excel(name = "周日时区")
    private String sunTz;

    /** 周日时区直接外出 */
    @Excel(name = "周日时区直接外出")
    private String sunTzOut;

    /** 周一时区 */
    @Excel(name = "周一时区")
    private String monTz;

    /** 周一时区直接外出 */
    @Excel(name = "周一时区直接外出")
    private String monTzOut;

    /** 周二时区 */
    @Excel(name = "周二时区")
    private String tueTz;

    /** 周二时区直接外出 */
    @Excel(name = "周二时区直接外出")
    private String tueTzOut;

    /** 周三时区 */
    @Excel(name = "周三时区")
    private String wedTz;

    /** 周三时区直接外出 */
    @Excel(name = "周三时区直接外出")
    private String wedTzOut;

    /** 周四时区 */
    @Excel(name = "周四时区")
    private String thuTz;

    /** 周四时区直接外出 */
    @Excel(name = "周四时区直接外出")
    private String thuTzOut;

    /** 周五时区 */
    @Excel(name = "周五时区")
    private String friTz;

    /** 周五时区直接外出 */
    @Excel(name = "周五时区直接外出")
    private String friTzOut;

    /** 周六时区 */
    @Excel(name = "周六时区")
    private String satTz;

    /** 周六时区直接外出 */
    @Excel(name = "周六时区直接外出")
    private String satTzOut;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setSunTz(String sunTz) 
    {
        this.sunTz = sunTz;
    }

    public String getSunTz() 
    {
        return sunTz;
    }
    public void setSunTzOut(String sunTzOut) 
    {
        this.sunTzOut = sunTzOut;
    }

    public String getSunTzOut() 
    {
        return sunTzOut;
    }
    public void setMonTz(String monTz) 
    {
        this.monTz = monTz;
    }

    public String getMonTz() 
    {
        return monTz;
    }
    public void setMonTzOut(String monTzOut) 
    {
        this.monTzOut = monTzOut;
    }

    public String getMonTzOut() 
    {
        return monTzOut;
    }
    public void setTueTz(String tueTz) 
    {
        this.tueTz = tueTz;
    }

    public String getTueTz() 
    {
        return tueTz;
    }
    public void setTueTzOut(String tueTzOut) 
    {
        this.tueTzOut = tueTzOut;
    }

    public String getTueTzOut() 
    {
        return tueTzOut;
    }
    public void setWedTz(String wedTz) 
    {
        this.wedTz = wedTz;
    }

    public String getWedTz() 
    {
        return wedTz;
    }
    public void setWedTzOut(String wedTzOut) 
    {
        this.wedTzOut = wedTzOut;
    }

    public String getWedTzOut() 
    {
        return wedTzOut;
    }
    public void setThuTz(String thuTz) 
    {
        this.thuTz = thuTz;
    }

    public String getThuTz() 
    {
        return thuTz;
    }
    public void setThuTzOut(String thuTzOut) 
    {
        this.thuTzOut = thuTzOut;
    }

    public String getThuTzOut() 
    {
        return thuTzOut;
    }
    public void setFriTz(String friTz) 
    {
        this.friTz = friTz;
    }

    public String getFriTz() 
    {
        return friTz;
    }
    public void setFriTzOut(String friTzOut) 
    {
        this.friTzOut = friTzOut;
    }

    public String getFriTzOut() 
    {
        return friTzOut;
    }
    public void setSatTz(String satTz) 
    {
        this.satTz = satTz;
    }

    public String getSatTz() 
    {
        return satTz;
    }
    public void setSatTzOut(String satTzOut) 
    {
        this.satTzOut = satTzOut;
    }

    public String getSatTzOut() 
    {
        return satTzOut;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sunTz", getSunTz())
            .append("sunTzOut", getSunTzOut())
            .append("monTz", getMonTz())
            .append("monTzOut", getMonTzOut())
            .append("tueTz", getTueTz())
            .append("tueTzOut", getTueTzOut())
            .append("wedTz", getWedTz())
            .append("wedTzOut", getWedTzOut())
            .append("thuTz", getThuTz())
            .append("thuTzOut", getThuTzOut())
            .append("friTz", getFriTz())
            .append("friTzOut", getFriTzOut())
            .append("satTz", getSatTz())
            .append("satTzOut", getSatTzOut())
            .toString();
    }
}
