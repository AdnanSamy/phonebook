package com.phonebook.app.phonebookapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phonebook.app.phonebookapp.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String name);
}
