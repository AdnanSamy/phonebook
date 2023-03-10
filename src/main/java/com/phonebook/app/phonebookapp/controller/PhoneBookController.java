package com.phonebook.app.phonebookapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phonebook.app.phonebookapp.dto.MessageResponse;
import com.phonebook.app.phonebookapp.dto.PhoneBookDTO;
import com.phonebook.app.phonebookapp.service.GroupService;
import com.phonebook.app.phonebookapp.service.PhoneBookService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PhoneBookController {

    private PhoneBookService phoneBookService;
    private GroupService groupService;

    public PhoneBookController(PhoneBookService phoneBookService, GroupService groupService) {
        this.phoneBookService = phoneBookService;
        this.groupService = groupService;
    }

    @GetMapping("/phonebook")
    public ResponseEntity<?> getAll(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String company,
            @RequestParam(required = false, defaultValue = "") String title) {

        List<PhoneBookDTO> phoneBookDTOs = null;

        if (!name.equals("") || !company.equals("") || !title.equals("")) {

            PhoneBookDTO phoneBookDTO = new PhoneBookDTO();
            phoneBookDTO.setName(name);
            phoneBookDTO.setCompany(company);
            phoneBookDTO.setTitle(title);

            phoneBookDTOs = phoneBookService.getAllByCondition(phoneBookDTO);
        }else {
            phoneBookDTOs = phoneBookService.getAll();

        }

        return ResponseEntity.ok(phoneBookDTOs);
    }

    @GetMapping("/phonebook/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        PhoneBookDTO phoneBookDTO = phoneBookService.getOne(id);

        return ResponseEntity.ok(phoneBookDTO);
    }

    @PutMapping("/phonebook")
    public ResponseEntity<?> update(@RequestBody PhoneBookDTO phoneBookDTO) {
        phoneBookService.update(phoneBookDTO);

        return ResponseEntity.ok(new MessageResponse("success"));
    }

    @PostMapping("/phonebook")
    public ResponseEntity<?> add(@RequestBody PhoneBookDTO phoneBookDTO) {
        phoneBookService.add(phoneBookDTO);

        return ResponseEntity.ok(new MessageResponse("success"));
    }

    @DeleteMapping("/phonebook/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        phoneBookService.delete(id);

        return ResponseEntity.ok(new MessageResponse("success"));
    }

    @PutMapping("/phonebook/{id}/{groupId}")
    public ResponseEntity<?> removeGroup(@PathVariable Long id, @PathVariable Long groupId) {

        groupService.removeFromPhoneBook(id, groupId);

        return ResponseEntity.ok(new MessageResponse("success"));
    }

}
