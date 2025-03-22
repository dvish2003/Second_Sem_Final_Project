package lk.ijse.back_end_prerental.controller;

import lk.ijse.back_end_prerental.dto.ResponseDTO;
import lk.ijse.back_end_prerental.dto.VerifyMemberDTO;
import lk.ijse.back_end_prerental.dto.VerifyMemberDeleteDTO;
import lk.ijse.back_end_prerental.dto.VerifyUserDTO;
import lk.ijse.back_end_prerental.service.custom.MemberService;
import lk.ijse.back_end_prerental.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: vishmee
 * Date: 3/22/25
 * Time: 11:44 PM
 * Description: 
 */
@RestController
@RequestMapping("/api/v1/verifyMember")
public class VerificationMemberController {
    private final MemberService memberService;

    public VerificationMemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @PostMapping("/verify")
    public ResponseEntity<ResponseDTO> verifyUser(@RequestBody VerifyMemberDeleteDTO verifyMemberDeleteDTO){
        System.out.println("member code verify controller qqqqqqqqqqqqqq");
        try {
            int res  = memberService.verifyMember(verifyMemberDeleteDTO.getEmail(), verifyMemberDeleteDTO.getCode());
            switch (res) {
                case VarList.OK:
                    return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Verification Successful", null));
                case VarList.Not_Found:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(VarList.Not_Found, "Member Not Found", null));
                default:
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseDTO(VarList.Not_Acceptable, "Invalid Verification Code", null));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
