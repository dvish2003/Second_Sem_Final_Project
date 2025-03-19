package lk.ijse.back_end_prerental.repo;


import lk.ijse.back_end_prerental.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;
import java.util.stream.DoubleStream;


public interface UserRepository extends JpaRepository<User,String> {

    User findByEmail(String userName);

    boolean existsByEmail(String userName);

    int deleteByEmail(String userName);

   /* @Query(value = "SELECT * FROM users ORDER BY join_date DESC LIMIT 4", nativeQuery = true)
    List<User> findLast4MembersByJoinDate();*/
}