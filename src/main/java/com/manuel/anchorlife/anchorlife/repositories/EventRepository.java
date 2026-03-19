package com.manuel.anchorlife.anchorlife.repositories;

import com.manuel.anchorlife.anchorlife.models.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
