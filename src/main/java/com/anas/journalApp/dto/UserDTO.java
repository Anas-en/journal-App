package com.anas.journalApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    private String username;
    private List<String> roles;

}
