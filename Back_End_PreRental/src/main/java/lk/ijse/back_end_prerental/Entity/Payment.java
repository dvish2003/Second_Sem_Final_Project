package lk.ijse.back_end_prerental.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.UUID;

/**
 * Author: vishmee
 * Date: 3/28/25
 * Time: 3:11 PM
 * Description:
 */

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false,name = "Deposit_Amount")
    private double DepositAmount;

    @Column(nullable = false,name = "Service_Amount")
    private double ServiceCharge;


    @Column(nullable = false)
    private String currency;

    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_payment_booking"))
    private Booking booking;

    private String memberEmail;

    private String CustomerEmail;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private AdminPayment adminPayment;
}