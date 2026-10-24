package com.anas.journalApp.repository;


import com.anas.journalApp.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("dev")
public class UserRepositoryImpTests {

    @MockitoBean
    private WeatherService weatherService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    public void testSaveNewUser(){
        userRepositoryImpl.getUsersForSA();
    }
}
