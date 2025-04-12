package lk.ijse.back_end_prerental.repo;

import lk.ijse.back_end_prerental.Entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author: vishmee
 * Date: 4/6/25
 * Time: 10:25 AM
 * Description:
 */

public interface WishListRepo extends JpaRepository<WishList,String> {
    boolean existsByPlateNumber(String plateNumber);

    WishList findByPlateNumber(String plateNumber);

    List<WishList> findAllByEmail(String email);

}
