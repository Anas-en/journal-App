package com.anas.journalApp.controller;


import com.anas.journalApp.entity.User;
import com.anas.journalApp.repository.JournalEntryRepository;
import com.anas.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryRepository journalEntryRepository;




    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getAllUser());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error");
        }
    }

    @GetMapping("/id/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        User user = userService.getUserByUsername(username);
        if(user != null){
            return ResponseEntity.status(HttpStatus.OK )
                    .body(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        boolean isCreated = userService.saveEntry(user);
        if(isCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("UserName already exists");
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user ,@PathVariable String username){
        User userInDB = userService.findByUserName(username);
        if(userInDB !=null){
            userInDB.setUsername(user.getUsername());
            userInDB.setPassword(user.getPassword());
            userService.saveEntry(userInDB);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



}
