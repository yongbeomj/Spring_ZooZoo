package ZooZoo.Chatting1;


import ZooZoo.Domain.DTO.Member.MemberDTO;
import ZooZoo.Domain.Entity.Member.MemberEntity;
import ZooZoo.Domain.Entity.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    HttpServletRequest request;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/rooms")
    public String rooms(Model model){
        HttpSession session = request.getSession();
        MemberDTO memberDto = (MemberDTO) session.getAttribute("loginDTO");
        MemberEntity memberEntity = memberRepository.findById(memberDto.getMno()).get();


        if (memberDto != null){
            chatRoomRepository.createChatRoom(memberDto.getMno(),memberEntity.getMname());
        }
        model.addAttribute("loginDTO",memberDto);
        model.addAttribute("rooms",chatRoomRepository.findAllRoom());
        return "Chatting/rooms";
    }

    @GetMapping("/rooms/{id}")
    public String room(@PathVariable int id, Model model){
        ChatRoom room = chatRoomRepository.findRoomById(id);
        HttpSession session = request.getSession();
        MemberDTO memberDto = (MemberDTO) session.getAttribute("loginDTO");

        model.addAttribute("loginDTO",memberDto);
        model.addAttribute("room",room);
        return "Chatting/room";
    }


}
