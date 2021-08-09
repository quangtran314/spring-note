package com.amela.controller;

import com.amela.model.Note;
import com.amela.model.NoteType;
import com.amela.model.User;
import com.amela.service.note.INoteService;
import com.amela.service.noteType.INoteTypeService;
import com.amela.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class NoteController {
    private final int ROWS_PER_PAGE = 3;

    @Autowired
    private INoteService noteService;

    @Autowired
    private INoteTypeService noteTypeService;

    @Autowired
    private IUserService userService;

    @ModelAttribute("noteTypes")
    public Iterable<NoteType> noteTypes(){
        Iterable<NoteType> temp = noteTypeService.findAll();
        return temp;
    }

    @ModelAttribute("user")
    public User user(){
        Optional<User> user = userService.findByUsername(getPrincipal());
        return user.get();
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @GetMapping("/notes")
    public ModelAndView listNotes(@RequestParam(required = false, defaultValue = "0") int page,
                                  @RequestParam(name = "sort", required = false, defaultValue = "false") boolean sort,
                                  Pageable pageable) {
        if(sort){
            pageable = PageRequest.of(page, ROWS_PER_PAGE, Sort.by("title"));
        } else {
            pageable = PageRequest.of(page, ROWS_PER_PAGE);
        }
        Optional<User> user = userService.findByUsername(getPrincipal());
        Page<Note> notes = noteService.findAllByUser(user.get(), pageable);
        ModelAndView modelAndView = new ModelAndView("/note/list");
        modelAndView.addObject("notes", notes);
        modelAndView.addObject("sort", sort);
        return modelAndView;
    }

    @GetMapping("/view-noteType/{id}")
    public ModelAndView viewNoteType(@PathVariable("id") int id, @RequestParam(required = false, defaultValue = "0") int page, Pageable pageable){
        pageable = PageRequest.of(page, ROWS_PER_PAGE);
        Optional<NoteType> noteTypeOptional = noteTypeService.findById(id);
        if(!noteTypeOptional.isPresent()){
            return new ModelAndView("/error.404");
        }

        Page<Note> notes = noteService.findAllByNoteTypeAndUser(noteTypeOptional.get(), user(), pageable);

        ModelAndView modelAndView = new ModelAndView("/note_type/view");
        modelAndView.addObject("noteType", noteTypeOptional.get());
        modelAndView.addObject("notes", notes);
        return modelAndView;
    }

    @GetMapping("/create-note")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/note/create");
        modelAndView.addObject("note", new Note());
        return modelAndView;
    }

    @PostMapping("/create-note")
    public ModelAndView saveNote(@Validated @ModelAttribute("note") Note note, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("/note/create");
            return modelAndView;
        }
        noteService.save(note);
        ModelAndView modelAndView = new ModelAndView("/note/create");
        modelAndView.addObject("note", new Note());
        modelAndView.addObject("message", "New note created successfully");
        return modelAndView;
    }

    @PostMapping("/search")
    public ModelAndView searchNotes(@RequestParam("title") String title, Pageable pageable){
        pageable = PageRequest.of(0, ROWS_PER_PAGE);
        Page<Note> notes = noteService.findAllByTitleContainingAndUser(title, user(), pageable);
        ModelAndView modelAndView = new ModelAndView("/note/searchResult");
        modelAndView.addObject("notes", notes);
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @GetMapping("/search-result")
    public ModelAndView searchResult(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(name = "title", required = false) String title, Pageable pageable){
        pageable = PageRequest.of(page, ROWS_PER_PAGE);
        Page<Note> notes = noteService.findAllByTitleContainingAndUser(title, user(), pageable);
        ModelAndView modelAndView = new ModelAndView("/note/searchResult");
        modelAndView.addObject("notes", notes);
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @GetMapping("/view-note/{id}")
    public ModelAndView noteView(@PathVariable int id){
        Optional<Note> note = noteService.findByIdAndUser(id, user());
        if(note.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/note/view");
            modelAndView.addObject("note", note.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @GetMapping("/edit-note/{id}")
    public ModelAndView showEditForm(@PathVariable int id) {
        Optional<Note> note = noteService.findByIdAndUser(id, user());
        if (note.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/note/edit");
            modelAndView.addObject("note", note.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-note")
    public ModelAndView updateNote(@Validated @ModelAttribute("note") Note note, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("/note/edit");
            return modelAndView;
        }
        noteService.save(note);
        ModelAndView modelAndView = new ModelAndView("/note/edit");
        modelAndView.addObject("note", note);
        modelAndView.addObject("message", "Note updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete-note/{id}")
    public ModelAndView showDeleteForm(@PathVariable int id) {
        Optional<Note> note = noteService.findByIdAndUser(id, user());
        if (note != null) {
            ModelAndView modelAndView = new ModelAndView("/note/delete");
            modelAndView.addObject("note", note.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-note")
    public String deleteNote(@ModelAttribute("note") Note note) {
        noteService.delete(note.getId());
        return "redirect:notes";
    }
}
