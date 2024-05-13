package com.comrade.reactjsbackend.service;

import com.comrade.reactjsbackend.entity.Event;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EventService {
    public Event save(Event event);
    public List<Event> all();
}
