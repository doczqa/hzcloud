package com.hzcloud.card.task;

import com.hzcloud.card.service.IAcsCardService;
import com.hzcloud.common.utils.spring.SpringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("cardTask")
public class AcsCardTask {
    @Autowired
    private IAcsCardService cardService;

    public AcsCardTask(){
        this.cardService =  SpringUtils.getBean(IAcsCardService.class);
    }

    public void cardCheck(){
        cardService.CheckAcsCard();
    }
}
