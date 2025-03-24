package lk.ijse.back_end_prerental.service.custom.IMPL;

import lk.ijse.back_end_prerental.Entity.Member;
import lk.ijse.back_end_prerental.config.VerificationCodeGenerator;
import lk.ijse.back_end_prerental.dto.MemberDTO;
import lk.ijse.back_end_prerental.dto.VerifyMemberDTO;
import lk.ijse.back_end_prerental.repo.MemberRepository;
import lk.ijse.back_end_prerental.repo.UserRepository;
import lk.ijse.back_end_prerental.service.custom.MemberService;
import lk.ijse.back_end_prerental.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Autowired
    private EmailService emailService;


    @Override
    public MemberDTO getMember(String email) {
        if (memberRepository.existsByEmail(email)) {
            Member member = memberRepository.findByEmail(email);
            return modelMapper.map(member, MemberDTO.class);
        } else {
            return null;
        }
    }
    @Override
    public int saveMember(MemberDTO memberDTO) {
        if(memberRepository.existsByEmail(memberDTO.getEmail())){
            return VarList.Not_Acceptable;
        }
        else {
            memberDTO.setVerificationCode(null);
            memberDTO.setVerified(true);
            Member member = modelMapper.map(memberDTO, Member.class);
            memberRepository.save(member);
            return VarList.Created;
        }
    }
    //get all Member
    @Override
    public List<MemberDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return modelMapper.map(members, new TypeToken<List<MemberDTO>>() {}.getType());

    }

    @Override
    public int deleteMember(VerifyMemberDTO verifyMemberDTO) {
        try {
            String email = verifyMemberDTO.getEmail();

            if (!memberRepository.existsByEmail(email)) {
                return VarList.Not_Found;
            }

            Member member = memberRepository.findByEmail(email);
            String verificationCode = VerificationCodeGenerator.generateCode(6);
            member.setVerificationCode(verificationCode);
            memberRepository.save(member);
            emailService.sendVerificationEmail(email, verificationCode);

            return VarList.OK;
        } catch (Exception e) {
            return VarList.Internal_Server_Error;
        }
    }
    @Override
    public int reactiveMember(VerifyMemberDTO verifyMemberDTO) {
try{
    String email = verifyMemberDTO.getEmail();
    if (!memberRepository.existsByEmail(email)) {
        return VarList.Not_Found;
    }
    Member member = memberRepository.findByEmail(email);
    String verificationCode = VerificationCodeGenerator.generateCode(6);
    member.setVerificationCode(verificationCode);
    memberRepository.save(member);
    emailService.sendVerificationEmail(email, verificationCode);
    return VarList.OK;
} catch (Exception e) {
    throw new RuntimeException(e);
}
    }

    @Override
    public int verifyMember2(String email, String code) {
        Member member = memberRepository.findByEmail(email);
        if (member != null && member.getVerificationCode().equals(code)) {
            member.setVerified(true);
            member.setVerificationCode(null);
            memberRepository.save(member);
            return VarList.OK;
        }
        return VarList.Not_Found;
    }

    //
    @Override
    public int verifyMember(String email, String code) {
        Member member = memberRepository.findByEmail(email);
        if (member != null && member.getVerificationCode().equals(code)) {
            member.setVerified(false);
            member.setVerificationCode(null);
            memberRepository.save(member);
            return VarList.OK;
        }
        return VarList.Not_Found;
    }


    //update member
    @Override
    public int updateMember(MemberDTO memberDTO) {
        if(memberRepository.existsById(memberDTO.getId())){
            Member member = modelMapper.map(memberDTO, Member.class);
            memberRepository.save(member);
            return VarList.OK;
        }
        else {
            return VarList.Not_Found;
        }
    }



}
