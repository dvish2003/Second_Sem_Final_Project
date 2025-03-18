package lk.ijse.back_end_prerental.dto;

/**
 * Author: vishmee
 * Date: 3/19/25
 * Time: 3:43 AM
 * Description:
 */

import jakarta.persistence.*;
import lk.ijse.back_end_prerental.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class VerifyDTO {
    private String email;
    private String code;
}
