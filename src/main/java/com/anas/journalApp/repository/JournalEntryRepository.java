package com.anas.journalApp.repository;

import com.anas.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


//handles the JournalEntry Document     Journal , id

public interface JournalEntryRepository extends MongoRepository<JournalEntry , ObjectId> {

}
