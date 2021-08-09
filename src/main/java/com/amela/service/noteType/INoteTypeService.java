package com.amela.service.noteType;

import com.amela.model.NoteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface INoteTypeService {
    Page<NoteType> findAll(Pageable pageable);

    Iterable<NoteType> findAll();

    Optional<NoteType> findById(int id);

    void save(NoteType noteType);

    void remove(int id);
}
