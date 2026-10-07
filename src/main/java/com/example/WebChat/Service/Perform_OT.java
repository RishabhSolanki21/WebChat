package com.example.WebChat.Service;

import com.example.WebChat.Dto.ChangedText;
import com.example.WebChat.Dto.DocsVersion;
import com.example.WebChat.Dto.RoomEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
@Slf4j
public class Perform_OT {


    private final ConcurrentHashMap<String,DocsVersion> version=new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;
    private final ConcurrentHashMap<String,List<ChangedText>> opHistory=new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String,List<Integer>> baseVersion=new ConcurrentHashMap<>();

    public void OT(RoomEvent roomEvent, String roomid) throws JsonProcessingException {
        ChangedText clinttext = objectMapper.treeToValue(roomEvent.getPayload(), ChangedText.class);
        int v = clinttext.getVersion();
        version.computeIfAbsent(roomid, room ->
                new DocsVersion(v, clinttext.getNewText())
        );
        DocsVersion currentVersion = version.get(roomid);
        if (clinttext.getVersion() ==currentVersion.getVersion()) {
            version.computeIfPresent(roomid, (room, docsVersion) -> {
                StringBuilder updatedText = new StringBuilder(docsVersion.getDocs());
                updatedText.delete(clinttext.getStart(),clinttext.getStart()+clinttext.getDelete_count());
                updatedText.insert(clinttext.getStart(), clinttext.getNewText());
                docsVersion.setDocs(String.valueOf(updatedText));
                docsVersion.setVersion(docsVersion.getVersion() + 1);
                clinttext.setVersion(clinttext.getVersion()+1);
                roomEvent.setPayload(objectMapper.valueToTree(clinttext));
                return docsVersion;
            });
            opHistory.computeIfAbsent(roomid, l->new ArrayList<>()).add(clinttext);
        }
        else{
            List<ChangedText>history=opHistory.get(roomid);
            int versionGap=currentVersion.getVersion()-clinttext.getVersion();
            int index= history.size()-versionGap;
            for (int i = index; i < history.size() ; i++) {
               ChangedText historytext= history.get(i);
               clinttext.setVersion(clinttext.getVersion()+1);
            }
        }
    }
}
