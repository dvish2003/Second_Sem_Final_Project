package lk.ijse.back_end_prerental.controller;

import jakarta.validation.Valid;
import lk.ijse.back_end_prerental.Entity.User;
import lk.ijse.back_end_prerental.dto.AuthDTO;
import lk.ijse.back_end_prerental.dto.ResponseDTO;
import lk.ijse.back_end_prerental.dto.UserDTO;
import lk.ijse.back_end_prerental.dto.VerifyUserDTO;
import lk.ijse.back_end_prerental.repo.UserRepository;
import lk.ijse.back_end_prerental.service.custom.IMPL.UserServiceImpl;
import lk.ijse.back_end_prerental.service.custom.UserService;
import lk.ijse.back_end_prerental.util.JwtUtil;
import lk.ijse.back_end_prerental.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    //constructor injection
    public UserController(UserService userService, UserRepository userRepository, JwtUtil jwtUtil) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("test");
        return "test";
    }

    @GetMapping("/get4Users")
    public ResponseEntity<ResponseDTO> getTop4Users() {
        System.out.println("get 4 Users");
      try{ List<UserDTO> users = userService.getLast4Users();
          if (users.isEmpty()) {
              return ResponseEntity.status(HttpStatus.NOT_FOUND)
                      .body(new ResponseDTO(VarList.Not_Found, "Users Not Found", null));
          }else{
              return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Success", users));

          }} catch (Exception e) {
          throw new RuntimeException(e);
      }
    }

    @GetMapping("/getProfilePic/{userEmail}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable String userEmail) {
        User user = userRepository.findByEmail(String.valueOf(userEmail));
        byte[] imageData = user.getData();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
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
    public ResponseEntity<ResponseDTO> updateUser(
            @RequestPart("userDTO") @Valid UserDTO userDTO,
            @RequestParam("file") MultipartFile file) {

        System.out.println("Update User: " + userDTO.getEmail());

        try {
            if (!file.isEmpty()) {
                Path filePath = Paths.get(System.getProperty("user.dir") + "/uploads/" + file.getOriginalFilename());
                try {
                    Files.copy(file.getInputStream(), filePath);
                    System.out.println("File uploaded successfully.");
                } catch (IOException e) {
                    System.out.println("Error uploading file: " + e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseDTO(VarList.Internal_Server_Error, "Error uploading file", null));
                }
                userDTO.setProfilePicture(file.getOriginalFilename());
            }
            //get local date
            Date localDate = Date.valueOf(LocalDate.now());
            userDTO.setJoinDate(localDate);
            int res = userService.updateUser(userDTO, file);

            switch (res) {
                case VarList.OK:
                    System.out.println("User updated");
                    return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User Updated Successfully", null));
                case VarList.Not_Found:
                    System.out.println("User not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
                default:
                    System.out.println("Error updating user");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseDTO(VarList.Internal_Server_Error, "Error updating user", null));
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Internal Server Error", null));
        }
    }
    @PostMapping(value = "/updateUser3")
    public ResponseEntity<ResponseDTO> updateUser3(@RequestBody @Valid UserDTO userDTO) {
        try {
            int res = userService.updateUser(userDTO);
            switch (res) {
                case VarList.OK:
                    System.out.println("User updated");
                    return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User Updated Successfully", userDTO));
                case VarList.Not_Found:
                    System.out.println("User not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
                default:
                    System.out.println("Error updating user");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseDTO(VarList.Internal_Server_Error, "Error updating user", null));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

       }

       @PostMapping(value = "/deleteUser")
       public ResponseEntity<ResponseDTO> deleteUser(@RequestBody VerifyUserDTO verifyUserDTO) {
        try {
            int res = userService.deactiveAccount(verifyUserDTO);
            switch (res) {
                case VarList.OK:
                    System.out.println("User deleted");
                    return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User Deleted Successfully", verifyUserDTO));
                case VarList.Not_Found:
                    System.out.println("User not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
                default:
                    System.out.println("Error deleting user");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseDTO(VarList.Internal_Server_Error, "Error deleting user", null));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
       }

       @PostMapping(value = "/falseNullUser")
       public ResponseEntity<ResponseDTO> falseNullUser(@RequestBody VerifyUserDTO verifyUserDTO) {
        try {
            int res = userService.CodeSent(verifyUserDTO.getEmail());
            switch (res) {
                case VarList.OK:
                    System.out.println("User verified code sent");
                    return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User Verified Successfully", verifyUserDTO));
                case VarList.Not_Found:
                    System.out.println("User not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
                default:
                    System.out.println("Error verifying user");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseDTO(VarList.Internal_Server_Error, "Error verifying user", null));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
       }
       @GetMapping(value = "/getAllUsers")
       public ResponseEntity<ResponseDTO> getAllUsers() {
        try {
            List<UserDTO> users = userService.getUsers();
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "All Users", users));
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(VarList.Internal_Server_Error, "Failed to get  List", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
       }
        }
