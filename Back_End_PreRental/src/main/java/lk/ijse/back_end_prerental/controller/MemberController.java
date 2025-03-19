package lk.ijse.back_end_prerental.controller;

import jakarta.validation.Valid;
import lk.ijse.back_end_prerental.dto.MemberDTO;
import lk.ijse.back_end_prerental.dto.ResponseDTO;
import lk.ijse.back_end_prerental.dto.UserDTO;
import lk.ijse.back_end_prerental.service.custom.MemberService;
import lk.ijse.back_end_prerental.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Author: vishmee
 * Date: 3/16/25
 * Time: 9:04 PM
 * Description:
 */
@RestController
@RequestMapping("/api/v1/member")
@CrossOrigin
public class MemberController {
    private  final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/getMemberInfo")
    public ResponseEntity<ResponseDTO> getMemberByUserId(@RequestParam String email) {
        System.out.println("Request Accepted");

        MemberDTO memberDTO = memberService.getMember(email);

        if (memberDTO == null) {
            return ResponseEntity.ok(
                    new ResponseDTO(VarList.OK, "not found a member", null)
            );
        } else {
            return ResponseEntity.ok(
                    new ResponseDTO(VarList.OK, "Already a member", memberDTO)
            );
        }
    }


    @PostMapping(value ="/saveMemberInfo")
    public ResponseEntity<ResponseDTO> saveMember(@RequestBody MemberDTO memberDTO){
        System.out.println(memberDTO);
        MemberDTO memberDTO1 = memberDTO;
        memberDTO1.setNicNumber(memberDTO.getUserDTO().getNational_id());
       try{
           Date localDate = Date.valueOf(LocalDate.now());
           memberDTO.setJoinDate(localDate);
           int res = memberService.saveMember(memberDTO1);
           switch(res){
               case VarList.Created:
                   System.out.println("Member saved successfully");
                   return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Member saved successfully", memberDTO1));
               case VarList.Not_Found:
                   System.out.println("Member already exists");
                   return ResponseEntity.status(HttpStatus.NOT_FOUND)
                          .body(new ResponseDTO(VarList.Not_Found, "Member already exists", null));
               default:
                   System.out.println("Error saving member");
                   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                          .body(new ResponseDTO(VarList.Internal_Server_Error, "Error saving member", null));
           }
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }
}
