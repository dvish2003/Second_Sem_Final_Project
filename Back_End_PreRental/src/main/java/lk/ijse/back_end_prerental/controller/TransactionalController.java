package lk.ijse.back_end_prerental.controller;

import lk.ijse.back_end_prerental.dto.*;
import lk.ijse.back_end_prerental.repo.BookingRepository;
import lk.ijse.back_end_prerental.repo.PaymentRepository;
import lk.ijse.back_end_prerental.service.custom.*;
import lk.ijse.back_end_prerental.util.JwtUtil;
import lk.ijse.back_end_prerental.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Author: vishmee
 * Date: 3/29/25
 * Time: 2:51 AM
 * Description:
 */
@RestController
@RequestMapping("api/v1/transaction")
@CrossOrigin
public class TransactionalController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private AdminPaymentService adminPaymentService;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/transactionalSave")
    public ResponseEntity<ResponseDTO> shareAllDetails(@RequestBody FBTransactionalDTO fbTransactionalDTO) {
        System.out.println("Share all details");
        String userEmail = fbTransactionalDTO.getUserEmail();
        Date pickUpDate = fbTransactionalDTO.getStartDate();
        Date returnDate = fbTransactionalDTO.getEndDate();
        double total = fbTransactionalDTO.getTotalAmount();
        String cardName = fbTransactionalDTO.getCardName();
        String expiryDate = fbTransactionalDTO.getExpiryDate();
        String cvv = fbTransactionalDTO.getCvv();
        String cardNumber = fbTransactionalDTO.getCardNumber();
        String paymentMethod = "Bank Card";
        String currency = "$";
        Date localDate = Date.valueOf(LocalDate.now());
        double ServiceFee = fbTransactionalDTO.getServiceFee();
        double depositCharge = fbTransactionalDTO.getDepositCharge();
        VehicleDTO vehicleDTO = fbTransactionalDTO.getVehicleDTO1();

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setVehicle(vehicleDTO);
        bookingDTO.setStatus(null);
        bookingDTO.setPickupDate(pickUpDate);
        bookingDTO.setReturnDate(returnDate);
        bookingDTO.setTotalAmount(total);
        bookingDTO.setMemberEmail(vehicleDTO.getOwner().getEmail());
        bookingDTO.setCustomer(userService.searchUser(userEmail));
        bookingDTO.setLocalDate(localDate);
        /*================================================================================*/

     PaymentDTO2 paymentDTO2 = new PaymentDTO2();
     paymentDTO2.setCardNumber(cardNumber);
     paymentDTO2.setCvv(cvv);
     paymentDTO2.setExpiryDate(expiryDate);
     paymentDTO2.setCardName(cardName);
     paymentDTO2.setUserEmail(userEmail);

     String token = jwtUtil.generateTokenPay(paymentDTO2);

       PaySuDTO paySu = new PaySuDTO();
       paySu.setToken(token);
       paySu.setCvv(cvv);
       paySu.setExpiryDate(expiryDate);
       paySu.setCardName(cardName);
       paySu.setUserEmail(userEmail);
     /*============================================================================*/
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(total);
        paymentDTO.setCurrency(currency);
        paymentDTO.setPaymentMethod(paymentMethod);
        paymentDTO.setPaymentDate(localDate);
        paymentDTO.setDepositAmount(depositCharge);
        paymentDTO.setServiceCharge(ServiceFee);
        paymentDTO.setMemberEmail(vehicleDTO.getOwner().getEmail());
        paymentDTO.setCustomerEmail(userEmail);



        /*================================================================================*/
        AdminPaymentDTO adminPaymentDTO = new AdminPaymentDTO();
        adminPaymentDTO.setPaymentDate(localDate);
        adminPaymentDTO.setAmount(ServiceFee);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setBookingDTO(bookingDTO);
        transactionDTO.setPaymentDTO(paymentDTO);
        transactionDTO.setAdminPaymentDTO(adminPaymentDTO);
        transactionDTO.setVehicleDTO(vehicleDTO);
        transactionDTO.setPaySuDTO(paySu);

        try {
            int res = transactionService.saveTransaction(transactionDTO);
            switch (res) {
                case VarList.Created:
                    System.out.println("Transaction  saved successfully .....................");
                    return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Transaction saved successfully", fbTransactionalDTO));
                default:
                    System.out.println("Error saving Transaction");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseDTO(VarList.Internal_Server_Error, "Error saving Transaction", null));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    }
