package lk.ijse.back_end_prerental.dto;

import jakarta.persistence.*;
import lk.ijse.back_end_prerental.Entity.User;
import lk.ijse.back_end_prerental.Entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

/**
 * Author: vishmee
 * Date: 3/11/25
 * Time: 9:20 PM
 * Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDTO {
    private int id;
    private String email;
    private String name;
    private String address;
    private String contact;
    private String NicNumber;
    private UserDTO userDTO;
    private Date joinDate;
    private boolean verified;
    private String verificationCode;

}
