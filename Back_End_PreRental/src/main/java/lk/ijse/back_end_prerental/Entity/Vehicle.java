package lk.ijse.back_end_prerental.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String vehicleType;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(name = "plate_number", nullable = false, unique = true)
    private String plateNumber;

    @Column(nullable = false)
    private String color;

    @Column(name = "fuel_type", nullable = false)
    private String fuelType;

    @Column(name = "engine_capacity")
    private Integer engineCapacity; // in cc

    @Column(nullable = false)
    private String transmission;

    @Column(name = "seating_capacity", nullable = false)
    private int seatingCapacity;

    private Integer mileage; // in km

    @Column(name = "fuel_efficiency")
    private Double fuelEfficiency; // km/l

    @Column(name = "daily_rate", nullable = false)
    private double dailyRate;

    @Column(name = "weekly_rate")
    private Double weeklyRate;

    @Column(name = "monthly_rate")
    private Double monthlyRate;

    @Column(name = "min_rental_days", nullable = false)
    private int minRentalDays = 1;

    @Column(name = "deposit_amount")
    private Double depositAmount;

    @Column(name = "available_now")
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


    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "rental_terms", columnDefinition = "TEXT")
    private String rentalTerms;

    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    private String fileName;
    private String filetype;
    @Lob
    private byte[] data;


    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member owner;
    private String city;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Booking> bookings;


}
