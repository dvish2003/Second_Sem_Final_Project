package lk.ijse.back_end_prerental.controller;

import lk.ijse.back_end_prerental.Entity.Booking;
import lk.ijse.back_end_prerental.Entity.Payment;
import lk.ijse.back_end_prerental.dto.*;
import lk.ijse.back_end_prerental.repo.BookingRepository;
import lk.ijse.back_end_prerental.repo.PaymentRepository;
import lk.ijse.back_end_prerental.service.custom.AdminPaymentService;
import lk.ijse.back_end_prerental.service.custom.BookingService;
import lk.ijse.back_end_prerental.service.custom.PaymentService;
import lk.ijse.back_end_prerental.service.custom.UserService;
import lk.ijse.back_end_prerental.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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
        VehicleDTO vehicleDTO = fbTransactionalDTO.getVehicleDTO1();

        //sout all varibles
        System.out.println("userEmail: " + userEmail);
        System.out.println("pickUpDate: " + pickUpDate);
        System.out.println("returnDate: " + returnDate);
        System.out.println("total: " + total);
        System.out.println("paymentMethod: " + paymentMethod);
        System.out.println("currency: " + currency);
        System.out.println("ServiceFee: " + ServiceFee);
        System.out.println("depositCharge: " + depositCharge);
        System.out.println("-----------------------------");


        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setVehicle(vehicleDTO);
        bookingDTO.setStatus(false);
        bookingDTO.setPickupDate(pickUpDate);
        bookingDTO.setReturnDate(returnDate);
        bookingDTO.setTotalAmount(total);
        bookingDTO.setCustomer(userService.searchUser(userEmail));

        try{
            int res = bookingService.saveBooking(bookingDTO);
            switch (res) {
                case VarList.Created -> {
                    List<BookingDTO> bookingDTOs = bookingService.getBookingDTOUseByDate(pickUpDate);
                    BookingDTO bookingDTO2 = new BookingDTO();
                    for (BookingDTO bookingDTO1 : bookingDTOs) {
                        if(bookingDTO1.getVehicle().getPlateNumber().equals(vehicleDTO.getPlateNumber())){
                            bookingDTO2 = bookingDTO1;
                        }
                    }

                    PaymentDTO paymentDTO = new PaymentDTO();
                    paymentDTO.setAmount(total);
                    paymentDTO.setCurrency(currency);
                    paymentDTO.setPaymentMethod(paymentMethod);
                    paymentDTO.setPaymentDate(localDate);
                    paymentDTO.setDepositAmount(depositCharge);
                    paymentDTO.setServiceCharge(ServiceFee);
                    paymentDTO.setBooking(bookingDTO2);
                    paymentDTO.setMemberEmail(vehicleDTO.getOwner().getEmail());
                    paymentDTO.setCustomerEmail(userEmail);

                    int res2 = paymentService.savePayment(paymentDTO);
                    switch (res2){
                        case VarList.Created -> {
                          Payment payment = paymentRepository.findPaymentByBookingId(bookingDTO2.getId());
                          System.out.println("Payment saved successfully");
                          paymentDTO.setId(payment.getId());
                          System.out.println("iiiiiiiiiiiiiiiii "+paymentDTO.getId());

                            AdminPaymentDTO adminPaymentDTO = new AdminPaymentDTO();
                            adminPaymentDTO.setPaymentDate(localDate);
                            adminPaymentDTO.setPayment(paymentDTO);
                            adminPaymentDTO.setAmount(ServiceFee);

                            int res3 = adminPaymentService.savePayment(adminPaymentDTO);
                            System.out.println("Admin payment saved successfully");


                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*int res = bookingService.saveBooking(bookingDTO);
        */
      /*  List<Booking> bookingList = bookingRepository.findBookingsByPickupDate(pickUpDate);
        for (Booking booking : bookingList){
            System.out.println("++++++++++++++++"+booking.getId());
            System.out.println("++++++++++++++++"+booking.getVehicle().getPlateNumber());
        }
        System.out.println("ddddddddddddddddd "+bookingList);*/



/*
        paymentService.savePayment(paymentDTO);
*/


        AdminPaymentDTO adminPaymentDTO = new AdminPaymentDTO();
    /*    adminPaymentDTO.setPaymentDate(localDate);
        adminPaymentDTO.setPayment(paymentDTO);
        adminPaymentDTO.setAmount(ServiceFee);*/
/*
try{
    int res  = bookingService.saveBooking(bookingDTO);
    switch (res) {
        case VarList.Created -> {
            System.out.println("Booking saved successfully");
            int res2 = paymentService.savePayment(paymentDTO);
            switch (res2) {
               case VarList.Created -> {
                   System.out.println("Payment saved successfully");
                   int res3 = adminPaymentService.savePayment(adminPaymentDTO);
                   switch (res3) {
                       case VarList.Created -> {
                           System.out.println("Admin Payment saved successfully");
                           return ResponseEntity.status(HttpStatus.CREATED)
                                   .body(new ResponseDTO(VarList.Created, "Success", fbTransactionalDTO));
                       }
                       default -> {
                           System.out.println("Error in saving admin payment");
                           return new ResponseEntity<>(new ResponseDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
                       }
                   }
               }
               default -> {
                   System.out.println("Error in saving payment");
                   return new ResponseEntity<>(new ResponseDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
               }
           }
        }
    }
} catch (Exception e) {
    throw new RuntimeException(e);
}*/
        System.out.println("ooooooooooooooooooooooooooooooooooooooooo");


        return null;
    }

    }
