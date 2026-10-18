package com.anas.journalApp.service;


import com.anas.journalApp.entity.JournalEntry;
import com.anas.journalApp.entity.User;
import com.anas.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JournalEntryService {


    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    //dependency Injection

//
    @Transactional
    public JournalEntry  saveEntry(JournalEntry entry, String username){
        try{
            User user = userService.getUserByUsername(username);
            entry.setDateTime(LocalDateTime.now());
            entry.setId(entry.getId());

            //can be possible after this cannot be procced ?
            JournalEntry savedEntry =  journalEntryRepository.save(entry);
            user.getUserJournalsList().add(savedEntry);
            userService.saveUser(user);

            return savedEntry;

        } catch (Exception e) {
            throw new RuntimeException("An error occured: "+e);
        }
    }



    public Optional<JournalEntry> findById(ObjectId id ){
        return journalEntryRepository.findById(id);
    }


    @Transactional
    public boolean deleteById(ObjectId id, String username){
        try {
            User user = userService.getUserByUsername(username);
            List<JournalEntry> collect =  user.getUserJournalsList().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
            if(!collect.isEmpty()){
                user.getUserJournalsList().removeIf(x -> x.getId().equals(id));
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
                return true;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    //no need to make this Transaction as Users Hold Refrence , any
    //update in Journals ,will show up in Users automatically


    public boolean isEntryExists(ObjectId id , String username ){
        User user = userService.getUserByUsername(username);
        List<JournalEntry> collect = user.getUserJournalsList().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            return true;
        }
        return false;
    }



    public JournalEntry updateJournal(JournalEntry oldJournal , JournalEntry newJournal ){
        oldJournal.setTitle(newJournal.getTitle() != null && !newJournal.getTitle().equals("") ? newJournal.getTitle() : oldJournal.getTitle());
        oldJournal.setContent(newJournal.getContent() != null && !newJournal.getContent().equals("") ? newJournal.getContent() : oldJournal.getContent());
        return oldJournal;
    }


}
