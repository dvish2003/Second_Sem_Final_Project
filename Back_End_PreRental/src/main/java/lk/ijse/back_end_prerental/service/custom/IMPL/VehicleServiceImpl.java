package lk.ijse.back_end_prerental.service.custom.IMPL;

import lk.ijse.back_end_prerental.Entity.Vehicle;
import lk.ijse.back_end_prerental.dto.VehicleDTO;
import lk.ijse.back_end_prerental.repo.VehicleRepository;
import lk.ijse.back_end_prerental.service.custom.VehicleService;
import lk.ijse.back_end_prerental.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: vishmee
 * Date: 3/25/25
 * Time: 1:08 AM
 * Description:
 */
@Service
public class VehicleServiceImpl implements VehicleService {
 @Autowired
 private VehicleRepository vehicleRepository;

 @Autowired
 private ModelMapper modelMapper;


 //get all Vehicle
 @Override
 public List<VehicleDTO> getAllVehicle(){
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return modelMapper.map(vehicles,new TypeToken<ArrayList<VehicleDTO>>(){
        }.getType());
    }
 @Override
 public VehicleDTO getVehicle(String plateNumber){
       try {
           if(vehicleRepository.existsByPlateNumber(plateNumber)){
               Vehicle vehicle = vehicleRepository.findByPlateNumber(plateNumber);
               return modelMapper.map(vehicle,VehicleDTO.class);
           }
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
     return null;
 }


    @Override
    public int saveVehicle(VehicleDTO vehicleDTO, MultipartFile file) {
        try{
            if(vehicleRepository.existsByPlateNumber(vehicleDTO.getPlateNumber())){
                return VarList.Found;
            }else{

                Vehicle vehicle = new Vehicle();
                //use model mapper
                vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
                if (file != null && !file.isEmpty()) {
                    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                    vehicle.setFileName(fileName);
                    vehicle.setFiletype(file.getContentType());
                    vehicle.setData(file.getBytes());
                }
                vehicleRepository.save(vehicle);
                return VarList.Created;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
