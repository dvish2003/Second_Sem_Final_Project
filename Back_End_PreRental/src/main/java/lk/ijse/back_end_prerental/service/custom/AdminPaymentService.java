package lk.ijse.back_end_prerental.service.custom;

import lk.ijse.back_end_prerental.dto.AdminPaymentDTO;

import java.util.List;

/**
 * Author: vishmee
 * Date: 3/29/25
 * Time: 3:27 PM
 * Description:
 */

public interface AdminPaymentService {
    int savePayment(AdminPaymentDTO adminPaymentDTO);

    List<AdminPaymentDTO> getPaymentDTO();
}
