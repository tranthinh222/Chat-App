package com.thinhtran.chatapp.service;

import com.thinhtran.chatapp.domain.response.ResConversationMemberDto;
import com.thinhtran.chatapp.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public List<ResConversationMemberDto> getMemberByConversationId (Long id){
        return this.memberRepository.findByConversationId(id);
    }
}
