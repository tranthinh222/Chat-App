package com.thinhtran.chatapp.controller;

import com.thinhtran.chatapp.domain.Conversation;
import com.thinhtran.chatapp.domain.request.ReqCreateConversationDto;
import com.thinhtran.chatapp.domain.response.ResConversationDto;
import com.thinhtran.chatapp.domain.response.ResConversationMemberDto;
import com.thinhtran.chatapp.service.ConversationService;
import com.thinhtran.chatapp.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ConversationController {
    private final ConversationService conversationService;
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }
    @GetMapping("/conversation/{id}")
    public ResponseEntity<Conversation> fetchConversationById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.conversationService.getUserById(id));
    }



    @PostMapping("/conversation")
    public ResponseEntity<ResConversationDto> createConversation(@Valid @RequestBody ReqCreateConversationDto reqCreateConversationDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.conversationService.createConversation(reqCreateConversationDto));
    }
}
