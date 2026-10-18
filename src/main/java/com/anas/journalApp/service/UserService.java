package com.anas.journalApp.service;

import com.anas.journalApp.entity.JournalEntry;
import com.anas.journalApp.entity.User;
import com.anas.journalApp.repository.JournalEntryRepository;
import com.anas.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean createNewUser(User user) {
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
        return true;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }


    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public boolean deleteById(ObjectId id) {
        User user = findById(id).orElse(null);
        if (user != null) {
            userRepository.deleteById(id);
//            log.info("Journal Got Deleted :"+id);
            return true;
        } else {
            return false;
        }
    }


    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName).orElse(null);
    }

    public User updateUserName(String currentUsername, String newUsername) {
        User user = userRepository.findByUsername(currentUsername).orElse(null);
        if (user != null) {
            user.setUsername(newUsername);
            userRepository.save(user);
            return user;
        }
        return null;
    }

    public Optional<List<JournalEntry>> getUserJournalEntry(String username) {
        return userRepository.findByUsername(username).map(User::getUserJournalsList);
    }

    public Optional<JournalEntry> addJournalEntry(String username, JournalEntry entry) {
        Optional<User> result = userRepository.findByUsername(username);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        User user = result.get();
        JournalEntry savedEntry = journalEntryRepository.save(entry);
        user.getUserJournalsList().add(savedEntry);
        userRepository.save(user);

        return Optional.of(savedEntry);
    }

    public void deleteUser(String username){
        try{
            userRepository.deleteByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
