package lk.ijse.back_end_prerental.service.custom;

import lk.ijse.back_end_prerental.dto.BookingDTO;

import java.sql.Date;
import java.util.List;

/**
 * Author: vishmee
 * Date: 3/29/25
 * Time: 3:25 PM
 * Description:
 */

public interface BookingService {
    int saveBooking(BookingDTO bookingDTO);

    List<BookingDTO> getBookings();

    List<BookingDTO> getBookingDTOUseByDate(Date pickUpDate);
}
