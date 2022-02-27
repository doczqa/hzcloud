package com.hzcloud.v6.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 卡片索引对象 acs_v6_card_index
 * 
 * @author zhangfan
 * @date 2021-07-20
 */
public class AcsV6CardIndex extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 控制器id */
    private Long controllerId;

    /** 卡id */
    private Long cardId;

    /** 卡片索引 */
    private String cardIndex;

    public void setControllerId(Long controllerId) 
    {
        this.controllerId = controllerId;
    }

    public Long getControllerId() 
    {
        return controllerId;
    }
    public void setCardId(Long cardId) 
    {
        this.cardId = cardId;
    }

    public Long getCardId() 
    {
        return cardId;
    }
    public void setCardIndex(String cardIndex) 
    {
        this.cardIndex = cardIndex;
    }

    public String getCardIndex() 
    {
        return cardIndex;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("controllerId", getControllerId())
            .append("cardId", getCardId())
            .append("cardIndex", getCardIndex())
            .toString();
    }
}
