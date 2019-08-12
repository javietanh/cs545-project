package edu.mum.repository;

import edu.mum.domain.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query(value = "update Message set read = 1 where id = ?1")
    void setMessageRead(Long id);
}
