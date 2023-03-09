package com.phonebook.app.phonebookapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phonebook.app.phonebookapp.model.PhoneBook;

@Repository
public interface PhoneBookRepository extends JpaRepository<PhoneBook, Long>{
    List<PhoneBook> findByNameOrCompanyOrTitle(String name, String company, String title);
}
