package lk.ijse.back_end_prerental.service.custom.IMPL;

import lk.ijse.back_end_prerental.dto.WishListDTO;
import lk.ijse.back_end_prerental.repo.WishListRepo;
import lk.ijse.back_end_prerental.service.custom.WishList;
import lk.ijse.back_end_prerental.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: vishmee
 * Date: 4/6/25
 * Time: 10:24 AM
 * Description:
 */
@Service
@Transactional
public class WishListServiceImpl implements WishList {
    @Autowired
    private WishListRepo wishListRepository;
    @Autowired
    private ModelMapper modelMapper;

/*    public int save(WishListDTO wishListDTO) {
        if (wishListRepository.existsByPlateNumber(wishListDTO.getPlateNumber())) {
            return VarList.Found;
        }

        try {
            WishList wishList = modelMapper.map(wishListDTO, WishList.class);
            wishListRepository.save(wishList);
            return VarList.Created;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save wishlist", e);
        }*/

}
