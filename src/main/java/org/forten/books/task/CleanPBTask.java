package org.forten.books.task;

import org.forten.books.bo.BookBo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("cleanPBTask")
public class CleanPBTask {
    @Resource
    private BookBo bo;

    @Scheduled(cron = "0 30 14 * * ?")
    public void execute(){
        bo.doCleanPB();
    }
}
