package com.example.WebChat.Repository;

import com.example.WebChat.Entity.GroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMessageRepo extends JpaRepository<GroupMessage, Long> {
}
