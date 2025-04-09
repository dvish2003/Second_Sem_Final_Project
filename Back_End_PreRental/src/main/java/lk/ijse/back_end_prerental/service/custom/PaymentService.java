package lk.ijse.back_end_prerental.service.custom;

import lk.ijse.back_end_prerental.dto.PaymentDTO;
import lk.ijse.back_end_prerental.dto.PaymentDTOTm;

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

    List<PaymentDTOTm> getPaymentDTOByMemberEmail(String memberEmail);

    List<PaymentDTOTm> getAllPayment();

    List<PaymentDTOTm> getPaymentDTOByCustomerEmail(String customerEmail);

    PaymentDTO getPayment(UUID paymentId);
}
