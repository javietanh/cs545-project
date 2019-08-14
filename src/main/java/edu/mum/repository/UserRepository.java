package edu.mum.repository;

import edu.mum.domain.Message;
import edu.mum.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // find user by email
    User findByEmail(String email);

    // get last 5 unread notify message of user by email.
    @Query(value = "select m from Message m join User u on m.user = u.id where m.read = 0 and  u.email = ?1 and rownum < 5 order by m.receivedDate desc")
    List<Message> getLast5UnreadNotifyMessageByUserEmail(String email);

}
