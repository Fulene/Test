package com.synox.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

//@Service
@EnableScheduling
public class ScheduleService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(fixedDelay = 5000)
    public void delaySchedule() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        logger.info("===== {} =====", simpleDateFormat.format(new Date()));
    }

    @Scheduled(fixedRate = 5000)
    public void rateSchedule() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        logger.info("===== {} =====", simpleDateFormat.format(new Date()));
    }

}
