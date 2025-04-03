package lk.ijse.back_end_prerental.dto;

/**
 * Author: vishmee
 * Date: 3/19/25
 * Time: 3:43 AM
 * Description:
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class VerifyUserDTO {
    private String email;
    private String code;
    private String Nic;
}
