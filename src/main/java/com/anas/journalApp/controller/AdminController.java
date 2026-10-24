package com.anas.journalApp.controller;


import com.anas.journalApp.cache.AppCache;
import com.anas.journalApp.dto.UserDTO;
import com.anas.journalApp.entity.User;
import com.anas.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AppCache appCache;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
          List<User> allUser = userService.getAllUser();
          if(allUser !=null && !allUser.isEmpty()){
                List<UserDTO> userDTOs = allUser.stream().map(user-> new UserDTO(user.getUsername(),user.getEmail(),user.getRoles() , user.isSentimentAnalyis()))
                        .collect(Collectors.toList());

                return ResponseEntity.status(HttpStatus.OK).body(userDTOs);
          }

          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user is there");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-new-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user){
         boolean isCreated = userService.createNewAdmin(user);
         if(isCreated){
             return ResponseEntity.status(HttpStatus.CREATED).build();
         }
         return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }


    @PostMapping("/clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }
}
