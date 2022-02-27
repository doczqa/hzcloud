package com.hzcloud.device.bo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;

@Data
public class DeleteUserData {
    private String cardNumber;
    private String controllerIndex;
    private String cardIndex;

    @Override
    public String toString(){
        return new ToStringBuilder(this,ToStringStyle.JSON_STYLE)
               .append("cardNumber", getCardNumber())
               .append("controllerIndex", getControllerIndex())
               .append("cardIndex", getCardIndex())
               .toString();
    }
}
