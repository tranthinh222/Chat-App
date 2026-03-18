package com.thinhtran.chatapp.service;

import com.thinhtran.chatapp.domain.Conversation;
import com.thinhtran.chatapp.domain.Member;
import com.thinhtran.chatapp.domain.User;
import com.thinhtran.chatapp.domain.request.ReqCreateConversationDto;
import com.thinhtran.chatapp.domain.response.ResConversationDto;
import com.thinhtran.chatapp.domain.response.ResConversationMemberDto;
import com.thinhtran.chatapp.repository.ConversationRepository;
import com.thinhtran.chatapp.repository.MemberRepository;
import com.thinhtran.chatapp.repository.UserRepository;
import com.thinhtran.chatapp.util.SecurityUtil;
import com.thinhtran.chatapp.util.constant.RoleMemberEnum;
import com.thinhtran.chatapp.util.constant.RoomTypeEnum;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        // create conversation
        Conversation conversation = new Conversation();
        conversation.setRoomType(reqCreateConversationDto.getRoomType());
        conversation.setName(
                RoomTypeEnum.DIRECT.equals(reqCreateConversationDto.getRoomType())
                        ? null :  reqCreateConversationDto.getRoomName()
        );

        Optional<Long> creatorId = SecurityUtil.getCurrentUserId();
        if (creatorId.isEmpty()) {
            throw new IllegalArgumentException("Creator id cannot be null");
        }

        Optional<User> creator = this.userRepository.findById(creatorId.get());
        if (!creator.isPresent()) {
            throw new RuntimeException("Creator not found");
        }
        conversation.setCreatedBy(creator.get());
        this.conversationRepository.save(conversation);


        // create member
        Set<Long> memIds = new HashSet<>(reqCreateConversationDto.getMemIds());
        memIds.add(creatorId.get());
        for (Long userId : memIds) {
            Optional<User> user = this.userRepository.findById(userId);
            if (!creator.isPresent()) {
                throw new RuntimeException("User not found");
            }

            Member member = new Member();
            member.setUser(user.get());
            member.setConversation(conversation);
            if (userId.equals(creatorId.get())) {
                member.setRole(
                        reqCreateConversationDto.getRoomType() == RoomTypeEnum.DIRECT ? RoleMemberEnum.MEMBER : RoleMemberEnum.ADMIN
                );
            }
            else
                member.setRole(RoleMemberEnum.MEMBER);
            this.memberRepository.save(member);
        }



        // return the response of conversation
        ResConversationDto res = new ResConversationDto();
        res.setId(conversation.getId());
        res.setRoomType(reqCreateConversationDto.getRoomType());
        res.setName(reqCreateConversationDto.getRoomName());

        return res;
    }



}
