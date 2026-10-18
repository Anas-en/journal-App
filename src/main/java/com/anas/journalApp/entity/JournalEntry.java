package com.anas.journalApp.entity;

//model or Entity

//POJO - >  Play in Old Java Object

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@Document(collection = "journal_entries")
public class JournalEntry {

    @Id
    private ObjectId id;

    @NotBlank(message = "Title is Required")
    private String  title;

    @NotBlank(message = "Content is Required")
    private String content;

    private LocalDateTime dateTime;

}
