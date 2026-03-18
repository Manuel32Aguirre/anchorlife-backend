package com.manuel.anchorlife.anchorlife.repositories;

import com.manuel.anchorlife.anchorlife.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
