package com.phonebook.app.phonebookapp.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneBookDTO {
    private Long id;
    private String name;
    private String company;
    private String title;
    private List<GroupDTO> groups = new ArrayList<>();
}
