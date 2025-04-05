package lk.ijse.back_end_prerental.service.custom;


import jakarta.validation.Valid;
import lk.ijse.back_end_prerental.dto.UserDTO;
import lk.ijse.back_end_prerental.dto.VerifyUserDTO;
import lk.ijse.back_end_prerental.service.SuperBO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
    //get last 4 users
    List<UserDTO> getLast4Users();

    int verifyUser(String email, String code);

    int saveUser(UserDTO userDTO);

    List<UserDTO> getAllUser();

    UserDTO searchUser(String username);

     int updateUser(UserDTO userDTO);

    int deactiveAccount(VerifyUserDTO verifyUserDTO);

    int verifyUser2(String email, String code);

    int updateUser(UserDTO userDTO, MultipartFile multipartFile);

    int updateUser2(UserDTO userDTO);

    List<UserDTO> getUsers();

    int deleteUser(String email);

    int CodeSent(String useEmail);

}