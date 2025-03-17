package lk.ijse.back_end_prerental.controller;

import jakarta.validation.Valid;
import lk.ijse.back_end_prerental.dto.AuthDTO;
import lk.ijse.back_end_prerental.dto.ResponseDTO;
import lk.ijse.back_end_prerental.dto.UserDTO;
import lk.ijse.back_end_prerental.service.custom.UserService;
import lk.ijse.back_end_prerental.util.JwtUtil;
import lk.ijse.back_end_prerental.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    //constructor injection
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("test");
        return "test";
    }

    @GetMapping("/getUser")
    public ResponseEntity<ResponseDTO> getUser(@RequestParam String email) {
        System.out.println("Get User Use " + email);
        UserDTO userDTO = userService.searchUser(email);
        System.out.println("ndsfisjdifsn"+userDTO.getUid());
        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
        }
        return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Success", userDTO));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserDTO userDTO) {
        System.out.println("register");
        System.out.println(userDTO.getEmail());
        System.out.println(userDTO.getName());
        System.out.println(userDTO.getRole());
        try {
            int res = userService.saveUser(userDTO);
            switch (res) {
                case VarList.Created -> {
                    String token = jwtUtil.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO();
                    authDTO.setEmail(userDTO.getEmail());
                    authDTO.setToken(token);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PutMapping(value = "/updateUser2")
    public ResponseEntity<ResponseDTO> updateUser2(@RequestParam String email, @RequestBody @Valid UserDTO userDTO) {
        try{
            // update user email or Password then create new token
            int res = userService.updateUser2(userDTO);
            switch (res) {
                case VarList.Created -> {
            String token = jwtUtil.generateToken(userDTO);
            AuthDTO authDTO = new AuthDTO();
            authDTO.setEmail(userDTO.getEmail());
            authDTO.setToken(token);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "Success", authDTO));
        }
        case VarList.Not_Acceptable -> {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
        }
        default -> {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(value = "/updateUser")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody @Valid UserDTO userDTO) {
        System.out.println("Update User" + userDTO.getEmail());
        System.out.println("Update User" + userDTO.getRole());
        System.out.println("Update User" + userDTO.getPassword());
        System.out.println("Update User" + userDTO.getName());
        try{
            int res = userService.updateUser(userDTO);
            switch (res) {
                case VarList.OK -> {
                    System.out.println("User updated");
                    return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User Updated Successfully", null));
                }
                case VarList.Not_Found -> {
                    System.out.println("User not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
                }
                default -> {
                    System.out.println("Error updating user");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(new ResponseDTO(VarList.Internal_Server_Error, "Error", null));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
