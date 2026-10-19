package com.anas.journalApp.service;

import com.anas.journalApp.entity.User;
import com.anas.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceTests {


    @Autowired
    private UserRepository userRepository;

    @Disabled
    @Test
    public void testFindByUserName(){
           // assertEquals(4,1+2);
            User user = userRepository.findByUsername("atif").orElse(null);
            assertTrue(!user.getUserJournalsList().isEmpty());

    }


    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "2,3,4",
            "1,3,5"
    })
    public void test(int a , int b , int expected){
        assertEquals(expected , a +b);
    }
}
