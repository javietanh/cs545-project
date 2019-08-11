package edu.mum.repository;

import edu.mum.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    // find user by email
    User findByEmail(String email);
}
