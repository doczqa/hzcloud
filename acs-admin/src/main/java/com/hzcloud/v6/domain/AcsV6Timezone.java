package com.hzcloud.v6.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 时区设置对象 acs_v6_timezone
 * 
 * @author zhangfan
 * @date 2021-07-14
 */
public class AcsV6Timezone extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 时区 */
    private String id;

    /** 时段一 */
    @Excel(name = "时段一")
    private String interval1;

    /** 时段二 */
    @Excel(name = "时段二")
    private String interval2;

    /** 时段三 */
    @Excel(name = "时段三")
    private String interval3;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setInterval1(String interval1) 
    {
        this.interval1 = interval1;
    }

    public String getInterval1() 
    {
        return interval1;
    }
    public void setInterval2(String interval2) 
    {
        this.interval2 = interval2;
    }

    public String getInterval2() 
    {
        return interval2;
    }
    public void setInterval3(String interval3) 
    {
        this.interval3 = interval3;
    }

    public String getInterval3() 
    {
        return interval3;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("interval1", getInterval1())
            .append("interval2", getInterval2())
            .append("interval3", getInterval3())
            .toString();
    }
}
