package lk.ijse.back_end_prerental.repo;

import lk.ijse.back_end_prerental.Entity.PaySuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Author: vishmee
 * Date: 4/12/25
 * Time: 3:15 AM
 * Description:
 */

public interface PaySuRepo extends JpaRepository<PaySuEntity, UUID> {
}
