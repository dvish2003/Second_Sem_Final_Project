package lk.ijse.back_end_prerental.dto;

import jakarta.persistence.*;
import lk.ijse.back_end_prerental.Entity.Payment;
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
public class AdminPaymentDTO {
    private UUID id;
    private double amount;
    private PaymentDTO payment;
    private Date paymentDate;
}
