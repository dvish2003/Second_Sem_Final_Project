package lk.ijse.back_end_prerental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

/**
 * Author: vishmee
 * Date: 3/31/25
 * Time: 11:14 PM
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data

public class CusBookingDTO {

    private UUID id;
    private Date pickupDate;
    private Date returnDate;
    private String status;
    private String customerEmail;
    private String plateNumber;
    private String MemberEmail;
    private String memberContact;
    private String memberFileName;
    private double paymentAmount;
    private String customerFileName;
    private String customerName;
    private String customerContact;
    private String customerContact2;
    private String vehicleFileName;
    private Date localDate;
}
