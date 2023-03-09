package com.phonebook.app.phonebookapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phonebook.app.phonebookapp.dto.GroupDTO;
import com.phonebook.app.phonebookapp.exception.ResourceNotFoundException;
import com.phonebook.app.phonebookapp.model.Group;
import com.phonebook.app.phonebookapp.model.PhoneBook;
import com.phonebook.app.phonebookapp.repository.GroupRepository;
import com.phonebook.app.phonebookapp.repository.PhoneBookRepository;

@Service
public class GroupService {
    private GroupRepository groupRepository;
    private PhoneBookRepository phoneBookRepository;

    public GroupService(GroupRepository groupRepository, PhoneBookRepository phoneBookRepository) {
        this.groupRepository = groupRepository;
        this.phoneBookRepository = phoneBookRepository;
    }

    @Transactional
    public void add(GroupDTO groupDTO) {
        Group group = new Group(groupDTO.getName());

        groupRepository.save(group);
    }

    public List<GroupDTO> getAll() {
        List<Group> groups = groupRepository.findAll();
        List<GroupDTO> groupDTOs = groups.stream()
                .map(group -> new GroupDTO(group.getId(), group.getName()))
                .collect(Collectors.toList());

        return groupDTOs;
    }

    public GroupDTO getOne(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group is Not Found."));

        GroupDTO groupDTO = new GroupDTO(group.getId(), group.getName());

        return groupDTO;
    }

    @Transactional
    public void update(GroupDTO groupDTO) {
        Group group = groupRepository.findById(groupDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Group is Not Found."));

        group.setName(groupDTO.getName());

        groupRepository.save(group);
    }

    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

    @Transactional
    public void removeFromPhoneBook(Long phoneBookId, Long groupId) {
        PhoneBook phoneBook = phoneBookRepository.findById(phoneBookId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact is Not Found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group is Not Found."));

        phoneBook.removeGroup(group);
    }
}
