package lk.ijse.back_end_prerental.service.custom.IMPL;

import lk.ijse.back_end_prerental.Entity.AdminPayment;
import lk.ijse.back_end_prerental.Entity.Booking;
import lk.ijse.back_end_prerental.Entity.Payment;
import lk.ijse.back_end_prerental.dto.TransactionDTO;
import lk.ijse.back_end_prerental.repo.AdminPaymentRepository;
import lk.ijse.back_end_prerental.repo.BookingRepository;
import lk.ijse.back_end_prerental.repo.PaymentRepository;
import lk.ijse.back_end_prerental.service.custom.TransactionService;
import lk.ijse.back_end_prerental.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: vishmee
 * Date: 3/31/25
 * Time: 8:09 PM
 * Description:
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private AdminPaymentRepository adminPaymentRepository;
    @Autowired
    private BookingEmailService bookingEmailService;
@Override
public int saveTransaction(TransactionDTO transactionDTO){
        try{
            Booking booking = bookingRepository.save(modelMapper.map(transactionDTO.getBookingDTO(),Booking.class));
            transactionDTO.getBookingDTO().setId(booking.getId());
            if(booking != null){
                Payment payment = modelMapper.map(transactionDTO.getPaymentDTO(),Payment.class);
                payment.setBooking(booking);
                paymentRepository.save(payment);
                transactionDTO.getPaymentDTO().setId(payment.getId());

                if (payment != null){
                    AdminPayment  adminPayment = modelMapper.map(transactionDTO.getAdminPaymentDTO(),AdminPayment.class);
                    adminPayment.setPayment(payment);
                    adminPaymentRepository.save(adminPayment);
                    if (adminPayment != null){
                        System.out.println("payment Successfully saved");
                        bookingEmailService.sendEmail(transactionDTO);
                        return VarList.Created;

                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

}
