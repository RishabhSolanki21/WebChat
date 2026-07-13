package com.example.WebChat.Repository;

import com.example.WebChat.Entity.Project_data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Project_data, Long> {
}
