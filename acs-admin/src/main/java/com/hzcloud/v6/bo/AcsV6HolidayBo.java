package com.hzcloud.v6.bo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AcsV6HolidayBo {
    /** �������� */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date holiday;
    /** ������ʽ */
    private String type;
}
