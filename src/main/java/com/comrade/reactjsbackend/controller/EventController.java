package com.comrade.reactjsbackend.controller;

import com.comrade.reactjsbackend.entity.Event;
import com.comrade.reactjsbackend.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
@AllArgsConstructor
//@CrossOrigin("http://localhost:5173")
public class EventController {

    private final EventService eventService;

    @PostMapping("/save")
    public Event save(@RequestBody Event event){
        return eventService.save( event);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public List<Event> all(){
        return eventService.all();
    }
}
