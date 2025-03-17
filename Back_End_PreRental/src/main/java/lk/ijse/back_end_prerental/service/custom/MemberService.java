package lk.ijse.back_end_prerental.service.custom;

import lk.ijse.back_end_prerental.dto.MemberDTO;
import lk.ijse.back_end_prerental.dto.UserDTO;

import java.util.UUID;

/**
 * Author: vishmee
 * Date: 3/16/25
 * Time: 8:22 PM
 * Description:
 */

public interface MemberService {


    MemberDTO getMember(String email);
}
