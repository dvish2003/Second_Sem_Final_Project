package lk.ijse.back_end_prerental.dto;

/**
 * Author: vishmee
 * Date: 3/22/25
 * Time: 11:41 PM
 * Description:
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class VerifyMemberDTO {
    private String email;
    private String NIC;
}
