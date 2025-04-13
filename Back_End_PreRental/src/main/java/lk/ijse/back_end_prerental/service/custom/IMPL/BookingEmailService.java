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

            String subject = "PreRental Booking Confirmation #" + bookingID.toString().substring(0, 8).toUpperCase();

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

/*@Service
@Transactional
public class BookingEmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmails(TransactionDTO transactionDTO) {
        try {
            // Extract all necessary data from DTO
            String customerEmail = transactionDTO.getBookingDTO().getCustomer().getEmail();
            String customerName = transactionDTO.getBookingDTO().getCustomer().getName();
            String memberName = transactionDTO.getVehicleDTO().getOwner().getName();
            String memberEmail = transactionDTO.getVehicleDTO().getOwner().getEmail();

            // Send receipt to customer
            sendCustomerReceipt(transactionDTO, customerEmail, customerName);

            // Send notification to vehicle owner
            sendOwnerNotification(transactionDTO, memberEmail, memberName, customerName);

        } catch (Exception e) {
            System.err.println("Failed to send emails: " + e.getMessage());
            throw new RuntimeException("Failed to send emails", e);
        }
    }

    private void sendCustomerReceipt(TransactionDTO transactionDTO, String toEmail, String customerName) {
        String vehicleBrand = transactionDTO.getVehicleDTO().getBrand();
        String vehicleModel = transactionDTO.getVehicleDTO().getModel();
        String vehiclePlateNumber = transactionDTO.getVehicleDTO().getPlateNumber();
        String memberName = transactionDTO.getVehicleDTO().getOwner().getName();
        String memberEmail = transactionDTO.getVehicleDTO().getOwner().getEmail();
        String memberContact = transactionDTO.getVehicleDTO().getOwner().getContact();
        UUID bookingID = transactionDTO.getBookingDTO().getId();
        UUID paymentID = transactionDTO.getPaymentDTO().getId();
        double paymentAmount = transactionDTO.getPaymentDTO().getAmount();
        Date pickUpDate = transactionDTO.getBookingDTO().getPickupDate();
        Date returnDate = transactionDTO.getBookingDTO().getReturnDate();

        System.out.println("Sending professional booking receipt to customer: " + toEmail);

        String subject = "PreRental Booking Confirmation #" + bookingID.toString().substring(0, 8).toUpperCase();

        String text = buildCustomerReceiptText(
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

        sendEmail(toEmail, subject, text);
        System.out.println("Professional receipt email sent successfully to customer: " + toEmail);
    }

    private void sendOwnerNotification(TransactionDTO transactionDTO, String toEmail, String memberName, String customerName) {
        String vehicleBrand = transactionDTO.getVehicleDTO().getBrand();
        String vehicleModel = transactionDTO.getVehicleDTO().getModel();
        String vehiclePlateNumber = transactionDTO.getVehicleDTO().getPlateNumber();
        UUID bookingID = transactionDTO.getBookingDTO().getId();
        Date pickUpDate = transactionDTO.getBookingDTO().getPickupDate();
        Date returnDate = transactionDTO.getBookingDTO().getReturnDate();

        System.out.println("Sending booking notification to vehicle owner: " + toEmail);

        String subject = "New Booking for Your Vehicle #" + bookingID.toString().substring(0, 8).toUpperCase();

        String text = buildOwnerNotificationText(
                memberName,
                customerName,
                bookingID,
                vehicleBrand,
                vehicleModel,
                vehiclePlateNumber,
                pickUpDate,
                returnDate
        );

        sendEmail(toEmail, subject, text);
        System.out.println("Booking notification email sent successfully to owner: " + toEmail);
    }

    private void sendEmail(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom("prerental2003@gmail.com");
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    private String buildCustomerReceiptText(
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

    private String buildOwnerNotificationText(
            String memberName,
            String customerName,
            UUID bookingID,
            String vehicleBrand,
            String vehicleModel,
            String vehiclePlateNumber,
            Date pickUpDate,
            Date returnDate
    ) {
        return String.format(
                "NEW BOOKING NOTIFICATION\n" +
                "========================\n\n" +
                "Dear %s,\n\n" +
                "We're pleased to inform you that your vehicle has been booked through PreRental.\n" +
                "Please find the booking details below:\n\n" +
                "BOOKING DETAILS\n" +
                "--------------\n" +
                "Booking ID:      %s\n" +
                "Customer Name:   %s\n\n" +
                "VEHICLE INFORMATION\n" +
                "-------------------\n" +
                "Brand:           %s\n" +
                "Model:           %s\n" +
                "Plate Number:    %s\n\n" +
                "RENTAL PERIOD\n" +
                "------------\n" +
                "Pickup Date:     %s\n" +
                "Return Date:     %s\n\n" +
                "NEXT STEPS\n" +
                "----------\n" +
                "1. Please prepare the vehicle for pickup on the scheduled date.\n" +
                "2. Verify the customer's ID and booking confirmation upon vehicle handover.\n" +
                "3. Complete the vehicle inspection checklist with the customer.\n\n" +
                "IMPORTANT REMINDERS\n" +
                "------------------\n" +
                "1. Ensure the vehicle is clean and in good working condition.\n" +
                "2. Document any pre-existing damages with the customer.\n" +
                "3. Provide clear instructions about the vehicle's features and requirements.\n\n" +
                "For any questions or support, please contact us at:\n" +
                "Email: owner-support@prerental.com\n" +
                "Phone: +1 (555) 987-6543\n\n" +
                "Thank you for being part of PreRental!\n\n" +
                "Best regards,\n" +
                "The PreRental Team\n" +
                "www.prerental.com",
                memberName,
                bookingID,
                customerName,
                vehicleBrand,
                vehicleModel,
                vehiclePlateNumber,
                pickUpDate,
                returnDate
        );
    }
}*/