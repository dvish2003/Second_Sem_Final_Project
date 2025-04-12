package lk.ijse.back_end_prerental.dto;

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
    private PaySuDTO paySu;

}
