package com.anas.journalApp.dto;

import com.anas.journalApp.enums.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    private String username;
    private String email;
    private List<String> roles;
    private  boolean sentimentAnalysis;

}
