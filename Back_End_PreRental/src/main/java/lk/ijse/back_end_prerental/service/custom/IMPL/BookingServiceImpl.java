package lk.ijse.back_end_prerental.service.custom.IMPL;

import lk.ijse.back_end_prerental.Entity.Booking;
import lk.ijse.back_end_prerental.dto.BookingDTO;
import lk.ijse.back_end_prerental.dto.CusBookingDTO;
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
import java.time.LocalDate;
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
        Date localDate = Date.valueOf(LocalDate.now());

        List<Booking> bookingList = bookingRepository.findByMemberEmail(memberEmail);
        List<MemBookingDTO> memBookingDTOList = new ArrayList<>();
        for (Booking bookingDTO : bookingList) {
            MemBookingDTO memBookingDTO = new MemBookingDTO();
            memBookingDTO.setId(bookingDTO.getId());
            memBookingDTO.setPickupDate(bookingDTO.getPickupDate());
            memBookingDTO.setReturnDate(bookingDTO.getReturnDate());
            memBookingDTO.setStatus(bookingDTO.getStatus());
            memBookingDTO.setPaymentAmount(bookingDTO.getPayment().getAmount());
            memBookingDTO.setCustomerEmail(bookingDTO.getCustomer().getEmail());
            memBookingDTO.setCustomerFileName(bookingDTO.getCustomer().getFileName());
            memBookingDTO.setCustomerName(bookingDTO.getCustomer().getName());
            memBookingDTO.setCustomerContact(bookingDTO.getCustomer().getPrimary_phone_number());
            memBookingDTO.setCustomerContact2(bookingDTO.getCustomer().getSecondary_phone_number());
            memBookingDTO.setMemberEmail(bookingDTO.getMemberEmail());
            memBookingDTO.setPlateNumber(bookingDTO.getVehicle().getPlateNumber());
            memBookingDTO.setVehicleFileName(bookingDTO.getVehicle().getFileName());
            memBookingDTO.setLocalDate(localDate);
            memBookingDTOList.add(memBookingDTO);

        }
        return memBookingDTOList;

    }


    @Override
    public List<CusBookingDTO> getCusBooking(){
        Date localDate = Date.valueOf(LocalDate.now());

        List<Booking> bookingList = bookingRepository.findAll();
        List<CusBookingDTO> cusBookingDTOS = new ArrayList<>();
        for (Booking bookingDTO : bookingList) {
            CusBookingDTO cusBookingDTO = new CusBookingDTO();
            cusBookingDTO.setId(bookingDTO.getId());
            cusBookingDTO.setPickupDate(bookingDTO.getPickupDate());
            cusBookingDTO.setReturnDate(bookingDTO.getReturnDate());
            cusBookingDTO.setStatus(bookingDTO.getStatus());
            cusBookingDTO.setPaymentAmount(bookingDTO.getPayment().getAmount());
            cusBookingDTO.setCustomerEmail(bookingDTO.getCustomer().getEmail());
            cusBookingDTO.setCustomerFileName(bookingDTO.getCustomer().getFileName());
            cusBookingDTO.setCustomerName(bookingDTO.getCustomer().getName());
            cusBookingDTO.setCustomerContact(bookingDTO.getCustomer().getPrimary_phone_number());
            cusBookingDTO.setCustomerContact2(bookingDTO.getCustomer().getSecondary_phone_number());
            cusBookingDTO.setMemberEmail(bookingDTO.getMemberEmail());
            cusBookingDTO.setPlateNumber(bookingDTO.getVehicle().getPlateNumber());
            cusBookingDTO.setVehicleFileName(bookingDTO.getVehicle().getFileName());
            cusBookingDTO.setMemberContact(bookingDTO.getVehicle().getOwner().getContact());
            cusBookingDTO.setLocalDate(localDate);
            cusBookingDTOS.add(cusBookingDTO);

        }
        return cusBookingDTOS;

    }

    @Override
    public List<BookingDTO> getBookingDTOUseByDate(Date pickUpDate) {
        List<Booking> bookingList = bookingRepository.findBookingsByPickupDate(pickUpDate);
        return modelMapper.map(bookingList, new TypeToken<List<BookingDTO>>() {}.getType());
    }

    @Override
    public int updateBooking(BookingDTO bookingDTO) {
        try {
            if(bookingRepository.existsById(bookingDTO.getId())){
                Booking booking = bookingRepository.findById(bookingDTO.getId()).get();
                booking.setStatus(bookingDTO.getStatus());
                bookingRepository.save(booking);
                return VarList.Created;
            }
            else {
                return VarList.Not_Found;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
