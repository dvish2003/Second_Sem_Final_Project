package lk.ijse.back_end_prerental.service.custom.IMPL;

import lk.ijse.back_end_prerental.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.UUID;

/**
 * Author: vishmee
 * Date: 4/8/25
 * Time: 9:03 PM
 * Description:
 */

@Service
@Transactional
public class BookingEmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(TransactionDTO transactionDTO) {
        try {
            // Extract all necessary data from DTO
            String toEmail = transactionDTO.getBookingDTO().getCustomer().getEmail();
            String vehicleBrand = transactionDTO.getVehicleDTO().getBrand();
            String vehicleModel = transactionDTO.getVehicleDTO().getModel();
            String vehiclePlateNumber = transactionDTO.getVehicleDTO().getPlateNumber();
            String customerName = transactionDTO.getBookingDTO().getCustomer().getName();
            String memberName = transactionDTO.getVehicleDTO().getOwner().getName();
            String memberEmail = transactionDTO.getVehicleDTO().getOwner().getEmail();
            String memberContact = transactionDTO.getVehicleDTO().getOwner().getContact();
            UUID bookingID = transactionDTO.getBookingDTO().getId();
            UUID paymentID = transactionDTO.getPaymentDTO().getId();
            double paymentAmount = transactionDTO.getPaymentDTO().getAmount();
            Date pickUpDate = transactionDTO.getBookingDTO().getPickupDate();
            Date returnDate = transactionDTO.getBookingDTO().getReturnDate();

            System.out.println("Sending professional booking receipt to: " + toEmail);

            // Create email subject
            String subject = "PreRental Booking Confirmation #" + bookingID.toString().substring(0, 8).toUpperCase();

            // Create professional receipt text
            String text = buildReceiptText(
                    customerName,
                    bookingID,
                    paymentID,
                    paymentAmount,
                    vehicleBrand,
                    vehicleModel,
                    vehiclePlateNumber,
                    pickUpDate,
                    returnDate,
                    memberName,
                    memberEmail,
                    memberContact
            );

            // Create and send email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setFrom("prerental2003@gmail.com");
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);

            System.out.println("Professional receipt email sent successfully to: " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send professional receipt email: " + e.getMessage());
            throw new RuntimeException("Failed to send professional receipt email", e);
        }
    }

    private String buildReceiptText(
            String customerName,
            UUID bookingID,
            UUID paymentID,
            double paymentAmount,
            String vehicleBrand,
            String vehicleModel,
            String vehiclePlateNumber,
            Date pickUpDate,
            Date returnDate,
            String memberName,
            String memberEmail,
            String memberContact
    ) {
        return String.format(
                "PRE-RENTAL BOOKING RECEIPT\n" +
                        "==========================\n\n" +
                        "Dear %s,\n\n" +
                        "Thank you for choosing PreRental. Your booking has been confirmed.\n" +
                        "Please find your booking details below:\n\n" +
                        "TRANSACTION DETAILS\n" +
                        "-------------------\n" +
                        "Booking ID:      %s\n" +
                        "Payment ID:      %s\n" +
                        "Payment Amount:  $%.2f\n\n" +
                        "VEHICLE INFORMATION\n" +
                        "-------------------\n" +
                        "Brand:           %s\n" +
                        "Model:           %s\n" +
                        "Plate Number:    %s\n\n" +
                        "RENTAL PERIOD\n" +
                        "------------\n" +
                        "Pickup Date:     %s\n" +
                        "Return Date:     %s\n\n" +
                        "OWNER CONTACT\n" +
                        "-------------\n" +
                        "Name:           %s\n" +
                        "Email:          %s\n" +
                        "Phone:          %s\n\n" +
                        "IMPORTANT NOTES\n" +
                        "--------------\n" +
                        "1. Please bring your ID and this receipt when picking up the vehicle.\n" +
                        "2. The vehicle must be returned with the same fuel level as at pickup.\n" +
                        "3. Any damages must be reported immediately.\n\n" +
                        "For any questions or changes to your booking, please contact us at:\n" +
                        "Email: support@prerental.com\n" +
                        "Phone: +1 (555) 123-4567\n\n" +
                        "We appreciate your business!\n\n" +
                        "Best regards,\n" +
                        "The PreRental Team\n" +
                        "www.prerental.com",
                customerName,
                bookingID,
                paymentID,
                paymentAmount,
                vehicleBrand,
                vehicleModel,
                vehiclePlateNumber,
                pickUpDate,
                returnDate,
                memberName,
                memberEmail,
                memberContact
        );
    }
}