package lk.ijse.back_end_prerental.service.custom.IMPL;

import lk.ijse.back_end_prerental.Entity.Member;
import lk.ijse.back_end_prerental.dto.MemberDTO;
import lk.ijse.back_end_prerental.repo.MemberRepository;
import lk.ijse.back_end_prerental.repo.UserRepository;
import lk.ijse.back_end_prerental.service.custom.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: vishmee
 * Date: 3/16/25
 * Time: 8:27 PM
 * Description:
 */
@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;


    @Override
    public MemberDTO getMember(String email){
        if(memberRepository.existsByEmail(email)){
            Member member = memberRepository.findByEmail(email);
            return modelMapper.map(member, MemberDTO.class);
            } else {
            return null;
        }

   }

}
