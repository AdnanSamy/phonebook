package com.phonebook.app.phonebookapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "phonebook")
public class PhoneBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(name = "company")
    private String company;

    @Column(name = "title")
    private String title;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "phonebook_group", joinColumns = @JoinColumn(name = "phonebook_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups = new HashSet<>();

    public void removeGroup(Group group) {
        groups.remove(group);
        group.getPhoneBooks().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PhoneBook))
            return false;
        return id != null && id.equals(((PhoneBook) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
