package lk.ijse.back_end_prerental.controller;

import lk.ijse.back_end_prerental.dto.*;
import lk.ijse.back_end_prerental.service.custom.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(value = "/transactionalSave")
    public ResponseEntity<ResponseDTO> shareAllDetails(@RequestBody FBTransactionalDTO fbTransactionalDTO) {
        System.out.println("Share all details");
        String userEmail = fbTransactionalDTO.getUserEmail();
        Date pickUpDate = fbTransactionalDTO.getStartDate();
        Date returnDate = fbTransactionalDTO.getEndDate();
        double total = fbTransactionalDTO.getTotalAmount();
        String paymentMethod = "PayHere";
        String currency = "$";
        Date localDate = Date.valueOf(LocalDate.now());
        double ServiceFee = fbTransactionalDTO.getServiceFee();
        double depositCharge = fbTransactionalDTO.getDepositCharge();


        TransactionDTO transactionDTO = new TransactionDTO();
        PaymentDTO paymentDTO = new PaymentDTO();
        AdminPaymentDTO adminPaymentDTO = new AdminPaymentDTO();
        BookingDTO bookingDTO = new BookingDTO();
        VehicleDTO vehicleDTO = fbTransactionalDTO.getVehicleDTO();
        UserDTO userDTO = userService.searchUser(userEmail);

        paymentDTO.setAmount(total);
        paymentDTO.setCurrency(currency);
        paymentDTO.setPaymentMethod(paymentMethod);
        paymentDTO.setPaymentDate(localDate);
        paymentDTO.setDepositAmount(depositCharge);
        paymentDTO.setServiceCharge(ServiceFee);
        paymentDTO.setBooking(bookingDTO);
        paymentDTO.setAdminPayment(adminPaymentDTO);


        transactionDTO.setVehicleDTO(vehicleDTO);
        transactionDTO.setBookingDTO(bookingDTO);
        transactionDTO.setUserDTO(userDTO);
        transactionDTO.setPaymentDTO(paymentDTO);
        transactionDTO.setAdminPaymentDTO(adminPaymentDTO);

        adminPaymentDTO.setPaymentDate(localDate);
        adminPaymentDTO.setPayment(paymentDTO);
        adminPaymentDTO.setAmount(ServiceFee);

        bookingDTO.setPickupDate(pickUpDate);
        bookingDTO.setReturnDate(returnDate);

        if(localDate.equals(bookingDTO.getPickupDate())){
            bookingDTO.setStatus(true);
        }
        else{
            bookingDTO.setStatus(false);
        }
        bookingDTO.setTotalAmount(total);
        bookingDTO.setPayment(paymentDTO);
        bookingDTO.setCustomer(userDTO);
        bookingDTO.setVehicle(vehicleDTO);


        //vehicle detail use vehicleDTO
        //save to transactional table
        //update vehicle status
        //return success message
        //else return error message
        return null;
    }

    }
