package lk.ijse.back_end_prerental.service.custom.IMPL;



import lk.ijse.back_end_prerental.Entity.User;
import lk.ijse.back_end_prerental.config.VerificationCodeGenerator;
import lk.ijse.back_end_prerental.dto.UserDTO;
import lk.ijse.back_end_prerental.dto.VerifyUserDTO;
import lk.ijse.back_end_prerental.repo.UserRepository;
import lk.ijse.back_end_prerental.service.custom.UserService;
import lk.ijse.back_end_prerental.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {
  /*  @Autowired
    private MemberRepository memberRepository;*/
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        return modelMapper.map(user,UserDTO.class);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }


    @Override
    public UserDTO searchUser(String username) {
        if (userRepository.existsByEmail(username)) {
            User user=userRepository.findByEmail(username);
            return modelMapper.map(user,UserDTO.class);
        } else {
            return null;
        }
    }

    @Override
    public int updateUser(UserDTO userDTO) {
        try{
            if(userRepository.existsByEmail(userDTO.getEmail())){
                User user = userRepository.findByEmail(userDTO.getEmail());

                user.setName(userDTO.getName());
                user.setAddress(userDTO.getAddress());
                user.setNational_id(userDTO.getNational_id());
                user.setPostal_code(userDTO.getPostal_code());
                user.setPrimary_phone_number(userDTO.getPrimary_phone_number());
                user.setSecondary_phone_number(userDTO.getSecondary_phone_number());

                if(userDTO.getPassword() != null){
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                }
                userRepository.save(user);
                return VarList.OK;
            }else{
                return VarList.Not_Found;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int deactiveAccount(VerifyUserDTO verifyUserDTO){
        try {
            if (userRepository.existsByEmail(verifyUserDTO.getEmail())) {
                User user = userRepository.findByEmail(verifyUserDTO.getEmail());
                String verificationCode = VerificationCodeGenerator.generateCode(6);
                user.setVerificationCode(verificationCode);
                userRepository.save(user);
                emailService.sendVerificationEmail(user.getEmail(), verificationCode);
                return VarList.OK;
            } else {
                return VarList.Not_Found;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int verifyUser2(String email, String code) {
        try{
            User user = userRepository.findByEmail(email);
            if (user.getVerificationCode().equals(code)) {
                user.setVerified(false);
                user.setVerificationCode(null);
                userRepository.save(user);


                return VarList.OK;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return VarList.Not_Found;
    }

    @Override
    public int updateUser(UserDTO userDTO, MultipartFile file) {
        try {
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                User user = userRepository.findByEmail(userDTO.getEmail());

                // Update file details if file is present
                if (file != null && !file.isEmpty()) {
                    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                    user.setFileName(fileName);
                    user.setFiletype(file.getContentType());
                    user.setData(file.getBytes());
                }

                // Update user details
                user.setAddress(userDTO.getAddress());
                user.setName(userDTO.getName());
                user.setNational_id(userDTO.getNational_id());
                user.setPostal_code(userDTO.getPostal_code());
                user.setPrimary_phone_number(userDTO.getPrimary_phone_number());
                user.setSecondary_phone_number(userDTO.getSecondary_phone_number());
                user.setCity(userDTO.getCity());
                user.setJoinDate(userDTO.getJoinDate());

                userRepository.save(user);
                return VarList.OK;
            } else {
                return VarList.Not_Found;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }

/*
    @Override
    public int updateUser(UserDTO userDTO, MultipartFile file) {
        try {
            if(userRepository.existsByEmail(userDTO.getEmail())){
                User user = userRepository.findByEmail(userDTO.getEmail());
                */
/*====================================================================*//*

                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                user.setFileName(fileName);
                user.setFiletype(file.getContentType());
                user.setData(file.getBytes());
                */
/*===================================================================*//*

                user.setAddress(userDTO.getAddress());
                user.setName(userDTO.getName());
                user.setNational_id(userDTO.getNational_id());
                user.setPostal_code(userDTO.getPostal_code());
                user.setPrimary_phone_number(userDTO.getPrimary_phone_number());
                user.setSecondary_phone_number(userDTO.getSecondary_phone_number());
                user.setCity(userDTO.getCity());

                userRepository.save(user);
                return VarList.OK;
            }
            else {
                return VarList.Not_Found;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
*/

    @Override
    public int updateUser2(UserDTO userDTO) {
        return 0;
    }


    //get last 4 users
    @Override
    public List<UserDTO> getLast4Users() {
      /*  List<User> users = userRepository.findLast4MembersByJoinDate();
        return modelMapper.map(users, new TypeToken<List<UserDTO>>() {}.getType());*/

        return List.of();
    }


    @Override
    public int verifyUser(String email, String code) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getVerificationCode().equals(code)) {
            user.setVerified(true);
            user.setVerificationCode(null);
            userRepository.save(user);
            return VarList.OK;
        }
        return VarList.Not_Found;
    }
    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            String verificationCode = VerificationCodeGenerator.generateCode(6);
            userDTO.setVerificationCode(verificationCode);
            userDTO.setVerified(false);
            userRepository.save(modelMapper.map(userDTO, User.class));
            emailService.sendVerificationEmail(userDTO.getEmail(), verificationCode);
            return VarList.Created;
        }
    }
    
    @Override
    public List<UserDTO> getUsers(){
        List<User> users = userRepository.findAll();
        return modelMapper.map(users, new TypeToken<List<UserDTO>>() {}.getType());
    }

    @Override
    public int deleteUser(String email) {
        if(userRepository.existsByEmail(email)){
            userRepository.deleteByEmail(email);
            return VarList.OK;
        }
        else {
            return VarList.Not_Found;
        }
    }
@Override
public int CodeSent(String userEmail){
        User user = userRepository.findByEmail(userEmail);
        if(user!= null){
            String verificationCode = VerificationCodeGenerator.generateCode(6);
            user.setVerificationCode(verificationCode);
            userRepository.save(user);
            emailService.sendVerificationEmail(user.getEmail(), verificationCode);
            return VarList.OK;
        }
        else {
            return VarList.Not_Found;
        }
  }

};

