package lk.ijse.back_end_prerental.dto;
import jakarta.validation.constraints.*;
import lk.ijse.back_end_prerental.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: vishmee
 * Date: 3/11/25
 * Time: 9:20 PM
 * Description:
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private int id;
    private String vehicleType;
    private String brand;
    private String model;
    private int year;
    private String plateNumber;
    private String color;
    private String fuelType;
    private Integer engineCapacity;
    private String transmission;
    private int seatingCapacity;
    private Integer mileage;
    private Double fuelEfficiency;
    private double dailyRate;
    private Double weeklyRate;
    private Double monthlyRate;
    private int minRentalDays;
    private Double depositAmount;
    private boolean availableNow;
    private String description;
    private String rentalTerms;
    private List<String> features = new ArrayList<>();
    private Date createdAt;
    private String image;
    private Member owner;
    private List<BookingDTO> bookings;
    private String mainImageUrl;
    private List<String> imageUrls;
    private double averageRating;
    private int reviewCount;
}
