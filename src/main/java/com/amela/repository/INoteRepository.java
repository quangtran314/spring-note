package com.amela.repository;

import com.amela.model.Note;
import com.amela.model.NoteType;
import com.amela.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface INoteRepository extends PagingAndSortingRepository<Note, Integer> {
    Page<Note> findAllByNoteTypeAndUser(NoteType noteType, User user, Pageable pageable);
    Page<Note> findAllByUser(User user, Pageable pageable);
    Page<Note> findAllByTitleContainingAndUser(String title, User user, Pageable pageable);
    Optional<Note> findByIdAndUser(int id, User user);
}
