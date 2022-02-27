package com.hzcloud.group.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 周计划模板对象 acs_week_template
 * 
 * @author zhangfan
 * @date 2021-05-13
 */
public class AcsWeekTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long templateId;

    /** 名称 */
    @Excel(name = "名称")
    private String templateName;

    /** 节日组 */
    @Excel(name = "节日组")
    private Long holidayGroupId;

    /** 周一时段一起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周一时段一起", width = 30, dateFormat = "HH:mm:ss")
    private Date monStartTime1;

    /** 周一时段一止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周一时段一止", width = 30, dateFormat = "HH:mm:ss")
    private Date monEndTime1;

    /** 周一时段二起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周一时段二起", width = 30, dateFormat = "HH:mm:ss")
    private Date monStartTime2;

    /** 周一时段二止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周一时段二止", width = 30, dateFormat = "HH:mm:ss")
    private Date monEndTime2;

    /** 周一时段三起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周一时段三起", width = 30, dateFormat = "HH:mm:ss")
    private Date monStartTime3;

    /** 周一时段三止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周一时段三止", width = 30, dateFormat = "HH:mm:ss")
    private Date monEndTime3;

    /** 周二时段一起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周二时段一起", width = 30, dateFormat = "HH:mm:ss")
    private Date tueStartTime1;

    /** 周二时段一止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周二时段一止", width = 30, dateFormat = "HH:mm:ss")
    private Date tueEndTime1;

    /** 周二时段二起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周二时段二起", width = 30, dateFormat = "HH:mm:ss")
    private Date tueStartTime2;

    /** 周二时段二止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周二时段二止", width = 30, dateFormat = "HH:mm:ss")
    private Date tueEndTime2;

    /** 周二时段三起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周二时段三起", width = 30, dateFormat = "HH:mm:ss")
    private Date tueStartTime3;

    /** 周二时段三止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周二时段三止", width = 30, dateFormat = "HH:mm:ss")
    private Date tueEndTime3;

    /** 周三时段一起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周三时段一起", width = 30, dateFormat = "HH:mm:ss")
    private Date wenStartTime1;

    /** 周三时段一止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周三时段一止", width = 30, dateFormat = "HH:mm:ss")
    private Date wenEndTime1;

    /** 周三时段二起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周三时段二起", width = 30, dateFormat = "HH:mm:ss")
    private Date wenStartTime2;

    /** 周三时段二止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周三时段二止", width = 30, dateFormat = "HH:mm:ss")
    private Date wenEndTime2;

    /** 周三时段三起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周三时段三起", width = 30, dateFormat = "HH:mm:ss")
    private Date wenStartTime3;

    /** 周三时段三止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周三时段三止", width = 30, dateFormat = "HH:mm:ss")
    private Date wenEndTime3;

    /** 周四时段一起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周四时段一起", width = 30, dateFormat = "HH:mm:ss")
    private Date turStartTime1;

    /** 周四时段一止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周四时段一止", width = 30, dateFormat = "HH:mm:ss")
    private Date turEndTime1;

    /** 周四时段二起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周四时段二起", width = 30, dateFormat = "HH:mm:ss")
    private Date turStartTime2;

    /** 周四时段二止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周四时段二止", width = 30, dateFormat = "HH:mm:ss")
    private Date turEndTime2;

    /** 周四时段三起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周四时段三起", width = 30, dateFormat = "HH:mm:ss")
    private Date turStartTime3;

    /** 周四时段三止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周四时段三止", width = 30, dateFormat = "HH:mm:ss")
    private Date turEndTime3;

    /** 周五时段一起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周五时段一起", width = 30, dateFormat = "HH:mm:ss")
    private Date friStartTime1;

    /** 周五时段一止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周五时段一止", width = 30, dateFormat = "HH:mm:ss")
    private Date friEndTime1;

    /** 周五时段二起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周五时段二起", width = 30, dateFormat = "HH:mm:ss")
    private Date friStartTime2;

    /** 周五时段二起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周五时段二起", width = 30, dateFormat = "HH:mm:ss")
    private Date friEndTime2;

    /** 周五时段三起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周五时段三起", width = 30, dateFormat = "HH:mm:ss")
    private Date friStartTime3;

    /** 周五时段三止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周五时段三止", width = 30, dateFormat = "HH:mm:ss")
    private Date friEndTime3;

    /** 周六时段一起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周六时段一起", width = 30, dateFormat = "HH:mm:ss")
    private Date satStartTime1;

    /** 周六时段一止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周六时段一止", width = 30, dateFormat = "HH:mm:ss")
    private Date satEndTime1;

    /** 周六时段二起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周六时段二起", width = 30, dateFormat = "HH:mm:ss")
    private Date satStartTime2;

    /** 周六时段二止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周六时段二止", width = 30, dateFormat = "HH:mm:ss")
    private Date satEndTime2;

    /** 周六时段三起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周六时段三起", width = 30, dateFormat = "HH:mm:ss")
    private Date satStartTime3;

    /** 周六时段三止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周六时段三止", width = 30, dateFormat = "HH:mm:ss")
    private Date satEndTime3;

    /** 周日时段一起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周日时段一起", width = 30, dateFormat = "HH:mm:ss")
    private Date sunStartTime1;

    /** 周日时段一止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周日时段一止", width = 30, dateFormat = "HH:mm:ss")
    private Date sunEndTime1;

    /** 周日时段二起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周日时段二起", width = 30, dateFormat = "HH:mm:ss")
    private Date sunStartTime2;

    /** 周日时段二止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周日时段二止", width = 30, dateFormat = "HH:mm:ss")
    private Date sunEndTime2;

    /** 周日时段三起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周日时段三起", width = 30, dateFormat = "HH:mm:ss")
    private Date sunStartTime3;

    /** 周日时段三止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "周日时段三止", width = 30, dateFormat = "HH:mm:ss")
    private Date sunEndTime3;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 备注 */
    private String remake;

    public void setTemplateId(Long templateId) 
    {
        this.templateId = templateId;
    }

    public Long getTemplateId() 
    {
        return templateId;
    }
    public void setTemplateName(String templateName) 
    {
        this.templateName = templateName;
    }

    public String getTemplateName() 
    {
        return templateName;
    }
    public void setHolidayGroupId(Long holidayGroupId) 
    {
        this.holidayGroupId = holidayGroupId;
    }

    public Long getHolidayGroupId() 
    {
        return holidayGroupId;
    }
    public void setMonStartTime1(Date monStartTime1) 
    {
        this.monStartTime1 = monStartTime1;
    }

    public Date getMonStartTime1() 
    {
        return monStartTime1;
    }
    public void setMonEndTime1(Date monEndTime1) 
    {
        this.monEndTime1 = monEndTime1;
    }

    public Date getMonEndTime1() 
    {
        return monEndTime1;
    }
    public void setMonStartTime2(Date monStartTime2) 
    {
        this.monStartTime2 = monStartTime2;
    }

    public Date getMonStartTime2() 
    {
        return monStartTime2;
    }
    public void setMonEndTime2(Date monEndTime2) 
    {
        this.monEndTime2 = monEndTime2;
    }

    public Date getMonEndTime2() 
    {
        return monEndTime2;
    }
    public void setMonStartTime3(Date monStartTime3) 
    {
        this.monStartTime3 = monStartTime3;
    }

    public Date getMonStartTime3() 
    {
        return monStartTime3;
    }
    public void setMonEndTime3(Date monEndTime3) 
    {
        this.monEndTime3 = monEndTime3;
    }

    public Date getMonEndTime3() 
    {
        return monEndTime3;
    }
    public void setTueStartTime1(Date tueStartTime1) 
    {
        this.tueStartTime1 = tueStartTime1;
    }

    public Date getTueStartTime1() 
    {
        return tueStartTime1;
    }
    public void setTueEndTime1(Date tueEndTime1) 
    {
        this.tueEndTime1 = tueEndTime1;
    }

    public Date getTueEndTime1() 
    {
        return tueEndTime1;
    }
    public void setTueStartTime2(Date tueStartTime2) 
    {
        this.tueStartTime2 = tueStartTime2;
    }

    public Date getTueStartTime2() 
    {
        return tueStartTime2;
    }
    public void setTueEndTime2(Date tueEndTime2) 
    {
        this.tueEndTime2 = tueEndTime2;
    }

    public Date getTueEndTime2() 
    {
        return tueEndTime2;
    }
    public void setTueStartTime3(Date tueStartTime3) 
    {
        this.tueStartTime3 = tueStartTime3;
    }

    public Date getTueStartTime3() 
    {
        return tueStartTime3;
    }
    public void setTueEndTime3(Date tueEndTime3) 
    {
        this.tueEndTime3 = tueEndTime3;
    }

    public Date getTueEndTime3() 
    {
        return tueEndTime3;
    }
    public void setWenStartTime1(Date wenStartTime1) 
    {
        this.wenStartTime1 = wenStartTime1;
    }

    public Date getWenStartTime1() 
    {
        return wenStartTime1;
    }
    public void setWenEndTime1(Date wenEndTime1) 
    {
        this.wenEndTime1 = wenEndTime1;
    }

    public Date getWenEndTime1() 
    {
        return wenEndTime1;
    }
    public void setWenStartTime2(Date wenStartTime2) 
    {
        this.wenStartTime2 = wenStartTime2;
    }

    public Date getWenStartTime2() 
    {
        return wenStartTime2;
    }
    public void setWenEndTime2(Date wenEndTime2) 
    {
        this.wenEndTime2 = wenEndTime2;
    }

    public Date getWenEndTime2() 
    {
        return wenEndTime2;
    }
    public void setWenStartTime3(Date wenStartTime3) 
    {
        this.wenStartTime3 = wenStartTime3;
    }

    public Date getWenStartTime3() 
    {
        return wenStartTime3;
    }
    public void setWenEndTime3(Date wenEndTime3) 
    {
        this.wenEndTime3 = wenEndTime3;
    }

    public Date getWenEndTime3() 
    {
        return wenEndTime3;
    }
    public void setTurStartTime1(Date turStartTime1) 
    {
        this.turStartTime1 = turStartTime1;
    }

    public Date getTurStartTime1() 
    {
        return turStartTime1;
    }
    public void setTurEndTime1(Date turEndTime1) 
    {
        this.turEndTime1 = turEndTime1;
    }

    public Date getTurEndTime1() 
    {
        return turEndTime1;
    }
    public void setTurStartTime2(Date turStartTime2) 
    {
        this.turStartTime2 = turStartTime2;
    }

    public Date getTurStartTime2() 
    {
        return turStartTime2;
    }
    public void setTurEndTime2(Date turEndTime2) 
    {
        this.turEndTime2 = turEndTime2;
    }

    public Date getTurEndTime2() 
    {
        return turEndTime2;
    }
    public void setTurStartTime3(Date turStartTime3) 
    {
        this.turStartTime3 = turStartTime3;
    }

    public Date getTurStartTime3() 
    {
        return turStartTime3;
    }
    public void setTurEndTime3(Date turEndTime3) 
    {
        this.turEndTime3 = turEndTime3;
    }

    public Date getTurEndTime3() 
    {
        return turEndTime3;
    }
    public void setFriStartTime1(Date friStartTime1) 
    {
        this.friStartTime1 = friStartTime1;
    }

    public Date getFriStartTime1() 
    {
        return friStartTime1;
    }
    public void setFriEndTime1(Date friEndTime1) 
    {
        this.friEndTime1 = friEndTime1;
    }

    public Date getFriEndTime1() 
    {
        return friEndTime1;
    }
    public void setFriStartTime2(Date friStartTime2) 
    {
        this.friStartTime2 = friStartTime2;
    }

    public Date getFriStartTime2() 
    {
        return friStartTime2;
    }
    public void setFriEndTime2(Date friEndTime2) 
    {
        this.friEndTime2 = friEndTime2;
    }

    public Date getFriEndTime2() 
    {
        return friEndTime2;
    }
    public void setFriStartTime3(Date friStartTime3) 
    {
        this.friStartTime3 = friStartTime3;
    }

    public Date getFriStartTime3() 
    {
        return friStartTime3;
    }
    public void setFriEndTime3(Date friEndTime3) 
    {
        this.friEndTime3 = friEndTime3;
    }

    public Date getFriEndTime3() 
    {
        return friEndTime3;
    }
    public void setSatStartTime1(Date satStartTime1) 
    {
        this.satStartTime1 = satStartTime1;
    }

    public Date getSatStartTime1() 
    {
        return satStartTime1;
    }
    public void setSatEndTime1(Date satEndTime1) 
    {
        this.satEndTime1 = satEndTime1;
    }

    public Date getSatEndTime1() 
    {
        return satEndTime1;
    }
    public void setSatStartTime2(Date satStartTime2) 
    {
        this.satStartTime2 = satStartTime2;
    }

    public Date getSatStartTime2() 
    {
        return satStartTime2;
    }
    public void setSatEndTime2(Date satEndTime2) 
    {
        this.satEndTime2 = satEndTime2;
    }

    public Date getSatEndTime2() 
    {
        return satEndTime2;
    }
    public void setSatStartTime3(Date satStartTime3) 
    {
        this.satStartTime3 = satStartTime3;
    }

    public Date getSatStartTime3() 
    {
        return satStartTime3;
    }
    public void setSatEndTime3(Date satEndTime3) 
    {
        this.satEndTime3 = satEndTime3;
    }

    public Date getSatEndTime3() 
    {
        return satEndTime3;
    }
    public void setSunStartTime1(Date sunStartTime1) 
    {
        this.sunStartTime1 = sunStartTime1;
    }

    public Date getSunStartTime1() 
    {
        return sunStartTime1;
    }
    public void setSunEndTime1(Date sunEndTime1) 
    {
        this.sunEndTime1 = sunEndTime1;
    }

    public Date getSunEndTime1() 
    {
        return sunEndTime1;
    }
    public void setSunStartTime2(Date sunStartTime2) 
    {
        this.sunStartTime2 = sunStartTime2;
    }

    public Date getSunStartTime2() 
    {
        return sunStartTime2;
    }
    public void setSunEndTime2(Date sunEndTime2) 
    {
        this.sunEndTime2 = sunEndTime2;
    }

    public Date getSunEndTime2() 
    {
        return sunEndTime2;
    }
    public void setSunStartTime3(Date sunStartTime3) 
    {
        this.sunStartTime3 = sunStartTime3;
    }

    public Date getSunStartTime3() 
    {
        return sunStartTime3;
    }
    public void setSunEndTime3(Date sunEndTime3) 
    {
        this.sunEndTime3 = sunEndTime3;
    }

    public Date getSunEndTime3() 
    {
        return sunEndTime3;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setRemake(String remake) 
    {
        this.remake = remake;
    }

    public String getRemake() 
    {
        return remake;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("templateId", getTemplateId())
            .append("templateName", getTemplateName())
            .append("holidayGroupId", getHolidayGroupId())
            .append("monStartTime1", getMonStartTime1())
            .append("monEndTime1", getMonEndTime1())
            .append("monStartTime2", getMonStartTime2())
            .append("monEndTime2", getMonEndTime2())
            .append("monStartTime3", getMonStartTime3())
            .append("monEndTime3", getMonEndTime3())
            .append("tueStartTime1", getTueStartTime1())
            .append("tueEndTime1", getTueEndTime1())
            .append("tueStartTime2", getTueStartTime2())
            .append("tueEndTime2", getTueEndTime2())
            .append("tueStartTime3", getTueStartTime3())
            .append("tueEndTime3", getTueEndTime3())
            .append("wenStartTime1", getWenStartTime1())
            .append("wenEndTime1", getWenEndTime1())
            .append("wenStartTime2", getWenStartTime2())
            .append("wenEndTime2", getWenEndTime2())
            .append("wenStartTime3", getWenStartTime3())
            .append("wenEndTime3", getWenEndTime3())
            .append("turStartTime1", getTurStartTime1())
            .append("turEndTime1", getTurEndTime1())
            .append("turStartTime2", getTurStartTime2())
            .append("turEndTime2", getTurEndTime2())
            .append("turStartTime3", getTurStartTime3())
            .append("turEndTime3", getTurEndTime3())
            .append("friStartTime1", getFriStartTime1())
            .append("friEndTime1", getFriEndTime1())
            .append("friStartTime2", getFriStartTime2())
            .append("friEndTime2", getFriEndTime2())
            .append("friStartTime3", getFriStartTime3())
            .append("friEndTime3", getFriEndTime3())
            .append("satStartTime1", getSatStartTime1())
            .append("satEndTime1", getSatEndTime1())
            .append("satStartTime2", getSatStartTime2())
            .append("satEndTime2", getSatEndTime2())
            .append("satStartTime3", getSatStartTime3())
            .append("satEndTime3", getSatEndTime3())
            .append("sunStartTime1", getSunStartTime1())
            .append("sunEndTime1", getSunEndTime1())
            .append("sunStartTime2", getSunStartTime2())
            .append("sunEndTime2", getSunEndTime2())
            .append("sunStartTime3", getSunStartTime3())
            .append("sunEndTime3", getSunEndTime3())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("remake", getRemake())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
