package lk.ijse.back_end_prerental.controller;

import lk.ijse.back_end_prerental.Entity.Vehicle;
import lk.ijse.back_end_prerental.dto.MemberDTO;
import lk.ijse.back_end_prerental.dto.ResponseDTO;
import lk.ijse.back_end_prerental.dto.VehicleDTO;
import lk.ijse.back_end_prerental.dto.VehicleSpDTO;
import lk.ijse.back_end_prerental.repo.VehicleRepository;
import lk.ijse.back_end_prerental.service.custom.MemberService;
import lk.ijse.back_end_prerental.service.custom.VehicleService;
import lk.ijse.back_end_prerental.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Author: vishmee
 * Date: 3/25/25
 * Time: 1:11 AM
 * Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping(value = "/getAllVehicle")
    public ResponseEntity<ResponseDTO> getAllVehicle() {
        System.out.println("Request Accepted");
        try {
            List<VehicleDTO> vehicleDTOList = vehicleService.getAllVehicle();
            return new ResponseEntity<>(new ResponseDTO(VarList.OK, "Vehicle List", vehicleDTOList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(VarList.Internal_Server_Error, "Failed to get Vehicle List", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping(value = "/getVehicle")
    public ResponseEntity<ResponseDTO> getVehicle(@RequestBody VehicleSpDTO vehicleSpDTO) {
        System.out.println(vehicleSpDTO.getPlate_number());
try{
    VehicleDTO vehicleDTO = vehicleService.getVehicle(vehicleSpDTO.getPlate_number());
    return new ResponseEntity<>(new ResponseDTO(VarList.OK, "Vehicle", vehicleDTO), HttpStatus.OK);
} catch (Exception e) {
    throw new RuntimeException(e);
}
    }

    @PostMapping(value = "/saveVehicle", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> saveVehicle(
            @RequestPart("vehicleDTO") VehicleDTO vehicleDTO,
            @RequestPart("file") MultipartFile file) {

        System.out.println("sssssssssssssssssssssssssssss");
        System.out.println("sssssssssssssssssssssssssssss"+vehicleDTO);
        System.out.println("sssssssssssssssssssssssssssss"+vehicleDTO.getOwner().getEmail());

        MemberDTO memberDTO = memberService.getMember(vehicleDTO.getOwner().getEmail());
        System.out.println(memberDTO);
        System.out.println(memberDTO.getUserDTO());
        System.out.println(memberDTO.getName());
        System.out.println(memberDTO.getNicNumber());
        System.out.println(memberDTO.getJoinDate());
        vehicleDTO.setOwner(memberDTO);
        try {

            vehicleDTO.setCreatedAt(Date.valueOf(LocalDate.now()));


            if (file != null && !file.isEmpty()) {
                Path uploadDir = Paths.get(System.getProperty("user.dir") + "/uploads/");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                Path filePath = uploadDir.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                vehicleDTO.setImage(fileName);
            }

            int res = vehicleService.saveVehicle(vehicleDTO, file);

            if (res == VarList.Created) {
                return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Vehicle saved successfully", vehicleDTO));
            } else if (res == VarList.Found) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDTO(VarList.Found, "Vehicle already exists", null));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, "Error saving vehicle", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Error: " + e.getMessage(), null));
        }
    }
    @GetMapping(value = "/getVehiclePic")
    public ResponseEntity<byte[]> getVehiclePicture(@RequestBody VehicleSpDTO vehicleSpDTO) {
        Vehicle vehicle = vehicleRepository.findByPlateNumber(vehicleSpDTO.getPlate_number());
        System.out.println("dsfjfdiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiisf  "+vehicle);
try {

    if (vehicle == null) {
        return ResponseEntity.notFound().build();
    }

    if (vehicle.getData() == null) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    MediaType mediaType = MediaType.IMAGE_JPEG;
    if (vehicle.getFiletype() != null) {
        mediaType = MediaType.parseMediaType(vehicle.getFiletype());
    }

    return ResponseEntity.ok()
            .contentType(mediaType)
            .body(vehicle.getData());
} catch (Exception e) {
    return ResponseEntity.internalServerError().build();
}
}

}

