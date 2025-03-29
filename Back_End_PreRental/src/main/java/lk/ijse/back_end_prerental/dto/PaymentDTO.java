package lk.ijse.back_end_prerental.dto;

import jakarta.persistence.*;
import lk.ijse.back_end_prerental.Entity.AdminPayment;
import lk.ijse.back_end_prerental.Entity.Booking;
import lk.ijse.back_end_prerental.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

/**
 * Author: vishmee
 * Date: 3/29/25
 * Time: 12:21 AM
 * Description:
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {
    private UUID id;
    private String paymentMethod;
    private double amount;
    private double DepositAmount;
    private double ServiceCharge;
    private String currency;
    private Date paymentDate;
    private BookingDTO booking;
    private String memberEmail;
    private String CustomerEmail;
    private AdminPaymentDTO adminPayment;

}
