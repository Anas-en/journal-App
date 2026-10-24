package com.anas.journalApp.cache;


import com.anas.journalApp.entity.AppConfig;
import com.anas.journalApp.repository.AppConfigRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    //Constructor Injection
    private final AppConfigRepository appConfigRepository;

    public AppCache(AppConfigRepository appConfigRepository){
        this.appConfigRepository = appConfigRepository;
    }

    public Map<String , String > APP_CACHE;

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<AppConfig> all = appConfigRepository.findAll();
        for(AppConfig config : all ){
            APP_CACHE.put(config.getKey() , config.getValue());
        }
    }
}
