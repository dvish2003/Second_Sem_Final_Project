package lk.ijse.back_end_prerental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: vishmee
 * Date: 3/29/25
 * Time: 12:21 AM
 * Description:
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {
    private VehicleDTO vehicleDTO;
    private BookingDTO bookingDTO;
    private UserDTO userDTO;
    private PaymentDTO paymentDTO;
    private  AdminPaymentDTO adminPaymentDTO;

}
