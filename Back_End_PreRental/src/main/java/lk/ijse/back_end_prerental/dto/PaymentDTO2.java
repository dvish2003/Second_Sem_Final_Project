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
public class PaymentDTO2 {
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardName;
    private String userEmail;
}
