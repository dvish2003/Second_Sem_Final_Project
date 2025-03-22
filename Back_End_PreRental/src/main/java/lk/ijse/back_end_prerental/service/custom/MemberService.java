package lk.ijse.back_end_prerental.service.custom;

import jakarta.validation.Valid;
import lk.ijse.back_end_prerental.dto.MemberDTO;
import lk.ijse.back_end_prerental.dto.UserDTO;
import lk.ijse.back_end_prerental.dto.VerifyMemberDTO;

import java.util.List;
import java.util.UUID;

/**
 * Author: vishmee
 * Date: 3/16/25
 * Time: 8:22 PM
 * Description:
 */

public interface MemberService {


    MemberDTO getMember(String email);

    int saveMember(MemberDTO memberDTO);

    //get all Member
    List<MemberDTO> getAllMembers();

    int deleteMember(VerifyMemberDTO verifyMemberDTO);

    //
    int verifyMember(String email, String code);

    //update member
    int updateMember(MemberDTO memberDTO);
}
