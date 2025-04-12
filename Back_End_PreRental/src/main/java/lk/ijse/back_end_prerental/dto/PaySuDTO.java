package lk.ijse.back_end_prerental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: vishmee
 * Date: 4/12/25
 * Time: 2:59 AM
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaySuDTO {
    private String token;
    private String expiryDate;
    private String cvv;
    private String cardName;
    private String userEmail;
}
