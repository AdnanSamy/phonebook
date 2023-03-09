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
import org.springframework.web.bind.annotation.RestController;

import com.phonebook.app.phonebookapp.dto.GroupDTO;
import com.phonebook.app.phonebookapp.dto.MessageResponse;
import com.phonebook.app.phonebookapp.service.GroupService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class GroupController {

    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/group")
    public ResponseEntity<?> getAll() {
        List<GroupDTO> groupDTOs = groupService.getAll();

        return ResponseEntity.ok(groupDTOs);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        GroupDTO groupDTO = groupService.getOne(id);

        return ResponseEntity.ok(groupDTO);
    }

    @PutMapping("/group")
    public ResponseEntity<?> update(@RequestBody GroupDTO groupDTO) {
        groupService.update(groupDTO);

        return ResponseEntity.ok(new MessageResponse("success"));
    }

    @PostMapping("/group")
    public ResponseEntity<?> add(@RequestBody GroupDTO groupDTO) {
        groupService.add(groupDTO);

        return ResponseEntity.ok(new MessageResponse("success"));
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        groupService.delete(id);

        return ResponseEntity.ok(new MessageResponse("success"));
    }

}
