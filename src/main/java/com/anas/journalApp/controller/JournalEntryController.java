package com.anas.journalApp.controller;
import com.anas.journalApp.entity.JournalEntry;
import com.anas.journalApp.entity.User;
import com.anas.journalApp.service.JournalEntryService;
import com.anas.journalApp.service.UserService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journals")
public class JournalEntryController {

    @Autowired
    private JournalEntryService entryService;

    @Autowired
    private UserService userService;

    @Autowired
    private org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;


    @GetMapping("{username}") // GET localhost:8000/journals
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username)
    {
        User user = userService.findByUserName(username);
        if(user != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(user.getUserJournalsList());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PostMapping("{username}") // POST localhost:8000/journals
    public ResponseEntity<?> createEntry(@Valid @RequestBody JournalEntry myEntry , @PathVariable String username){
        try{
            JournalEntry entry = entryService.saveEntry(myEntry,username);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(entry);

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    //updating Entries

    @PutMapping("{username}/{id}") // GET localhost:8000/id/{id}
    public ResponseEntity<JournalEntry> updateJournalById(@RequestBody JournalEntry newJournal ,
                                                          @PathVariable ObjectId id,
                                                          @PathVariable String username){

        JournalEntry oldJournal  = entryService.findById(id).orElse(null);

        if(oldJournal != null) {
            oldJournal = entryService.updateJournal(oldJournal, newJournal );
            entryService.saveEntry(oldJournal);
            return ResponseEntity.status(HttpStatus.CREATED).body(oldJournal);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    }


    @DeleteMapping("{username}/{id}")
    public ResponseEntity<String> deleteJournalById(@PathVariable ObjectId id , @PathVariable String username ){
            boolean isDeleted = entryService.deleteById(id, username);
            if(isDeleted){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Delete Sucess");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Entry Found by Id: "+ id);

    }


}
