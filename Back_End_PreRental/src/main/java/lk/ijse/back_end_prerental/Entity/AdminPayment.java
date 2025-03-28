package lk.ijse.back_end_prerental.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

/**
 * Author: vishmee
 * Date: 3/29/25
 * Time: 1:43 AM
 * Description:
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private double amount;
    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;
}
