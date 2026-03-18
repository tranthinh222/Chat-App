package com.thinhtran.chatapp.repository;

import com.thinhtran.chatapp.domain.Message;
import com.thinhtran.chatapp.domain.MessageReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {
}
