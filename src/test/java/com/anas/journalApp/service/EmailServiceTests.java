package com.anas.journalApp.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("dev")
public class EmailServiceTests {

    @MockitoBean
    private WeatherService weatherService;

    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendEmail("anasbtechjmi@gmail.com",
                "this is SMTP",
                "We are Learning SMTP in Spring BOOT");
    }
}