package lk.ijse.back_end_prerental.controller;

import lk.ijse.back_end_prerental.dto.ResponseDTO;
import lk.ijse.back_end_prerental.dto.VerifyUserDTO;
import lk.ijse.back_end_prerental.service.custom.IMPL.UserServiceImpl;
import lk.ijse.back_end_prerental.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Author: vishmee
 * Date: 3/18/25
 * Time: 10:45 PM
 * Description:
 */

@RestController
@RequestMapping("/api/v1/verifyUser")
public class VerificationController {
    private final UserServiceImpl userService;

    public VerificationController(UserServiceImpl userService) {

        this.userService = userService;
    }

    @PostMapping("/verify")
    public ResponseEntity<ResponseDTO> verifyUser(@RequestBody VerifyUserDTO verifyUserDTO){
        System.out.println("email     "+ verifyUserDTO.getEmail());
        System.out.println("code     "+ verifyUserDTO.getCode());
        try {
            int res  = userService.verifyUser(verifyUserDTO.getEmail(), verifyUserDTO.getCode());
            switch (res) {
                case VarList.OK:
                    return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Verification Successful", null));
                case VarList.Not_Found:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
                default:
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseDTO(VarList.Not_Acceptable, "Invalid Verification Code", null));
                 }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/verify2")
    public ResponseEntity<ResponseDTO> verifyUser2(@RequestBody VerifyUserDTO verifyUserDTO){
        System.out.println("email     "+ verifyUserDTO.getEmail());
        System.out.println("code     "+ verifyUserDTO.getCode());
        try {
            int res  = userService.verifyUser2(verifyUserDTO.getEmail(), verifyUserDTO.getCode());
            switch (res) {
                case VarList.OK:
                    return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Verification Successful", verifyUserDTO));
                case VarList.Not_Found:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
                default:
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseDTO(VarList.Not_Acceptable, "Invalid Verification Code", null));
                 }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
