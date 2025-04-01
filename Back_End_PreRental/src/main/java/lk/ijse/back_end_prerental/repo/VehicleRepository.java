package lk.ijse.back_end_prerental.repo;

import lk.ijse.back_end_prerental.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Author: vishmee
 * Date: 3/25/25
 * Time: 1:10 AM
 * Description:
 */

public interface VehicleRepository extends JpaRepository<Vehicle,Integer> {
    boolean existsByPlateNumber(String plateNumber);

    Vehicle findByPlateNumber(String plateNumber);
    List<Vehicle> findByMemberId(int memberId);
}