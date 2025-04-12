package lk.ijse.back_end_prerental.service.custom.IMPL;

import lk.ijse.back_end_prerental.dto.WishListDTO;
import lk.ijse.back_end_prerental.Entity.WishList;
import lk.ijse.back_end_prerental.repo.WishListRepo;
import lk.ijse.back_end_prerental.service.custom.WishListService;
import lk.ijse.back_end_prerental.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: vishmee
 * Date: 4/6/25
 * Time: 10:24 AM
 * Description:
 */
@Service
@Transactional
public class WishListServiceImpl implements WishListService {
    @Autowired
    private WishListRepo wishListRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int save(WishListDTO wishListDTO) {
        try{
            if(wishListRepository.existsByPlateNumber(wishListDTO.getPlateNumber())){
                return VarList.Conflict;
            }
            WishList wishList = modelMapper.map(wishListDTO,WishList.class);
            wishListRepository.save(wishList);
            return VarList.Created;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int Delete(WishListDTO wishListDTO) {
        String email = wishListDTO.getEmail();
        String plateNumber = wishListDTO.getPlateNumber();

        List<WishList> wishLists = wishListRepository.findAllByEmail(email);
        for(WishList wishList : wishLists){
            System.out.println("fffffffffffffffffffffffffffffffffffffff"+wishList.getPlateNumber());
            if(wishList.getPlateNumber().equals(plateNumber)){
                wishListRepository.delete(wishList);
                return VarList.OK;
            }
        }
        return VarList.Not_Found;
    }


}
