package com.anas.journalApp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection ="users")
public class User {
    @Id
    private ObjectId user_id;

    @Indexed(unique = true) //unique for indexing
    @NonNull
    private String username;

    @NonNull
    private String password;

    private String email;
    
    private boolean sentimentAnalyis;
    @DBRef
    private List<JournalEntry> userJournalsList = new ArrayList<>();

    private List<String> roles;


}
