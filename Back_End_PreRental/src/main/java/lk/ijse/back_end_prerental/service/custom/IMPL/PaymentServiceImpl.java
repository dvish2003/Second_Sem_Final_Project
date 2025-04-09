package lk.ijse.back_end_prerental.service.custom.IMPL;

import lk.ijse.back_end_prerental.Entity.Payment;
import lk.ijse.back_end_prerental.dto.PaymentDTO;
import lk.ijse.back_end_prerental.dto.PaymentDTOTm;
import lk.ijse.back_end_prerental.repo.PaymentRepository;
import lk.ijse.back_end_prerental.service.custom.PaymentService;
import lk.ijse.back_end_prerental.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: vishmee
 * Date: 3/29/25
 * Time: 3:26 PM
 * Description:
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaymentRepository paymentRepository;
@Override
public int savePayment(PaymentDTO paymentDTO){
        //save payment
        try {
            paymentRepository.save(modelMapper.map(paymentDTO, Payment.class));
           return VarList.Created;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
   @Override
   public List<PaymentDTO> getPaymentDTO(){
        List<Payment> paymentList = paymentRepository.findAll();
        return modelMapper.map(paymentList, new TypeToken<List<PaymentDTO>>() {}.getType());
    }
    @Override
    public List<PaymentDTOTm> getPaymentDTOByMemberEmail(String memberEmail){
        List<Payment> paymentList = paymentRepository.findAllByMemberEmail(memberEmail);
        List<PaymentDTOTm> paymentDTOTmList = new ArrayList<>();
        for (Payment payment : paymentList) {
            PaymentDTOTm paymentDTOTm = new PaymentDTOTm();
            paymentDTOTm.setId(payment.getId());
            paymentDTOTm.setBookingId(payment.getBooking().getId());
            paymentDTOTm.setPaymentMethod(payment.getPaymentMethod());
            paymentDTOTm.setAmount(payment.getAmount());
            paymentDTOTm.setPaymentDate(payment.getPaymentDate());
            paymentDTOTm.setCurrency(payment.getCurrency());
            paymentDTOTm.setDepositAmount(payment.getDepositAmount());
            paymentDTOTm.setServiceCharge(payment.getServiceCharge());
            paymentDTOTm.setCustomerEmail(payment.getCustomerEmail());
            paymentDTOTm.setMemberEmail(payment.getMemberEmail());
            paymentDTOTmList.add(paymentDTOTm);
        }
        return paymentDTOTmList;
    }
    @Override
    public List<PaymentDTOTm> getAllPayment(){
        List<Payment> paymentList = paymentRepository.findAll();
        List<PaymentDTOTm> paymentDTOTmList = new ArrayList<>();
        for (Payment payment : paymentList) {
            PaymentDTOTm paymentDTOTm = new PaymentDTOTm();
            paymentDTOTm.setId(payment.getId());
            paymentDTOTm.setBookingId(payment.getBooking().getId());
            paymentDTOTm.setPaymentMethod(payment.getPaymentMethod());
            paymentDTOTm.setAmount(payment.getAmount());
            paymentDTOTm.setPaymentDate(payment.getPaymentDate());
            paymentDTOTm.setCurrency(payment.getCurrency());
            paymentDTOTm.setDepositAmount(payment.getDepositAmount());
            paymentDTOTm.setServiceCharge(payment.getServiceCharge());
            paymentDTOTm.setCustomerEmail(payment.getCustomerEmail());
            paymentDTOTm.setMemberEmail(payment.getMemberEmail());
            paymentDTOTmList.add(paymentDTOTm);
        }
        return paymentDTOTmList;
    }
    @Override
    public List<PaymentDTOTm> getPaymentDTOByCustomerEmail(String customerEmail){
        List<Payment> paymentList = paymentRepository.findAllByCustomerEmail(customerEmail);
        List<PaymentDTOTm> paymentDTOTmList = new ArrayList<>();
        for (Payment payment : paymentList) {
            PaymentDTOTm paymentDTOTm = new PaymentDTOTm();
            paymentDTOTm.setId(payment.getId());
            paymentDTOTm.setBookingId(payment.getBooking().getId());
            paymentDTOTm.setPaymentMethod(payment.getPaymentMethod());
            paymentDTOTm.setAmount(payment.getAmount());
            paymentDTOTm.setPaymentDate(payment.getPaymentDate());
            paymentDTOTm.setCurrency(payment.getCurrency());
            paymentDTOTm.setDepositAmount(payment.getDepositAmount());
            paymentDTOTm.setServiceCharge(payment.getServiceCharge());
            paymentDTOTm.setCustomerEmail(payment.getCustomerEmail());
            paymentDTOTm.setMemberEmail(payment.getMemberEmail());
            paymentDTOTmList.add(paymentDTOTm);
        }
        return paymentDTOTmList;
    }


    @Override
    public PaymentDTO getPayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(String.valueOf(paymentId)).orElse(null);
        return modelMapper.map(payment, PaymentDTO.class);
    }


}
