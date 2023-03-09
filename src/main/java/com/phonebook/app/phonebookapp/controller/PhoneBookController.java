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
import com.phonebook.app.phonebookapp.service.PhoneBookService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PhoneBookController {

    private PhoneBookService phoneBookService;

    public PhoneBookController(PhoneBookService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    @GetMapping("/phonebook")
    public ResponseEntity<?> getAll(@RequestParam(required = false) String name,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String title) {

        PhoneBookDTO phoneBookDTO = new PhoneBookDTO();
        phoneBookDTO.setName(name);
        phoneBookDTO.setCompany(company);
        phoneBookDTO.setTitle(title);

        List<PhoneBookDTO> phoneBookDTOs = phoneBookService.getAll(phoneBookDTO);

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

}
