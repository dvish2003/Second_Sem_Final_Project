package lk.ijse.back_end_prerental.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
    private String national_id;
    private String address;
    private String city;
    private String postal_code;
    private String primary_phone_number;
    private String secondary_phone_number;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Member member;
    /*====================================================*/
    //profile picture upload
    private String fileName;
    private String filetype;
    @Lob
    private byte[] data;

    /*======================== Verification============================*/
    private boolean verified;
    private String verificationCode;
    /*==============================================================*/
    private Date joinDate;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;
}
