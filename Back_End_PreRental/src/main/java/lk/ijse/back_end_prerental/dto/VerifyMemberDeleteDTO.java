package lk.ijse.back_end_prerental.dto;

/**
 * Author: vishmee
 * Date: 3/23/25
 * Time: 12:36 AM
 * Description:
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class VerifyMemberDeleteDTO {
    private String email;
    private String code;
}
