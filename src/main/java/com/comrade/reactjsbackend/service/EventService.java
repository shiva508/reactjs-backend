package com.comrade.reactjsbackend.service;

import com.comrade.reactjsbackend.entity.Event;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EventService {
    Event save(Event event);
    List<Event> all();
}
