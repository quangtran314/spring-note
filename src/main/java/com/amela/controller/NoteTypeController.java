package com.amela.controller;

import com.amela.model.NoteType;
import com.amela.service.note.INoteService;
import com.amela.service.noteType.INoteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class NoteTypeController {

    private final int ROWS_PER_PAGE = 3;

    @Autowired
    private INoteService noteService;

    @Autowired
    private INoteTypeService noteTypeService;

    @GetMapping("/noteTypes")
    public ModelAndView listNoteTypes(@RequestParam(required = false, defaultValue = "0") int page, Pageable pageable) {
        pageable = PageRequest.of(page, ROWS_PER_PAGE);
        Page<NoteType> noteTypes = noteTypeService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/note_type/list");
        modelAndView.addObject("noteTypes", noteTypes);
        return modelAndView;
    }

    @GetMapping("/create-noteType")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/note_type/create");
        modelAndView.addObject("noteType", new NoteType());
        return modelAndView;
    }

    @PostMapping("/create-noteType")
    public ModelAndView saveNoteType(@Validated @ModelAttribute("noteType") NoteType noteType, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("/note_type/create");
            return modelAndView;
        }
        noteTypeService.save(noteType);
        ModelAndView modelAndView = new ModelAndView("/note_type/create");
        modelAndView.addObject("noteType", new NoteType());
        modelAndView.addObject("message", "New note type created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-noteType/{id}")
    public ModelAndView showEditForm(@PathVariable int id) {
        Optional<NoteType> noteType = noteTypeService.findById(id);
        if (noteType.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/note_type/edit");
            modelAndView.addObject("noteType", noteType.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-noteType")
    public ModelAndView updateNoteType(@Validated @ModelAttribute("noteType") NoteType noteType, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("/note_type/edit");
            return modelAndView;
        }
        noteTypeService.save(noteType);
        ModelAndView modelAndView = new ModelAndView("/note_type/edit");
        modelAndView.addObject("noteType", noteType);
        modelAndView.addObject("message", "note type updated successfully");
        return modelAndView;
    }
}
