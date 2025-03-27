package lk.ijse.back_end_prerental.service.custom;

import lk.ijse.back_end_prerental.dto.VehicleDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Author: vishmee
 * Date: 3/25/25
 * Time: 1:07 AM
 * Description:
 */

public interface VehicleService {
    //get all Vehicle
    List<VehicleDTO> getAllVehicle();

    VehicleDTO getVehicle(String plateNumber);

    int saveVehicle(VehicleDTO vehicleDTO, MultipartFile multipartFile);

}
