package com.anas.journalApp.controller;
import com.anas.journalApp.entity.JournalEntry;
import com.anas.journalApp.entity.User;
import com.anas.journalApp.service.CurrentUserService;
import com.anas.journalApp.service.JournalEntryService;
import com.anas.journalApp.service.UserService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journals")
public class JournalEntryController {

    @Autowired
    private JournalEntryService entryService;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;


    @GetMapping// GET localhost:8000/journals
    public ResponseEntity<?> getAllJournalEntriesOfUser()
    {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
          //  String username = currentUserService.getCurrentUsername();
            User user = userService.findByUserName(username);
            if(user != null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(user.getUserJournalsList());
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId id){

            String username = currentUserService.getCurrentUsername();
            User user = userService.findByUserName(username);
            List<JournalEntry> collect=  user.getUserJournalsList()
                    .stream()
                    .filter(x->x.getId().equals(id))
                    .collect(Collectors.toList());

            if(!collect.isEmpty()){
                Optional<JournalEntry> journalEntry = entryService.findById(id);
                if(journalEntry.isPresent()){
                    return ResponseEntity.status(HttpStatus.OK).body(journalEntry);
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping // POST localhost:8000/journals
    public ResponseEntity<?> createEntry(@Valid @RequestBody JournalEntry myEntry){
        try{
           // have to implement goes to current User only
            String username = currentUserService.getCurrentUsername();
            JournalEntry entry = entryService.saveEntry(myEntry,username);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(entry);

        } catch (Exception e){
            throw new RuntimeException(e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    //updating Entries
    @PutMapping("/id/{id}") // GET localhost:8000/id/{id}
    public ResponseEntity<JournalEntry> updateJournalById(@RequestBody JournalEntry newJournal ,
                                                          @PathVariable ObjectId id){

        String username = currentUserService.getCurrentUsername();
        boolean isOwner = entryService.isEntryExists(id , username);

        if(isOwner){
            JournalEntry oldJournal  = entryService.findById(id).orElse(null);
            if(oldJournal != null) {
                    oldJournal = entryService.updateJournal(oldJournal, newJournal );
                    entryService.saveEntry(oldJournal,username);
                    return ResponseEntity.status(HttpStatus.CREATED).body(oldJournal);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    }


    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteJournalById(@PathVariable ObjectId id ){
            String username = currentUserService.getCurrentUsername();

            boolean isDeleted = entryService.deleteById(id, username);
            if(isDeleted){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Delete Sucess");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Entry Found by Id: "+ id);
    }


}
