package lk.ijse.back_end_prerental.controller;

import lk.ijse.back_end_prerental.dto.MemberDTO;
import lk.ijse.back_end_prerental.dto.PaymentDTO;
import lk.ijse.back_end_prerental.dto.PaymentDTOTm;
import lk.ijse.back_end_prerental.dto.ResponseDTO;
import lk.ijse.back_end_prerental.service.custom.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.back_end_prerental.util.VarList.OK;

/**
 * Author: vishmee
 * Date: 4/2/25
 * Time: 1:07 AM
 * Description:
 */
@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin
public class PaymentController {
    @Autowired
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(value = "/getPaymentsByMember")
    public ResponseEntity<ResponseDTO> getMemberBookings(@RequestBody MemberDTO memberDTO) {
        System.out.println("Payment List fetch..........................................");
        List<PaymentDTOTm> paymentDTOTmList = paymentService.getPaymentDTOByMemberEmail(memberDTO.getEmail());
        for (int i = 0; i < paymentDTOTmList.size(); i++) {
            System.out.println(paymentDTOTmList.get(i).getCustomerEmail());
            System.out.println(paymentDTOTmList.get(i).getBookingId());
            System.out.println(paymentDTOTmList.get(i).getPaymentMethod());
        }
        System.out.println(paymentDTOTmList.size());
        return ResponseEntity.ok(
                new ResponseDTO(OK, "Payment List",paymentDTOTmList));
    }
    @GetMapping(value = "/getPayments")
    public ResponseEntity<ResponseDTO> getAll() {
        System.out.println("Payment List fetch..........................................");
        List<PaymentDTOTm> paymentDTOTmList = paymentService.getAllPayment();
        for (int i = 0; i < paymentDTOTmList.size(); i++) {
            System.out.println(paymentDTOTmList.get(i).getCustomerEmail());
            System.out.println(paymentDTOTmList.get(i).getBookingId());
            System.out.println(paymentDTOTmList.get(i).getPaymentMethod());
        }
        System.out.println(paymentDTOTmList.size());
        return ResponseEntity.ok(
                new ResponseDTO(OK, "Payment List",paymentDTOTmList));
    }
    @PostMapping(value = "/getPaymentsByCustomer")
    public ResponseEntity<ResponseDTO> getMemberBookings(@RequestBody PaymentDTO paymentDTO) {
        System.out.println("Payment List fetch..........................................");
        List<PaymentDTOTm> paymentDTOTmList = paymentService.getPaymentDTOByCustomerEmail(paymentDTO.getCustomerEmail());
        for (int i = 0; i < paymentDTOTmList.size(); i++) {
            System.out.println(paymentDTOTmList.get(i).getCustomerEmail());
            System.out.println(paymentDTOTmList.get(i).getBookingId());
            System.out.println(paymentDTOTmList.get(i).getPaymentMethod());
        }
        System.out.println(paymentDTOTmList.size());
        return ResponseEntity.ok(
                new ResponseDTO(OK, "Payment List",paymentDTOTmList));
    }
    }
