package lk.ijse.back_end_prerental.dto;
import jakarta.persistence.*;
import lk.ijse.back_end_prerental.Entity.Payment;
import lk.ijse.back_end_prerental.Entity.User;
import lk.ijse.back_end_prerental.Entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

/**
 * Author: vishmee
 * Date: 3/11/25
 * Time: 9:20 PM
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDTO {
    private UUID id;
    private Date pickupDate;
    private Date returnDate;
    private String status;
    private double totalAmount;
    private PaymentDTO payment;
    private UserDTO customer;
    private VehicleDTO vehicle;
    private String MemberEmail;
    private Date LocalDate;

}
