package com.amela.service.note;

import com.amela.model.Note;
import com.amela.model.NoteType;
import com.amela.model.User;
import com.amela.repository.INoteRepository;
import com.amela.service.note.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class NoteService implements INoteService {

    @Autowired
    private INoteRepository noteRepository;

    @Override
    public Page<Note> findAll(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }

    @Override
    public Optional<Note> findByIdAndUser(int id, User user) {
        return noteRepository.findByIdAndUser(id, user);
    }

    @Override
    public Page<Note> findAllByTitleContainingAndUser(String title, User user, Pageable pageable) {
        return noteRepository.findAllByTitleContainingAndUser(title, user, pageable);
    }

    @Override
    public Page<Note> findAllByNoteTypeAndUser(NoteType noteType, User user, Pageable pageable) {
        return noteRepository.findAllByNoteTypeAndUser(noteType, user, pageable);
    }

    @Override
    public Page<Note> findAllByUser(User user, Pageable pageable) {
        return noteRepository.findAllByUser(user, pageable);
    }

    @Override
    public void save(Note note) {
        noteRepository.save(note);
    }

    @Override
    public void delete(int id) {
        noteRepository.deleteById(id);
    }
}
