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
    private String email;
    private String name;
    private String password;
    private String role;
}
