package com.thinhtran.chatapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thinhtran.chatapp.domain.MessageStatus;

@Repository
public interface MessageStatusRepository extends JpaRepository<MessageStatus, Long> {
    @Query("""
                SELECT s
                FROM MessageStatus s
                WHERE s.message.id IN :messageIds
                        AND s.user.id = :userId
            """)
    List<MessageStatus> findAllByMessageIds(List<Long> messageIds, Long userId);

    @Query("""
            SELECT COUNT(ms)
            FROM Message m
            JOIN MessageStatus ms ON m.id = ms.message.id
            WHERE m.conversation.id = :conversationId
                AND ms.user.id = :userId
                AND ms.seenAt IS NULL
                AND m.sender.id <> :userId
            """)
    Long findUnreadMessageCount(Long conversationId, Long userId);

    MessageStatus findByMessageIdAndUserId(Long messageId, Long userId);

    @Modifying
    @Query("""
            UPDATE MessageStatus ms
            SET ms.seenAt = CURRENT_TIMESTAMP
            WHERE ms.message.conversation.id = :conversationId
                AND ms.user.id = :userId
                AND ms.seenAt IS NULL
            """)
    void markAllMessagesAsSeenInConversation(Long conversationId, Long userId);
}
