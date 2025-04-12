package lk.ijse.back_end_prerental.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Author: vishmee
 * Date: 4/12/25
 * Time: 2:59 AM
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PaySuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String token;
    private String expiryDate;
    private String cvv;
    private String cardName;
    private String userEmail;
    @OneToOne(mappedBy = "paySu", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Payment payment;

}
