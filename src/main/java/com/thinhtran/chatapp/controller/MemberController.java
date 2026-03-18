package com.thinhtran.chatapp.controller;

import com.thinhtran.chatapp.domain.response.ResConversationMemberDto;
import com.thinhtran.chatapp.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class MemberController {
    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<List<ResConversationMemberDto>> fetchMemberById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.memberService.getMemberByConversationId(id));
    }

}
