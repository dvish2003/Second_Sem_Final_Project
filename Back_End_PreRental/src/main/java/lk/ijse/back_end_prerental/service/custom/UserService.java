package lk.ijse.back_end_prerental.service.custom;


import lk.ijse.back_end_prerental.dto.UserDTO;
import lk.ijse.back_end_prerental.service.SuperBO;

public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String username);
}