package lk.ijse.back_end_prerental.controller;

import lk.ijse.back_end_prerental.dto.*;
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


    @PostMapping(value = "/getExistsBookingDate")
    public ResponseEntity<ResponseDTO> getExistBookings(@RequestBody VehicleDTO vehicleDTO) {
        System.out.println("vehicle plate numbbbbbbbbbbbbberrrrrrrrr   "+vehicleDTO.getPlateNumber());
        String plateNumber = vehicleDTO.getPlateNumber();
        List<MemBookingDTO> bookingDTOList = bookingService.getAllBooking();
        List<MemBookingDTO> bookingDTOList_2 = new ArrayList<>();
        for(MemBookingDTO memBookingDTO : bookingDTOList){
            MemBookingDTO memBookingDTO1 = new MemBookingDTO();
            memBookingDTO1 = memBookingDTO;
            if(plateNumber.equals(memBookingDTO1.getPlateNumber())){
                bookingDTOList_2.add(memBookingDTO1);
            }
        }
        System.out.println(bookingDTOList_2.size());
        return ResponseEntity.ok(
                new ResponseDTO(OK, "Booking List",bookingDTOList_2)
        );
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

    @GetMapping(value = "/grtAllBookings")
    public ResponseEntity<ResponseDTO> getMemberBookings() {
        System.out.println("Request Accepted 111111111111111111ppppppppppppppppppppppppppp");
        List<MemBookingDTO> memBookingDTOList = bookingService.getAllBooking();
        for (int i = 0; i < memBookingDTOList.size(); i++) {
            System.out.println(memBookingDTOList.get(i).getCustomerFileName());
            System.out.println(memBookingDTOList.get(i).getVehicleFileName());
            System.out.println(memBookingDTOList.get(i).getCustomerName());
            System.out.println("member emaaaaaaaaaaaaaaaaaaaaaaal"+memBookingDTOList.get(i).getMemberEmail());
            System.out.println("memberrrrrrrrrrrrrrrrrr name"+memBookingDTOList.get(i).getMemberName());
            System.out.println(memBookingDTOList.get(i).getCustomerContact2());

        }
        System.out.println(memBookingDTOList.size());
        return ResponseEntity.ok(
                new ResponseDTO(OK, "Booking List",memBookingDTOList)
        );

    }
    @PostMapping(value = "/getCustomerBookings")
    public ResponseEntity<ResponseDTO> getMemberBookings(@RequestBody UserDTO userDTO) {
        System.out.println("Request Acceptedoooooooooooooooooooooooo");
        List<CusBookingDTO> cusBookingDTOS = bookingService.getCusBooking();
        List<CusBookingDTO> cusBookingDTOS1 = new ArrayList<>();
        for (CusBookingDTO cusBookingDTO2 : cusBookingDTOS){
            CusBookingDTO cusBookingDTO = new CusBookingDTO();
            cusBookingDTO = cusBookingDTO2;
            if(cusBookingDTO.getCustomerEmail().equals(userDTO.getEmail())){
                cusBookingDTOS1.add(cusBookingDTO);
            }

        }
        System.out.println(cusBookingDTOS.size());
        return ResponseEntity.ok(
                new ResponseDTO(OK, "Booking List",cusBookingDTOS1)
        );

    }

    @PutMapping(value = "/updateBooking")
    public ResponseEntity<ResponseDTO> updateBooking(@RequestBody BookingDTO bookingDTO) {
        BookingDTO bookingDTO1 = new BookingDTO();
        UUID id = bookingDTO.getId();
        String status = bookingDTO.getStatus();
        bookingDTO1.setId(id);
        bookingDTO1.setStatus(status);
        System.out.println("lllllllllllllllll"+bookingDTO1.getStatus());
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






























