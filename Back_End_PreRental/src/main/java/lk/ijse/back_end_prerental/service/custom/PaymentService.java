package lk.ijse.back_end_prerental.service.custom;

import lk.ijse.back_end_prerental.dto.PaymentDTO;

import java.util.List;
import java.util.UUID;

/**
 * Author: vishmee
 * Date: 3/29/25
 * Time: 3:26 PM
 * Description:
 */

public interface PaymentService {
    int savePayment(PaymentDTO paymentDTO);

    List<PaymentDTO> getPaymentDTO();

    PaymentDTO getPayment(UUID paymentId);
}
