package com.fixStay.backend.repository;

import com.fixStay.backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE (m.sender.emailAddress = :user1 AND m.receiver.emailAddress = :user2) OR (m.sender.emailAddress = :user2 AND m.receiver.emailAddress = :user1) ORDER BY m.timestamp ASC")
    List<Message> findConversation(@Param("user1") String user1, @Param("user2") String user2);
}