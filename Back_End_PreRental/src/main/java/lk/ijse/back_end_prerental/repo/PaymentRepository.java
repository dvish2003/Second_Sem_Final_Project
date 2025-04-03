package lk.ijse.back_end_prerental.repo;

import lk.ijse.back_end_prerental.Entity.Booking;
import lk.ijse.back_end_prerental.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

/**
 * Author: vishmee
 * Date: 3/29/25
 * Time: 2:20 PM
 * Description:
 */

public interface PaymentRepository extends JpaRepository<Payment,String> {
    Payment findPaymentByBookingId(UUID booking_id);
    List<Payment> findAllByMemberEmail(String memberEmail);
    List<Payment> findAllByCustomerEmail(String customerEmail);


}
