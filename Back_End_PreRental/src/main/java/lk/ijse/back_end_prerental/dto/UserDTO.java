package lk.ijse.back_end_prerental.dto;

import jakarta.persistence.*;
import lk.ijse.back_end_prerental.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private UUID uid;
    private String email;
    private String name;
    private String password;
    private String role;
    private String national_id;
    private String address;
    private String city;
    private String postal_code;
    private String primary_phone_number;
    private String secondary_phone_number;
    private String profilePicture;
}
