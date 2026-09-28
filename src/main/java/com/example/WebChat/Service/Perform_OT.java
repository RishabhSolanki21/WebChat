package com.example.WebChat.Service;

import com.example.WebChat.Dto.ChangedText;
import com.example.WebChat.Dto.DocsVersion;
import com.example.WebChat.Dto.RoomEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
@Slf4j
public class Perform_OT {


    private final ConcurrentHashMap<String,DocsVersion> version=new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;
    private final ConcurrentHashMap<String,List<ChangedText>> history=new ConcurrentHashMap<>();

    @Synchronized
    public void OT(RoomEvent roomEvent, String roomid) throws JsonProcessingException {
        ChangedText text = objectMapper.treeToValue(roomEvent.getPayload(), ChangedText.class);
        int v = text.getVersion();
        DocsVersion version1 = version.get(roomid);
        if (text.getVersion() ==version1.getVersion()) {
            version.computeIfPresent(roomid, (room, docsVersion) -> {
                log.info("received text {}",text);
                StringBuilder updatedText = new StringBuilder(docsVersion.getDocs());
                log.info("updated text0 {}",updatedText);
                updatedText.delete(text.getStart(),text.getStart()+text.getDelete_count());
                log.info("updated text {}",updatedText);
                updatedText.insert(text.getStart(), text.getNewText());
                log.info("updated text2 {}",updatedText);
                docsVersion.setDocs(String.valueOf(updatedText));
                docsVersion.setVersion(docsVersion.getVersion() + 1);
                text.setVersion(text.getVersion()+1);
                roomEvent.setPayload(objectMapper.valueToTree(text));
                log.info("updated roomEvent {}",roomEvent);
                return docsVersion;
            });
            version.computeIfAbsent(roomid, room ->
                    new DocsVersion(v, text.getNewText())
            );
        }
        else{
            // perform ot
        }
    }
}
