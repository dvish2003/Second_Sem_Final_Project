package lk.ijse.back_end_prerental.repo;

import lk.ijse.back_end_prerental.Entity.Member;
import lk.ijse.back_end_prerental.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: vishmee
 * Date: 3/16/25
 * Time: 8:33 PM
 * Description:
 */

public interface MemberRepository extends JpaRepository<Member,Integer> {

    boolean existsByEmail(String email);

    Member findByEmail(String email);

    void deleteByEmail(String email);
}
