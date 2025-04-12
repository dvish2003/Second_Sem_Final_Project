package lk.ijse.back_end_prerental.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Author: vishmee
 * Date: 3/11/25
 * Time: 9:20 PM
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FBTransactionalDTO {
    private String userEmail;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardName;
    private double totalAmount;
    private double serviceFee;
    private double depositCharge;
    private Date startDate;
    private Date endDate;
    private VehicleDTO vehicleDTO1;

}
