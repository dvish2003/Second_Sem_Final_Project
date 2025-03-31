package lk.ijse.back_end_prerental.service.custom.IMPL;

import lk.ijse.back_end_prerental.Entity.Booking;
import lk.ijse.back_end_prerental.dto.BookingDTO;
import lk.ijse.back_end_prerental.dto.MemBookingDTO;
import lk.ijse.back_end_prerental.repo.BookingRepository;
import lk.ijse.back_end_prerental.service.custom.BookingService;
import lk.ijse.back_end_prerental.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: vishmee
 * Date: 3/29/25
 * Time: 3:24 PM
 * Description:
 */
@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BookingRepository bookingRepository;
@Override
public int saveBooking(BookingDTO bookingDTO){
        try{
       bookingRepository.save(modelMapper.map(bookingDTO, Booking.class));
       return VarList.Created;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BookingDTO> getBookings(){
        List<Booking> bookingList = bookingRepository.findAll();
        return modelMapper.map(bookingList, new TypeToken<List<BookingDTO>>() {}.getType());

    }


    @Override
    public List<MemBookingDTO> getBookingByMember(String memberEmail){
        List<Booking> bookingList = bookingRepository.findByMemberEmail(memberEmail);
        List<MemBookingDTO> memBookingDTOList = new ArrayList<>();
        for (Booking bookingDTO : bookingList) {
            MemBookingDTO memBookingDTO = new MemBookingDTO();
            memBookingDTO.setId(bookingDTO.getId());
            memBookingDTO.setPickupDate(bookingDTO.getPickupDate());
            memBookingDTO.setReturnDate(bookingDTO.getReturnDate());
            memBookingDTO.setStatus(bookingDTO.isStatus());
            memBookingDTO.setPaymentAmount(bookingDTO.getPayment().getAmount());
            memBookingDTO.setCustomerEmail(bookingDTO.getCustomer().getEmail());
            memBookingDTO.setCustomerFileName(bookingDTO.getCustomer().getFileName());
            memBookingDTO.setCustomerName(bookingDTO.getCustomer().getName());
            memBookingDTO.setCustomerContact(bookingDTO.getCustomer().getPrimary_phone_number());
            memBookingDTO.setCustomerContact2(bookingDTO.getCustomer().getSecondary_phone_number());
            memBookingDTO.setMemberEmail(bookingDTO.getMemberEmail());
            memBookingDTO.setPlateNumber(bookingDTO.getVehicle().getPlateNumber());
            memBookingDTO.setVehicleFileName(bookingDTO.getVehicle().getFileName());
            memBookingDTOList.add(memBookingDTO);

        }
        return memBookingDTOList;

    }

    @Override
    public List<BookingDTO> getBookingDTOUseByDate(Date pickUpDate) {
        List<Booking> bookingList = bookingRepository.findBookingsByPickupDate(pickUpDate);
        return modelMapper.map(bookingList, new TypeToken<List<BookingDTO>>() {}.getType());
    }
}
