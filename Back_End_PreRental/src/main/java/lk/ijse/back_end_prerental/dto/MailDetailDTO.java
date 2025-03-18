package lk.ijse.back_end_prerental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: vishmee
 * Date: 3/18/25
 * Time: 10:32 PM
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailDetailDTO {
    private String toMail;
    private String subject;
    private String body;
}
