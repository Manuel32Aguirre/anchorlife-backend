package com.manuel.anchorlife.anchorlife.repositories;

import com.manuel.anchorlife.anchorlife.models.entities.Event;
import com.manuel.anchorlife.anchorlife.models.entities.FixedEvent;
import com.manuel.anchorlife.anchorlife.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<FixedEvent> findFixedEventsById(Long id);
    List<Event>findEventsById(Long id);
}
