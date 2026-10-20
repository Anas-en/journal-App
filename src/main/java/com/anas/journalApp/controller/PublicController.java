package com.anas.journalApp.controller;


import com.anas.journalApp.entity.User;
import com.anas.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired UserService userService;

    @GetMapping("/health")
    public String healthCheck(){
        return "Ok";
    }


    @PostMapping("register")
    public ResponseEntity<?> createUser(@RequestBody User user){

        boolean isCreated = userService.createNewUser(user);
        if(isCreated){
            return ResponseEntity.status(HttpStatus.CREATED).build();

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("UserName already exists");

    }




}
