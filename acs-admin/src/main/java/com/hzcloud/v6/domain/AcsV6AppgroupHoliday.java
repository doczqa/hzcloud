package com.hzcloud.v6.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 假日计划对象 acs_v6_appgroup_holiday
 * 
 * @author zhangfan
 * @date 2021-07-16
 */
public class AcsV6AppgroupHoliday extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 应用群组 */
    private String id;

    /** 假期前一天通行时区一 */
    @Excel(name = "假期前一天通行时区一")
    private String preHolidayTz1;

    /** 假期前一天通行时区一直接外出 */
    @Excel(name = "假期前一天通行时区一直接外出")
    private String preHolidayTz1Out;

    /** 假期前一天通行时区二 */
    @Excel(name = "假期前一天通行时区二")
    private String preHolidayTz2;

    /** 假期前一天通行时区二直接外出 */
    @Excel(name = "假期前一天通行时区二直接外出")
    private String preHolidayTz2Out;

    /** 假期前一天通行时区三 */
    @Excel(name = "假期前一天通行时区三")
    private String preHolidayTz3;

    /** 假期前一天通行时区三直接外出 */
    @Excel(name = "假期前一天通行时区三直接外出")
    private String preHolidayTz3Out;

    /** 假期前一天通行时区四 */
    @Excel(name = "假期前一天通行时区四")
    private String preHolidayTz4;

    /** 假期前一天通行时区四直接外出 */
    @Excel(name = "假期前一天通行时区四直接外出")
    private String preHolidayTz4Out;

    /** 假期前一天通行时区五 */
    @Excel(name = "假期前一天通行时区五")
    private String preHolidayTz5;

    /** 假期前一天通行时区五直接外出 */
    @Excel(name = "假期前一天通行时区五直接外出")
    private String preHolidayTz5Out;

    /** 假期前一天通行时区六 */
    @Excel(name = "假期前一天通行时区六")
    private String preHolidayTz6;

    /** 假期前一天通行时区六直接外出 */
    @Excel(name = "假期前一天通行时区六直接外出")
    private String preHolidayTz6Out;

    /** 假期前一天通行时区七 */
    @Excel(name = "假期前一天通行时区七")
    private String preHolidayTz7;

    /** 假期前一天通行时区七直接外出 */
    @Excel(name = "假期前一天通行时区七直接外出")
    private String preHolidayTz7Out;

    /** 假期前一天通行时区八 */
    @Excel(name = "假期前一天通行时区八")
    private String preHolidayTz8;

    /** 假期前一天通行时区八直接外出 */
    @Excel(name = "假期前一天通行时区八直接外出")
    private String preHolidayTz8Out;

    /** 假期通行时区一 */
    @Excel(name = "假期通行时区一")
    private String holidayTz1;

    /** 假期通行时区一直接外出 */
    @Excel(name = "假期通行时区一直接外出")
    private String holidayTz1Out;

    /** 假期通行时区二 */
    @Excel(name = "假期通行时区二")
    private String holidayTz2;

    /** 假期通行时区二直接外出 */
    @Excel(name = "假期通行时区二直接外出")
    private String holidayTz2Out;

    /** 假期通行时区三 */
    @Excel(name = "假期通行时区三")
    private String holidayTz3;

    /** 假期通行时区三直接外出 */
    @Excel(name = "假期通行时区三直接外出")
    private String holidayTz3Out;

    /** 假期通行时区四 */
    @Excel(name = "假期通行时区四")
    private String holidayTz4;

    /** 假期通行时区四直接外出 */
    @Excel(name = "假期通行时区四直接外出")
    private String holidayTz4Out;

    /** 假期通行时区五 */
    @Excel(name = "假期通行时区五")
    private String holidayTz5;

    /** 假期通行时区五直接外出 */
    @Excel(name = "假期通行时区五直接外出")
    private String holidayTz5Out;

    /** 假期通行时区六 */
    @Excel(name = "假期通行时区六")
    private String holidayTz6;

    /** 假期通行时区六直接外出 */
    @Excel(name = "假期通行时区六直接外出")
    private String holidayTz6Out;

    /** 假期通行时区七 */
    @Excel(name = "假期通行时区七")
    private String holidayTz7;

    /** 假期通行时区七直接外出 */
    @Excel(name = "假期通行时区七直接外出")
    private String holidayTz7Out;

    /** 假期通行时区八 */
    @Excel(name = "假期通行时区八")
    private String holidayTz8;

    /** 假期通行时区八直接外出 */
    @Excel(name = "假期通行时区八直接外出")
    private String holidayTz8Out;

    /** 假期后一天通行时区一 */
    @Excel(name = "假期后一天通行时区一")
    private String postHolidayTz1;

    /** 假期后一天通行时区一直接外出 */
    @Excel(name = "假期后一天通行时区一直接外出")
    private String postHolidayTz1Out;

    /** 假期后一天通行时区二 */
    @Excel(name = "假期后一天通行时区二")
    private String postHolidayTz2;

    /** 假期后一天通行时区二直接外出 */
    @Excel(name = "假期后一天通行时区二直接外出")
    private String postHolidayTz2Out;

    /** 假期后一天通行时区三 */
    @Excel(name = "假期后一天通行时区三")
    private String postHolidayTz3;

    /** 假期后一天通行时区三直接外出 */
    @Excel(name = "假期后一天通行时区三直接外出")
    private String postHolidayTz3Out;

    /** 假期后一天通行时区四 */
    @Excel(name = "假期后一天通行时区四")
    private String postHolidayTz4;

    /** 假期后一天通行时区四直接外出 */
    @Excel(name = "假期后一天通行时区四直接外出")
    private String postHolidayTz4Out;

    /** 假期后一天通行时区五 */
    @Excel(name = "假期后一天通行时区五")
    private String postHolidayTz5;

    /** 假期后一天通行时区五直接外出 */
    @Excel(name = "假期后一天通行时区五直接外出")
    private String postHolidayTz5Out;

    /** 假期后一天通行时区六 */
    @Excel(name = "假期后一天通行时区六")
    private String postHolidayTz6;

    /** 假期后一天通行时区六直接外出 */
    @Excel(name = "假期后一天通行时区六直接外出")
    private String postHolidayTz6Out;

    /** 假期后一天通行时区七 */
    @Excel(name = "假期后一天通行时区七")
    private String postHolidayTz7;

    /** 假期后一天通行时区七直接外出 */
    @Excel(name = "假期后一天通行时区七直接外出")
    private String postHolidayTz7Out;

    /** 假期后一天通行时区八 */
    @Excel(name = "假期后一天通行时区八")
    private String postHolidayTz8;

    /** 假期后一天通行时区八直接外出 */
    @Excel(name = "假期后一天通行时区八直接外出")
    private String postHolidayTz8Out;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setPreHolidayTz1(String preHolidayTz1) 
    {
        this.preHolidayTz1 = preHolidayTz1;
    }

    public String getPreHolidayTz1() 
    {
        return preHolidayTz1;
    }
    public void setPreHolidayTz1Out(String preHolidayTz1Out) 
    {
        this.preHolidayTz1Out = preHolidayTz1Out;
    }

    public String getPreHolidayTz1Out() 
    {
        return preHolidayTz1Out;
    }
    public void setPreHolidayTz2(String preHolidayTz2) 
    {
        this.preHolidayTz2 = preHolidayTz2;
    }

    public String getPreHolidayTz2() 
    {
        return preHolidayTz2;
    }
    public void setPreHolidayTz2Out(String preHolidayTz2Out) 
    {
        this.preHolidayTz2Out = preHolidayTz2Out;
    }

    public String getPreHolidayTz2Out() 
    {
        return preHolidayTz2Out;
    }
    public void setPreHolidayTz3(String preHolidayTz3) 
    {
        this.preHolidayTz3 = preHolidayTz3;
    }

    public String getPreHolidayTz3() 
    {
        return preHolidayTz3;
    }
    public void setPreHolidayTz3Out(String preHolidayTz3Out) 
    {
        this.preHolidayTz3Out = preHolidayTz3Out;
    }

    public String getPreHolidayTz3Out() 
    {
        return preHolidayTz3Out;
    }
    public void setPreHolidayTz4(String preHolidayTz4) 
    {
        this.preHolidayTz4 = preHolidayTz4;
    }

    public String getPreHolidayTz4() 
    {
        return preHolidayTz4;
    }
    public void setPreHolidayTz4Out(String preHolidayTz4Out) 
    {
        this.preHolidayTz4Out = preHolidayTz4Out;
    }

    public String getPreHolidayTz4Out() 
    {
        return preHolidayTz4Out;
    }
    public void setPreHolidayTz5(String preHolidayTz5) 
    {
        this.preHolidayTz5 = preHolidayTz5;
    }

    public String getPreHolidayTz5() 
    {
        return preHolidayTz5;
    }
    public void setPreHolidayTz5Out(String preHolidayTz5Out) 
    {
        this.preHolidayTz5Out = preHolidayTz5Out;
    }

    public String getPreHolidayTz5Out() 
    {
        return preHolidayTz5Out;
    }
    public void setPreHolidayTz6(String preHolidayTz6) 
    {
        this.preHolidayTz6 = preHolidayTz6;
    }

    public String getPreHolidayTz6() 
    {
        return preHolidayTz6;
    }
    public void setPreHolidayTz6Out(String preHolidayTz6Out) 
    {
        this.preHolidayTz6Out = preHolidayTz6Out;
    }

    public String getPreHolidayTz6Out() 
    {
        return preHolidayTz6Out;
    }
    public void setPreHolidayTz7(String preHolidayTz7) 
    {
        this.preHolidayTz7 = preHolidayTz7;
    }

    public String getPreHolidayTz7() 
    {
        return preHolidayTz7;
    }
    public void setPreHolidayTz7Out(String preHolidayTz7Out) 
    {
        this.preHolidayTz7Out = preHolidayTz7Out;
    }

    public String getPreHolidayTz7Out() 
    {
        return preHolidayTz7Out;
    }
    public void setPreHolidayTz8(String preHolidayTz8) 
    {
        this.preHolidayTz8 = preHolidayTz8;
    }

    public String getPreHolidayTz8() 
    {
        return preHolidayTz8;
    }
    public void setPreHolidayTz8Out(String preHolidayTz8Out) 
    {
        this.preHolidayTz8Out = preHolidayTz8Out;
    }

    public String getPreHolidayTz8Out() 
    {
        return preHolidayTz8Out;
    }
    public void setHolidayTz1(String holidayTz1) 
    {
        this.holidayTz1 = holidayTz1;
    }

    public String getHolidayTz1() 
    {
        return holidayTz1;
    }
    public void setHolidayTz1Out(String holidayTz1Out) 
    {
        this.holidayTz1Out = holidayTz1Out;
    }

    public String getHolidayTz1Out() 
    {
        return holidayTz1Out;
    }
    public void setHolidayTz2(String holidayTz2) 
    {
        this.holidayTz2 = holidayTz2;
    }

    public String getHolidayTz2() 
    {
        return holidayTz2;
    }
    public void setHolidayTz2Out(String holidayTz2Out) 
    {
        this.holidayTz2Out = holidayTz2Out;
    }

    public String getHolidayTz2Out() 
    {
        return holidayTz2Out;
    }
    public void setHolidayTz3(String holidayTz3) 
    {
        this.holidayTz3 = holidayTz3;
    }

    public String getHolidayTz3() 
    {
        return holidayTz3;
    }
    public void setHolidayTz3Out(String holidayTz3Out) 
    {
        this.holidayTz3Out = holidayTz3Out;
    }

    public String getHolidayTz3Out() 
    {
        return holidayTz3Out;
    }
    public void setHolidayTz4(String holidayTz4) 
    {
        this.holidayTz4 = holidayTz4;
    }

    public String getHolidayTz4() 
    {
        return holidayTz4;
    }
    public void setHolidayTz4Out(String holidayTz4Out) 
    {
        this.holidayTz4Out = holidayTz4Out;
    }

    public String getHolidayTz4Out() 
    {
        return holidayTz4Out;
    }
    public void setHolidayTz5(String holidayTz5) 
    {
        this.holidayTz5 = holidayTz5;
    }

    public String getHolidayTz5() 
    {
        return holidayTz5;
    }
    public void setHolidayTz5Out(String holidayTz5Out) 
    {
        this.holidayTz5Out = holidayTz5Out;
    }

    public String getHolidayTz5Out() 
    {
        return holidayTz5Out;
    }
    public void setHolidayTz6(String holidayTz6) 
    {
        this.holidayTz6 = holidayTz6;
    }

    public String getHolidayTz6() 
    {
        return holidayTz6;
    }
    public void setHolidayTz6Out(String holidayTz6Out) 
    {
        this.holidayTz6Out = holidayTz6Out;
    }

    public String getHolidayTz6Out() 
    {
        return holidayTz6Out;
    }
    public void setHolidayTz7(String holidayTz7) 
    {
        this.holidayTz7 = holidayTz7;
    }

    public String getHolidayTz7() 
    {
        return holidayTz7;
    }
    public void setHolidayTz7Out(String holidayTz7Out) 
    {
        this.holidayTz7Out = holidayTz7Out;
    }

    public String getHolidayTz7Out() 
    {
        return holidayTz7Out;
    }
    public void setHolidayTz8(String holidayTz8) 
    {
        this.holidayTz8 = holidayTz8;
    }

    public String getHolidayTz8() 
    {
        return holidayTz8;
    }
    public void setHolidayTz8Out(String holidayTz8Out) 
    {
        this.holidayTz8Out = holidayTz8Out;
    }

    public String getHolidayTz8Out() 
    {
        return holidayTz8Out;
    }
    public void setPostHolidayTz1(String postHolidayTz1) 
    {
        this.postHolidayTz1 = postHolidayTz1;
    }

    public String getPostHolidayTz1() 
    {
        return postHolidayTz1;
    }
    public void setPostHolidayTz1Out(String postHolidayTz1Out) 
    {
        this.postHolidayTz1Out = postHolidayTz1Out;
    }

    public String getPostHolidayTz1Out() 
    {
        return postHolidayTz1Out;
    }
    public void setPostHolidayTz2(String postHolidayTz2) 
    {
        this.postHolidayTz2 = postHolidayTz2;
    }

    public String getPostHolidayTz2() 
    {
        return postHolidayTz2;
    }
    public void setPostHolidayTz2Out(String postHolidayTz2Out) 
    {
        this.postHolidayTz2Out = postHolidayTz2Out;
    }

    public String getPostHolidayTz2Out() 
    {
        return postHolidayTz2Out;
    }
    public void setPostHolidayTz3(String postHolidayTz3) 
    {
        this.postHolidayTz3 = postHolidayTz3;
    }

    public String getPostHolidayTz3() 
    {
        return postHolidayTz3;
    }
    public void setPostHolidayTz3Out(String postHolidayTz3Out) 
    {
        this.postHolidayTz3Out = postHolidayTz3Out;
    }

    public String getPostHolidayTz3Out() 
    {
        return postHolidayTz3Out;
    }
    public void setPostHolidayTz4(String postHolidayTz4) 
    {
        this.postHolidayTz4 = postHolidayTz4;
    }

    public String getPostHolidayTz4() 
    {
        return postHolidayTz4;
    }
    public void setPostHolidayTz4Out(String postHolidayTz4Out) 
    {
        this.postHolidayTz4Out = postHolidayTz4Out;
    }

    public String getPostHolidayTz4Out() 
    {
        return postHolidayTz4Out;
    }
    public void setPostHolidayTz5(String postHolidayTz5) 
    {
        this.postHolidayTz5 = postHolidayTz5;
    }

    public String getPostHolidayTz5() 
    {
        return postHolidayTz5;
    }
    public void setPostHolidayTz5Out(String postHolidayTz5Out) 
    {
        this.postHolidayTz5Out = postHolidayTz5Out;
    }

    public String getPostHolidayTz5Out() 
    {
        return postHolidayTz5Out;
    }
    public void setPostHolidayTz6(String postHolidayTz6) 
    {
        this.postHolidayTz6 = postHolidayTz6;
    }

    public String getPostHolidayTz6() 
    {
        return postHolidayTz6;
    }
    public void setPostHolidayTz6Out(String postHolidayTz6Out) 
    {
        this.postHolidayTz6Out = postHolidayTz6Out;
    }

    public String getPostHolidayTz6Out() 
    {
        return postHolidayTz6Out;
    }
    public void setPostHolidayTz7(String postHolidayTz7) 
    {
        this.postHolidayTz7 = postHolidayTz7;
    }

    public String getPostHolidayTz7() 
    {
        return postHolidayTz7;
    }
    public void setPostHolidayTz7Out(String postHolidayTz7Out) 
    {
        this.postHolidayTz7Out = postHolidayTz7Out;
    }

    public String getPostHolidayTz7Out() 
    {
        return postHolidayTz7Out;
    }
    public void setPostHolidayTz8(String postHolidayTz8) 
    {
        this.postHolidayTz8 = postHolidayTz8;
    }

    public String getPostHolidayTz8() 
    {
        return postHolidayTz8;
    }
    public void setPostHolidayTz8Out(String postHolidayTz8Out) 
    {
        this.postHolidayTz8Out = postHolidayTz8Out;
    }

    public String getPostHolidayTz8Out() 
    {
        return postHolidayTz8Out;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("preHolidayTz1", getPreHolidayTz1())
            .append("preHolidayTz1Out", getPreHolidayTz1Out())
            .append("preHolidayTz2", getPreHolidayTz2())
            .append("preHolidayTz2Out", getPreHolidayTz2Out())
            .append("preHolidayTz3", getPreHolidayTz3())
            .append("preHolidayTz3Out", getPreHolidayTz3Out())
            .append("preHolidayTz4", getPreHolidayTz4())
            .append("preHolidayTz4Out", getPreHolidayTz4Out())
            .append("preHolidayTz5", getPreHolidayTz5())
            .append("preHolidayTz5Out", getPreHolidayTz5Out())
            .append("preHolidayTz6", getPreHolidayTz6())
            .append("preHolidayTz6Out", getPreHolidayTz6Out())
            .append("preHolidayTz7", getPreHolidayTz7())
            .append("preHolidayTz7Out", getPreHolidayTz7Out())
            .append("preHolidayTz8", getPreHolidayTz8())
            .append("preHolidayTz8Out", getPreHolidayTz8Out())
            .append("holidayTz1", getHolidayTz1())
            .append("holidayTz1Out", getHolidayTz1Out())
            .append("holidayTz2", getHolidayTz2())
            .append("holidayTz2Out", getHolidayTz2Out())
            .append("holidayTz3", getHolidayTz3())
            .append("holidayTz3Out", getHolidayTz3Out())
            .append("holidayTz4", getHolidayTz4())
            .append("holidayTz4Out", getHolidayTz4Out())
            .append("holidayTz5", getHolidayTz5())
            .append("holidayTz5Out", getHolidayTz5Out())
            .append("holidayTz6", getHolidayTz6())
            .append("holidayTz6Out", getHolidayTz6Out())
            .append("holidayTz7", getHolidayTz7())
            .append("holidayTz7Out", getHolidayTz7Out())
            .append("holidayTz8", getHolidayTz8())
            .append("holidayTz8Out", getHolidayTz8Out())
            .append("postHolidayTz1", getPostHolidayTz1())
            .append("postHolidayTz1Out", getPostHolidayTz1Out())
            .append("postHolidayTz2", getPostHolidayTz2())
            .append("postHolidayTz2Out", getPostHolidayTz2Out())
            .append("postHolidayTz3", getPostHolidayTz3())
            .append("postHolidayTz3Out", getPostHolidayTz3Out())
            .append("postHolidayTz4", getPostHolidayTz4())
            .append("postHolidayTz4Out", getPostHolidayTz4Out())
            .append("postHolidayTz5", getPostHolidayTz5())
            .append("postHolidayTz5Out", getPostHolidayTz5Out())
            .append("postHolidayTz6", getPostHolidayTz6())
            .append("postHolidayTz6Out", getPostHolidayTz6Out())
            .append("postHolidayTz7", getPostHolidayTz7())
            .append("postHolidayTz7Out", getPostHolidayTz7Out())
            .append("postHolidayTz8", getPostHolidayTz8())
            .append("postHolidayTz8Out", getPostHolidayTz8Out())
            .toString();
    }
}
