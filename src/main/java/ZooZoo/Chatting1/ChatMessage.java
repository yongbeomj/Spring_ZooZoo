package ZooZoo.Chatting1;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ChatMessage {
    private int chatRoomId;
    private String writer;
    private String message;
    private MessageType type;
}
