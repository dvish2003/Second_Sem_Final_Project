package lk.ijse.back_end_prerental.controller;

import lk.ijse.back_end_prerental.dto.BookingDTO;
import lk.ijse.back_end_prerental.dto.MemBookingDTO;
import lk.ijse.back_end_prerental.dto.MemberDTO;
import lk.ijse.back_end_prerental.dto.ResponseDTO;
import lk.ijse.back_end_prerental.repo.BookingRepository;
import lk.ijse.back_end_prerental.service.custom.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
}
