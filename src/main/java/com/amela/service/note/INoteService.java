package com.amela.service.note;

import com.amela.model.Note;
import com.amela.model.NoteType;
import com.amela.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface INoteService {

    Page<Note> findAll(Pageable pageable);

    Optional<Note> findByIdAndUser(int id, User user);

    Page<Note> findAllByTitleContainingAndUser(String title, User user, Pageable pageable);

    Page<Note> findAllByNoteTypeAndUser(NoteType noteType, User user, Pageable pageable);

    Page<Note> findAllByUser(User user, Pageable pageable);

    void save(Note note);

    void delete(int id);
}
