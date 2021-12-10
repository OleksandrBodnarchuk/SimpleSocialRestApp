package pl.alex.app.controller;

import org.springframework.web.bind.annotation.*;
import pl.alex.app.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {
    private int counter= 5;
    private List<Map<String,String>> messages = new ArrayList<>() {{
        add(new HashMap<>() {{
            put("id", "1");
            put("text", "First message");
        }});
        add(new HashMap<>() {{
            put("id", "2");
            put("text", "Second message");
        }});
        add(new HashMap<>() {{
            put("id", "3");
            put("text", "Third message");
        }});
        add(new HashMap<>() {{
            put("id", "4");
            put("text", "Fourth message");
        }});
    }};

    @GetMapping
    public List<Map<String,String>> listMessages(){
        return messages;
    }

    @GetMapping(path = "/{messageId}")
    public Map<String,String> getOneMessage(@PathVariable String id){
        return getMessageById(id);
    }



    @PostMapping
    public Map<String,String> createMessage(@RequestBody Map<String,String> message){
        message.put("id",String.valueOf(counter++));
        messages.add(message);
        return message;
    }

    @PutMapping(value = "/{messageId}")
    public Map<String,String> updateMessage(@PathVariable String messageId, @RequestBody Map<String,String> message){
       Map<String,String> messageFromDB = getMessageById(messageId);
       messageFromDB.putAll(message);
       messageFromDB.put("id",messageId);
        return messageFromDB;
    }

    @DeleteMapping(path = "/{messageId}")
    public void deleteMessage(@PathVariable String messageId){
        Map<String,String> messageToDelete = getMessageById(messageId);
        messages.remove(messageToDelete);

    }


    private Map<String, String> getMessageById(String id) {
        return messages.stream().filter(messages -> messages.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

}
