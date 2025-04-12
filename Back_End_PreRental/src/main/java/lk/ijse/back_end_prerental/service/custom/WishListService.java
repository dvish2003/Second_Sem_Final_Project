package lk.ijse.back_end_prerental.service.custom;

import lk.ijse.back_end_prerental.dto.WishListDTO;

/**
 * Author: vishmee
 * Date: 4/6/25
 * Time: 10:23 AM
 * Description:
 */

public interface WishListService {
    int save(WishListDTO wishListDTO);

    int Delete(WishListDTO wishListDTO);
}
