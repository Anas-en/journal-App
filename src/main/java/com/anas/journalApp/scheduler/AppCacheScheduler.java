package com.anas.journalApp.scheduler;

import com.anas.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class AppCacheScheduler {

    @Autowired
    private  AppCache appCache;

    @Scheduled(cron = "0 */10 * * * *")  //* * * * * - > every minute */10 * * * * every -> 10 minutes
    public void clearAppCache(){
        appCache.init();
    }

}
