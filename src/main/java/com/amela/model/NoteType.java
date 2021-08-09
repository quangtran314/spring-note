package com.amela.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class NoteType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    @Length(max = 100)
    @NotNull
    private String name;

    @Column(name = "description")
    @Length(max = 200)
    private String description;

    @OneToMany(targetEntity = Note.class)
    public List<Note> notes;

    public NoteType() {
    }

    public NoteType(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public NoteType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Note> getBlogs() {
        return notes;
    }

    public void setBlogs(List<Note> blogs) {
        this.notes = blogs;
    }
}
