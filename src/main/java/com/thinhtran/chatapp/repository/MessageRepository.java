package com.thinhtran.chatapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thinhtran.chatapp.domain.Message;
import com.thinhtran.chatapp.domain.response.ResMessageDto;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {
    @Query("""
                SELECT new com.thinhtran.chatapp.domain.response.ResMessageDto(
                        m.id,
                        u.id,
                        u.username,
                        u.avatar,
                        m.messageType,
                        m.content,
                        m.createdAt
                    )
                FROM Message m
                JOIN m.sender u
                WHERE m.conversation.id = :conversationId
                ORDER BY m.createdAt ASC
            """)
    Page<ResMessageDto> findMesssages(Long conversationId, Pageable pageable);

    Message findTopByConversationIdOrderByCreatedAtDesc(Long conversationId);

    @Query("SELECT m.sender.id FROM Message m WHERE m.id = :messageId")
    Long findSenderIdById(Long messageId);
}
