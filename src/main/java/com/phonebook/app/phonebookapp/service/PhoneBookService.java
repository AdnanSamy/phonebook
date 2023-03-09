package com.phonebook.app.phonebookapp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phonebook.app.phonebookapp.dto.GroupDTO;
import com.phonebook.app.phonebookapp.dto.PhoneBookDTO;
import com.phonebook.app.phonebookapp.exception.ResourceNotFoundException;
import com.phonebook.app.phonebookapp.model.Group;
import com.phonebook.app.phonebookapp.model.PhoneBook;
import com.phonebook.app.phonebookapp.repository.GroupRepository;
import com.phonebook.app.phonebookapp.repository.PhoneBookRepository;

@Service
public class PhoneBookService {

    private PhoneBookRepository phoneBookRepository;
    private GroupRepository groupRepository;
    private static final Logger logger = LoggerFactory.getLogger(PhoneBookService.class);

    public PhoneBookService(PhoneBookRepository phoneBookRepository, GroupRepository groupRepository) {

        this.phoneBookRepository = phoneBookRepository;
        this.groupRepository = groupRepository;
    }

    @Transactional
    public void add(PhoneBookDTO phoneBookDTO) {

        Set<Group> groups = new HashSet<>();

        PhoneBook phoneBook = new PhoneBook();
        phoneBook.setName(phoneBookDTO.getName());
        phoneBook.setCompany(phoneBookDTO.getCompany());
        phoneBook.setTitle(phoneBookDTO.getTitle());
        phoneBookDTO.getGroups().forEach(groupDTO -> {
            Group group = groupRepository.findByName(groupDTO.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Error: Group is Not Found."));
            
            logger.info("GROUPDTO -> {}", groupDTO.getName());

            group.getPhoneBooks().add(phoneBook);

            groups.add(group);
        });
        phoneBook.setGroups(groups);

        phoneBookRepository.save(phoneBook);
    }

    public List<PhoneBookDTO> getAll(PhoneBookDTO phoneBookDTO) {
        List<PhoneBook> phoneBooks = phoneBookRepository.findByNameOrCompanyOrTitle(
                phoneBookDTO.getName(), phoneBookDTO.getCompany(), phoneBookDTO.getTitle());

        List<PhoneBookDTO> phoneBookDTOs = phoneBooks.stream()
                .map(phoneBook -> {

                    List<GroupDTO> groupDTOs = phoneBook.getGroups().stream()
                            .map(group -> new GroupDTO(group.getId(), group.getName()))
                            .collect(Collectors.toList());

                    return new PhoneBookDTO(phoneBook.getId(), phoneBook.getName(), phoneBook.getCompany(),
                            phoneBook.getTitle(), groupDTOs);
                })
                .collect(Collectors.toList());

        return phoneBookDTOs;
    }

    public PhoneBookDTO getOne(Long id) {
        PhoneBook phoneBook = phoneBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact is Not Found"));

        List<GroupDTO> groupDTOs = phoneBook.getGroups().stream()
                .map(group -> new GroupDTO(group.getId(), group.getName()))
                .collect(Collectors.toList());

        PhoneBookDTO phoneBookDTO = new PhoneBookDTO(phoneBook.getId(), phoneBook.getName(), phoneBook.getCompany(),
                phoneBook.getTitle(), groupDTOs);

        return phoneBookDTO;
    }

    @Transactional
    public void update(PhoneBookDTO phoneBookDTO) {
        PhoneBook phoneBook = phoneBookRepository.findById(phoneBookDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Contact is Not Found"));

        phoneBook.setName(phoneBookDTO.getName());
        phoneBook.setCompany(phoneBookDTO.getCompany());
        phoneBook.setTitle(phoneBookDTO.getTitle());

        phoneBookRepository.save(phoneBook);
    }

    public void delete(Long id) {
        phoneBookRepository.deleteById(id);
    }

}
