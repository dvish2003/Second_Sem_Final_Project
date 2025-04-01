package lk.ijse.back_end_prerental.controller;

import lk.ijse.back_end_prerental.dto.BookingDTO;
import lk.ijse.back_end_prerental.dto.MemBookingDTO;
import lk.ijse.back_end_prerental.dto.MemberDTO;
import lk.ijse.back_end_prerental.dto.ResponseDTO;
import lk.ijse.back_end_prerental.repo.BookingRepository;
import lk.ijse.back_end_prerental.service.custom.BookingService;
import lk.ijse.back_end_prerental.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static lk.ijse.back_end_prerental.util.VarList.OK;

/**
 * Author: vishmee
 * Date: 3/31/25
 * Time: 7:36 PM
 * Description:
 */

@RestController
@RequestMapping("/api/v1/booking")
@CrossOrigin
public class BookingController {
    private  final BookingRepository bookingRepository;
    private  final BookingService bookingService;

    public BookingController(BookingRepository bookingRepository, BookingService bookingService) {
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
    }


    @PostMapping(value = "/getMemberBookings")
    public ResponseEntity<ResponseDTO> getMemberBookings(@RequestBody MemberDTO memberDTO) {
        System.out.println("Request Accepted 111111111111111111ppppppppppppppppppppppppppp");
        List<MemBookingDTO> memBookingDTOList = bookingService.getBookingByMember(memberDTO.getEmail());
        for (int i = 0; i < memBookingDTOList.size(); i++) {
            System.out.println(memBookingDTOList.get(i).getCustomerFileName());
            System.out.println(memBookingDTOList.get(i).getVehicleFileName());
            System.out.println(memBookingDTOList.get(i).getCustomerName());
            System.out.println(memBookingDTOList.get(i).getCustomerContact2());

        }
        System.out.println(memBookingDTOList.size());
        return ResponseEntity.ok(
                new ResponseDTO(OK, "Booking List",memBookingDTOList)
        );

    }

    @PutMapping(value = "/updateBooking")
    public ResponseEntity<ResponseDTO> updateBooking(@RequestBody BookingDTO bookingDTO) {
        BookingDTO bookingDTO1 = new BookingDTO();
        UUID id = bookingDTO.getId();
        String status = bookingDTO.getStatus();
        bookingDTO1.setId(id);
        bookingDTO1.setStatus(status);
try{
    int res = bookingService.updateBooking(bookingDTO1);
    switch (res) {
        case VarList.Created:
            System.out.println("Booking updated successfully");
            return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Booking updated successfully", bookingDTO1));
        case VarList.Not_Found:
            System.out.println("Booking not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(new ResponseDTO(VarList.Not_Found, "Booking not found", null));
        default:
            System.out.println("Error updating booking");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(new ResponseDTO(VarList.Internal_Server_Error, "Error updating booking", null));
    }
} catch (Exception e) {
    throw new RuntimeException(e);
}
    }
}






























