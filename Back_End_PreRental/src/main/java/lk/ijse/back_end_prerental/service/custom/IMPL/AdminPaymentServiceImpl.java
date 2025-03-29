package lk.ijse.back_end_prerental.service.custom.IMPL;

import lk.ijse.back_end_prerental.Entity.AdminPayment;
import lk.ijse.back_end_prerental.dto.AdminPaymentDTO;
import lk.ijse.back_end_prerental.repo.AdminPaymentRepository;
import lk.ijse.back_end_prerental.service.custom.AdminPaymentService;
import lk.ijse.back_end_prerental.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: vishmee
 * Date: 3/29/25
 * Time: 3:27 PM
 * Description:
 */
@Service
@Transactional
public class AdminPaymentServiceImpl implements AdminPaymentService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AdminPaymentRepository adminPaymentRepository;

    @Override
    public int savePayment(AdminPaymentDTO adminPaymentDTO){
        //save payment
        try {
            adminPaymentRepository.save(modelMapper.map(adminPaymentDTO, AdminPayment.class));
            return VarList.Created;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public List<AdminPaymentDTO> getPaymentDTO(){
        List<AdminPayment> paymentList = adminPaymentRepository.findAll();
        return modelMapper.map(paymentList, new TypeToken<List<AdminPaymentDTO>>() {}.getType());
    }
}
