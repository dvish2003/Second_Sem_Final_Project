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
    public ResponseEntity<ResponseDTO> getMemberByUserId(@RequestParam String email){
        MemberDTO memberDTO = memberService.getMember(email);
        if(memberDTO == null){
            System.out.println("member not found");
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Member not found", null));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "Already you are member ", memberDTO));
        }

    }


    @PostMapping(value ="/saveMemberInfo")
    public ResponseEntity<ResponseDTO> saveMember(@RequestBody @Valid MemberDTO memberDTO){
        System.out.println("ddddddddddddddddaaaaaaaaaaaaaaaaaaaaaaaaffffffffffffffffffffffffffffffffffff");
        System.out.println(memberDTO.getName());
        System.out.println(memberDTO.getUserDTO().getRole()+"dhhhhhhhhhhhhhhhhhhhhhh");
        System.out.println(memberDTO.getAddress());
        return null;
    }
}
