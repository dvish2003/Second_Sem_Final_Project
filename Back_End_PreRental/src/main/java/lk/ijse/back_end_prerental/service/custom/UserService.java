package lk.ijse.back_end_prerental.service.custom;


import jakarta.validation.Valid;
import lk.ijse.back_end_prerental.dto.UserDTO;
import lk.ijse.back_end_prerental.service.SuperBO;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {
    int verifyUser(String email, String code);

    int saveUser(UserDTO userDTO);

     UserDTO searchUser(String username);

     int updateUser(UserDTO userDTO);

    int updateUser(UserDTO userDTO, MultipartFile multipartFile);

    int updateUser2(UserDTO userDTO);

}