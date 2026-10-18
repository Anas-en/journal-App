package com.anas.journalApp.service;


//implements , all the Methods , which requires the authentication
//Authentication authentication  = SecurityContextHolder.getContext().getAuthenticated( );
//authentication.getName();

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public String getCurrentUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()|| authentication instanceof AnonymousAuthenticationToken){
            throw   new IllegalStateException(
                    "No authenticated user Found"
            );
        }

        return authentication.getName();
    }

}
