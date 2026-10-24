package com.anas.journalApp.controller;


import ch.qos.logback.core.joran.spi.HttpUtil;
import com.anas.journalApp.api.weather.WeatherResponse;
import com.anas.journalApp.entity.User;
import com.anas.journalApp.repository.JournalEntryRepository;
import com.anas.journalApp.service.CurrentUserService;
import com.anas.journalApp.service.UserService;
import com.anas.journalApp.service.WeatherService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user ){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        //authenticated SuccessFully

        User userInDB = userService.findByUserName(username);
        if(userInDB !=null){
            userInDB.setUsername(user.getUsername());
            userInDB.setPassword(user.getPassword());
            userService.createNewUser(userInDB);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }




    @GetMapping
    public ResponseEntity<?> getUserByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        if(user != null){
            return ResponseEntity.status(HttpStatus.OK )
                    .body(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
            String username= authentication.getName();
            userService.deleteUser(username);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/weather/{city}")
    public ResponseEntity<?> greeting(@PathVariable String city){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather(city);
        String greeting = "";
        if(weatherResponse !=null){
            greeting +=" ,Weather Feels Like : "+weatherResponse.getCurrent().getFeelslike();
        }

        return  ResponseEntity.status(HttpStatus.OK).body("HI "+authentication.getName()+greeting);
    }

    @PostMapping("/speech")
    public void voiceSpeech() {

    }
}
