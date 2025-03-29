package lk.ijse.back_end_prerental.repo;

import lk.ijse.back_end_prerental.Entity.AdminPayment;
import lk.ijse.back_end_prerental.Entity.Payment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: vishmee
 * Date: 3/29/25
 * Time: 2:22 PM
 * Description:
 */

public interface AdminPaymentRepository extends JpaRepository<AdminPayment,String> {

}
