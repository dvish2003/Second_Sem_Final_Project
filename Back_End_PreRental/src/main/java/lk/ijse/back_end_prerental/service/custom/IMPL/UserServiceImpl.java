package lk.ijse.back_end_prerental.service.custom.IMPL;



import lk.ijse.back_end_prerental.Entity.User;
import lk.ijse.back_end_prerental.dto.UserDTO;
import lk.ijse.back_end_prerental.repo.UserRepository;
import lk.ijse.back_end_prerental.service.custom.UserService;
import lk.ijse.back_end_prerental.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

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
        if(userRepository.existsByEmail(userDTO.getEmail())){
            User user = userRepository.findByEmail(userDTO.getEmail());
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
    }

    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }


};

