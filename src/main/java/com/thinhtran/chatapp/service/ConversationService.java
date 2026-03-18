package com.thinhtran.chatapp.service;

import com.thinhtran.chatapp.domain.Conversation;
import com.thinhtran.chatapp.domain.Member;
import com.thinhtran.chatapp.domain.User;
import com.thinhtran.chatapp.domain.request.ReqCreateConversationDto;
import com.thinhtran.chatapp.domain.response.ResConversationDto;
import com.thinhtran.chatapp.repository.ConversationRepository;
import com.thinhtran.chatapp.repository.MemberRepository;
import com.thinhtran.chatapp.repository.UserRepository;
import com.thinhtran.chatapp.util.SecurityUtil;
import com.thinhtran.chatapp.util.constant.RoleMemberEnum;
import com.thinhtran.chatapp.util.constant.RoomTypeEnum;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    public ConversationService(ConversationRepository conversationRepository, UserRepository  userRepository,  MemberRepository memberRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
    }

    public Conversation getUserById(Long id){
        return this.conversationRepository.findById(id).orElse(null);
    }

    @Transactional
    public ResConversationDto createConversation (ReqCreateConversationDto reqCreateConversationDto){
        Conversation conversation = new Conversation();
        conversation.setRoomType(reqCreateConversationDto.getRoomType());
        if (RoomTypeEnum.DIRECT.equals(reqCreateConversationDto.getRoomType()))
            conversation.setName(null);
        else
            conversation.setName(reqCreateConversationDto.getRoomName());

        Optional<Long> id = SecurityUtil.getCurrentUserId();
        if (id.isEmpty()) {
            throw new IllegalArgumentException("Creator id cannot be null");
        }

        User creator = this.userRepository.findById(id.get()).get();
        conversation.setCreatedBy(creator);
        this.conversationRepository.save(conversation);

        for (int i = 0; i < reqCreateConversationDto.getMemIds().size(); i++) {
            User user = this.userRepository.findById(reqCreateConversationDto.getMemIds().get(i)).get();

            Member member = new Member();
            member.setUser(user);
            member.setConversation(conversation);
            member.setRole(RoleMemberEnum.MEMBER);
            this.memberRepository.save(member);
        }

        Member member = new Member();
        member.setUser(creator);
        member.setConversation(conversation);
        member.setRole(RoleMemberEnum.MEMBER);
        this.memberRepository.save(member);

        ResConversationDto res = new ResConversationDto();
        res.setId(conversation.getId());
        res.setRoomType(reqCreateConversationDto.getRoomType());
        res.setName(reqCreateConversationDto.getRoomName());


        return res;
    }
}
