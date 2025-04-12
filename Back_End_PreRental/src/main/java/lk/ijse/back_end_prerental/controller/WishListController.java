package lk.ijse.back_end_prerental.controller;

import lk.ijse.back_end_prerental.Entity.WishList;
import lk.ijse.back_end_prerental.dto.MemberDTO;
import lk.ijse.back_end_prerental.dto.ResponseDTO;
import lk.ijse.back_end_prerental.dto.VehicleDTO;
import lk.ijse.back_end_prerental.dto.WishListDTO;
import lk.ijse.back_end_prerental.repo.WishListRepo;
import lk.ijse.back_end_prerental.service.custom.WishListService;
import lk.ijse.back_end_prerental.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final WishListService wishListService;

    @Autowired
    private WishListRepo wishListRepository;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @PostMapping(value = "/saveWishLsit")
    public ResponseEntity<ResponseDTO> SaveWishList(@RequestBody WishListDTO wishListDTO) {
        try{
            int res = wishListService.save(wishListDTO);
            switch (res) {
                case VarList.Created:
                    System.out.println("add to WishList");
                    return ResponseEntity.ok(new ResponseDTO(VarList.Created, "add to WishList", wishListDTO));

                case VarList.Conflict:
                    System.out.println("All ready Added");
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(new ResponseDTO(VarList.Conflict, "All_Ready_Added", null));
                default:
                    System.out.println("Error updating");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseDTO(VarList.Internal_Server_Error, "Error updating", null));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping(value = "/deleteWishLsit")
    public ResponseEntity<ResponseDTO> deleteWishList(@RequestBody WishListDTO wishListDTO) {
        System.out.println("delete wenna badu awoooooooooooooooooooooo");
        try{
            int res = wishListService.Delete(wishListDTO);
            switch (res) {
                case VarList.OK:
                    System.out.println("remove to WishList");
                    return ResponseEntity.ok(new ResponseDTO(VarList.Created, "remove to WishList", wishListDTO));

                case VarList.Not_Found:
                    System.out.println("not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "not found", null));
                default:
                    System.out.println("Error updating");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseDTO(VarList.Internal_Server_Error, "Error updating", null));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/getAll")
    public ResponseEntity<ResponseDTO> getAll(@RequestBody WishListDTO wishListDTO) {
        System.out.println("WishLiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiis"+wishListDTO.getEmail());
        try {
            List<WishList> wishLists = wishListRepository.findAllByEmail(wishListDTO.getEmail());
            for (WishList wishList : wishLists){
                System.out.println("dddddddddd"+wishList.getEmail());
                System.out.println("dddddddddd"+wishList.getPlateNumber());
            }
            System.out.println(wishLists.size());
            return new ResponseEntity<>(new ResponseDTO(VarList.OK, "WishList List", wishLists), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(VarList.Internal_Server_Error, "Failed to get  List", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
