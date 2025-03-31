package lk.ijse.back_end_prerental.repo;

import lk.ijse.back_end_prerental.Entity.Booking;
import lk.ijse.back_end_prerental.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

/**
 * Author: vishmee
 * Date: 3/29/25
 * Time: 2:19 PM
 * Description:
 */

public interface BookingRepository extends JpaRepository<Booking,String> {
    List<Booking> findBookingsByPickupDate(Date pickupDate);

/*
    @Query(value = "select * from bookings Where member_email : memberEmail" ,nativeQuery = true)
*/
    List<Booking> findByMemberEmail(String memberEmail);
}
