package lk.ijse.back_end_prerental.dto;
import jakarta.persistence.Lob;
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

    private boolean airCondition;
    private boolean bluetooth;
    private boolean Navigation;
    private boolean sunroof;
    private boolean cruiseControl;
    private boolean backCamera;
    private boolean heatedSeat;
    private boolean childSeat;
    private boolean tollPass;
    private boolean gpsTracker;
    private boolean wifiHotspot;


    private String description;
    private String rentalTerms;
    private Date createdAt;
    private String fileName;
    private String filetype;
    private byte[] data;
    private boolean active;
    private String Image;
    private MemberDTO owner;
    private String city;

}
