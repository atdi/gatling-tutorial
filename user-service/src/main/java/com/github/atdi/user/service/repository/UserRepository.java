package com.github.atdi.user.service.repository;

import com.github.atdi.user.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by aurelavramescu on 07/04/16.
 */
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(@Param("email") String email);

}
