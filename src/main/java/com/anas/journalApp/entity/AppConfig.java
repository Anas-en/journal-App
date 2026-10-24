package com.anas.journalApp.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "app_config")
@Getter
@Setter
public class AppConfig {

    private String key;
    private String value;

}
