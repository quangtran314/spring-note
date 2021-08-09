package com.amela.service.noteType;

import com.amela.model.NoteType;
import com.amela.repository.INoteTypeRepository;
import com.amela.service.noteType.INoteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class NoteTypeService implements INoteTypeService {

    @Autowired
    private INoteTypeRepository noteTypeRepository;

    @Override
    public Page<NoteType> findAll(Pageable pageable) {
        return noteTypeRepository.findAll(pageable);
    }

    @Override
    public Iterable<NoteType> findAll() {
        return noteTypeRepository.findAll();
    }

    @Override
    public Optional<NoteType> findById(int id) {
        return noteTypeRepository.findById(id);
    }

    @Override
    public void save(NoteType noteType) {
        noteTypeRepository.save(noteType);
    }

    @Override
    public void remove(int id) {
        noteTypeRepository.deleteById(id);
    }
}
