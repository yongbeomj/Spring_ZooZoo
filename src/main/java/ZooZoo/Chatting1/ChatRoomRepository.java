package ZooZoo.Chatting1;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {
    private Map<Integer, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init(){
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom(){
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public ChatRoom findRoomById(int id){
        return chatRoomMap.get(id);
    }

    public ChatRoom createChatRoom(int mno, String name){

//        ChatRoom chatRoom = ChatRoom.create(name);
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomId(mno);
        chatRoom.setName(name);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}
