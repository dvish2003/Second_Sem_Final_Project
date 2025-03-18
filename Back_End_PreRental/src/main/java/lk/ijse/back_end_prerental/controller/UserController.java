package lk.ijse.back_end_prerental.controller;

import jakarta.validation.Valid;
import lk.ijse.back_end_prerental.Entity.User;
import lk.ijse.back_end_prerental.dto.AuthDTO;
import lk.ijse.back_end_prerental.dto.ResponseDTO;
import lk.ijse.back_end_prerental.dto.UserDTO;
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

 /*   @PutMapping(value = "/updateUser")
    public ResponseEntity<ResponseDTO> updateUser(@RequestParam("file") @RequestBody @Valid UserDTO userDTO,MultipartFile file) {
        System.out.println("Update User" + userDTO.getEmail());
        System.out.println("Update User" + userDTO.getRole());
        System.out.println("Update User" + userDTO.getPassword());
        System.out.println("Update User" + userDTO.getName());
        try{
            // check if file is not null
            if (!file.isEmpty()) {
                // save the file
                Path filePath = Paths.get(System.getProperty("user.dir") + "/uploads/" + file.getOriginalFilename());
                try {
                    Files.copy(file.getInputStream(), filePath);
                    System.out.println("File uploaded successfully.");
                } catch (IOException e) {
                    System.out.println("Error uploading file: " + e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(new ResponseDTO(VarList.Internal_Server_Error, "Error", null));
                }
            }

            userDTO.setProfilePicture(file.getOriginalFilename());
            System.out.println("File name: " + userDTO.getProfilePicture());
            System.out.println("File size: " + file.getSize());
            System.out.println("File type: " + file.getContentType());

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
*//*
@PutMapping(value = "/updateUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<ResponseDTO> updateUser(
        @RequestParam("email") String email,
        @RequestParam("name") String name,
        @RequestParam("national_id") String nationalId,
        @RequestParam("address") String address,
        @RequestParam("city") String city,
        @RequestParam("postal_code") String postalCode,
        @RequestParam("primary_phone_number") String primaryPhoneNumber,
        @RequestParam(value = "secondary_phone_number", required = false) String secondaryPhoneNumber,
        @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture) {

    System.out.println("Update User: " + email);

    if (profilePicture != null && !profilePicture.isEmpty()) {
        try {
            String uploadDir = "uploads/";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            Path filePath = Paths.get(uploadDir + profilePicture.getOriginalFilename());
            profilePicture.transferTo(filePath.toFile());
            System.out.println("Profile picture saved: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "File upload failed!", null));
        }
    }

    // Create UserDTO (modify this according to your DTO)
    UserDTO userDTO = new UserDTO();
    userDTO.setEmail(email);
    userDTO.setName(name);
    userDTO.setNational_id(nationalId);
    userDTO.setAddress(address);
    userDTO.setCity(city);
    userDTO.setPostal_code(postalCode);
    userDTO.setPrimary_phone_number(primaryPhoneNumber);
    userDTO.setSecondary_phone_number(secondaryPhoneNumber);
    userDTO.setProfilePicture(String.valueOf(profilePicture));

    try {
        int res = userService.updateUser(userDTO);
        switch (res) {
            case VarList.OK:
                return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User Updated Successfully", null));
            case VarList.Not_Found:
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, "Error", null));
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(VarList.Internal_Server_Error, "Update failed!", null));
    }
}
*/


}
