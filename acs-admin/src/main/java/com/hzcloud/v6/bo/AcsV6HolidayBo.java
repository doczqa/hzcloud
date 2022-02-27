package com.hzcloud.v6.bo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AcsV6HolidayBo {
    /** 假日日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date holiday;
    /** 假日型式 */
    private String type;
}
