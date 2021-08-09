package com.amela.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    @Length(max = 100)
    @NotEmpty
    private String title;

    @Column(name = "content", nullable = false)
    @Length(max = 500)
    @NotEmpty
    private String content;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private NoteType noteType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Note() {
    }

    public Note(int id, String title, String content, NoteType noteType, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.noteType = noteType;
        this.user = user;
    }

    public Note(String title, String content, NoteType noteType, User user) {
        this.title = title;
        this.content = content;
        this.noteType = noteType;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
