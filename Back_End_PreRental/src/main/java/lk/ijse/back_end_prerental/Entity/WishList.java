package lk.ijse.back_end_prerental.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Author: vishmee
 * Date: 3/28/25
 * Time: 3:12 PM
 * Description:
 */
@Entity
@Table(name = "wish_list")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String plateNumber;
}
