package com.comrade.reactjsbackend.service;

import com.comrade.reactjsbackend.entity.Event;
import com.comrade.reactjsbackend.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;

    @Override
    @Transactional
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public List<Event> all() {
        return eventRepository.findAll();
    }
}
