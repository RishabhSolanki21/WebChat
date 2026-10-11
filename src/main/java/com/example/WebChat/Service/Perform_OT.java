package com.example.WebChat.Service;

import com.example.WebChat.Dto.ChangedText;
import com.example.WebChat.Dto.DocsVersion;
import com.example.WebChat.Dto.RoomEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.MathContext;
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

    public void OT(RoomEvent roomEvent, String roomid) throws JsonProcessingException {
        ChangedText clinttext = objectMapper.treeToValue(roomEvent.getPayload(), ChangedText.class);
        int v = clinttext.getVersion();
        DocsVersion currentVersion=createdocs(roomid);
        if (clinttext.getVersion() ==currentVersion.getVersion()) {
            transform(roomid, clinttext, roomEvent);
            opHistory.computeIfAbsent(roomid, l->new ArrayList<>()).add(clinttext);
            log.info("list created {} with updated docs {}",opHistory.get(roomid),version.get(roomid));
        } else if (clinttext.getVersion() < currentVersion.getVersion()){
            List<ChangedText>history=opHistory.get(roomid);
            int versionGap=currentVersion.getVersion()-clinttext.getVersion();
            int index= history.size()-versionGap;
            for (int i = index; i < history.size() ; i++) {
               adjustPosition(history.get(i),clinttext);
            }
            transform(roomid, clinttext, roomEvent);
            opHistory.get(roomid).add(clinttext);
            log.info("list updated {} with updated docs {}",opHistory.get(roomid),version.get(roomid));
        }
    }
    public void adjustPosition(ChangedText historyOperation, ChangedText incomingOperation) {
        // normal delete and write operatiions
        int hstart=historyOperation.getStart();
        int hdeletecount=historyOperation.getDelete_count();
        int htext=historyOperation.getNewText().length();
        int vstart=incomingOperation.getStart();
        int vdeletecount=incomingOperation.getDelete_count();
        if (vstart+vdeletecount<hstart) {
            log.info("calling 1 ==>");
            return;
        }else if (vstart>=hstart&&vstart<hstart+hdeletecount) {
            log.info("before calling 2 with data start and deleteCount {} {}",incomingOperation,incomingOperation);
            int offsetI=vstart+vdeletecount;
            int offsetH=hstart+hdeletecount;
            incomingOperation.setDelete_count(Math.max(0,offsetI-offsetH));
            incomingOperation.setStart(hstart+htext);
            log.info("after calling 2 with data start and deleteCount {} {}",incomingOperation,incomingOperation);
        } else if (vstart<hstart&&vstart+vdeletecount>=hstart) {
            if(vstart+vdeletecount>=hstart+hdeletecount){
                log.info("before calling 3.1 with data start and deleteCount {} {}",incomingOperation,incomingOperation);
                incomingOperation.setDelete_count(vdeletecount-hdeletecount+htext);
                log.info("after calling 3.1 with data start and deleteCount {} {}",incomingOperation,incomingOperation);
            }else {
                log.info("before calling 3.2 with data start and deleteCount {} {}",incomingOperation,incomingOperation);
                incomingOperation.setDelete_count(hstart - vstart);
                log.info("after calling 3.2 with data start and deleteCount {} {}",incomingOperation,incomingOperation);
            }
        } else if (vstart>=hstart+hdeletecount){// this will work for if two users type at same index or above
            log.info("before calling 4 with data start and deleteCount {} {}",incomingOperation,incomingOperation);
            incomingOperation.setStart(vstart+historyOperation.getNewText().length()-hdeletecount);
            log.info("after calling 4 with data start and deleteCount {} {}",incomingOperation,incomingOperation);
        }
    }
    public DocsVersion createdocs(String roomid){
        log.info("new document is created {}",roomid);
        return version.computeIfAbsent(roomid, room ->
                new DocsVersion(0, "")
        );
    }

    public void transform(String roomid, ChangedText clinttext, RoomEvent roomEvent) throws JsonProcessingException {
        version.computeIfPresent(roomid, (room, docsVersion) -> {
            StringBuilder updatedText = new StringBuilder(docsVersion.getDocs());
            updatedText.delete(clinttext.getStart(),clinttext.getStart()+clinttext.getDelete_count());
            updatedText.insert(clinttext.getStart(), clinttext.getNewText());
            docsVersion.setDocs(String.valueOf(updatedText));
            docsVersion.setVersion(docsVersion.getVersion() + 1);
            clinttext.setVersion(docsVersion.getVersion());
            roomEvent.setPayload(objectMapper.valueToTree(clinttext));
            return docsVersion;
        });
    }
}
