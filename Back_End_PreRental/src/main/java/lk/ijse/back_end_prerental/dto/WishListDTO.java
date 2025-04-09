package lk.ijse.back_end_prerental.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Author: vishmee
 * Date: 4/6/25
 * Time: 10:17 AM
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishListDTO {
    private UUID id;
    private String email;
    private String plateNumber;
}
