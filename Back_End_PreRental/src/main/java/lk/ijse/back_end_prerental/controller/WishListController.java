package lk.ijse.back_end_prerental.controller;

import lk.ijse.back_end_prerental.dto.MemberDTO;
import lk.ijse.back_end_prerental.dto.ResponseDTO;
import lk.ijse.back_end_prerental.dto.WishListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Author: vishmee
 * Date: 4/6/25
 * Time: 10:20 AM
 * Description:
 */
@RestController
@RequestMapping("/api/v1/wishList")
@CrossOrigin
public class WishListController {
    @PostMapping(value = "saveWishLsit")
    public ResponseEntity<ResponseDTO> SaveDate(@RequestBody WishListDTO wishListDTO) {

        return null;
    }
}
