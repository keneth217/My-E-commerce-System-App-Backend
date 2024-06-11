package com.example.commerce.services.Auth;

import com.example.commerce.constants.AuthConstants;
import com.example.commerce.dto.*;
import com.example.commerce.entity.Orders;
import com.example.commerce.entity.User;
import com.example.commerce.enums.OrderStatus;
import com.example.commerce.enums.Role;
import com.example.commerce.exceptions.CustomerAlreadyExistException;
import com.example.commerce.repository.OrderRepository;
import com.example.commerce.repository.UserRepository;
import com.example.commerce.services.Jwt.UserService;
import com.example.commerce.util.JwtUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements  AuthService{
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final OrderRepository orderRepository;
    private  final UserService userService;
    private final JwtUtils jwtUtils;
    @PostConstruct
    public void createAdmin(){
        User adminAccount=userRepository.findByRole(Role.ADMIN);
        if (adminAccount==null){
            User newAdmin= new User();
            newAdmin.setFirstName("keneth ");
            newAdmin.setLastName(" admin");
            newAdmin.setPhone("0711766223");
            newAdmin.setEmail("admin@test.com");
            newAdmin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            newAdmin.setRole(Role.ADMIN);
            userRepository.save(newAdmin);
            System.out.println("new admin adding");
        }
    }

    @Override
    public User createCustomer(SignUpRequest signUpRequest) {
        Optional<User> optionalUser=userRepository.findFirstByPhone(signUpRequest.getPhone());
        if (optionalUser.isPresent()){
            throw new CustomerAlreadyExistException("Customer already registered with the given phone number"+signUpRequest.getPhone());
        }else{

        System.out.println("adding user------------");
        User user=new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setPhone(signUpRequest.getPhone());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        user.setRole(Role.CUSTOMER);
        User createdUser=userRepository.save(user);
        System.out.println("user added"+createdUser);
        System.out.println("user added"+user);


        UserDto userDto=new UserDto();
        userDto.setId(createdUser.getId());
        return user;
    }
    }
    public AuthenthicationResponse createAuthToken(@RequestBody AuthenthicationRequest authenticationRequest)

    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getPhone(),
                    authenticationRequest.getPassword()));
        } catch (RuntimeException e) {
            throw new RuntimeException(AuthConstants.LOGIN_ERROR);
        }
        final UserDetails userDetails=userService.userDetailsService()
                .loadUserByUsername(authenticationRequest.getPhone());
        Optional<User> optionalUser=userRepository.findFirstByPhone(authenticationRequest.getPhone());
        final String jwt= jwtUtils.generateToken(userDetails.getUsername());

        AuthenthicationResponse authenticationResponse= new AuthenthicationResponse();
        if (optionalUser.isPresent()){
            String userName=optionalUser.get().getFirstName()+" "+optionalUser.get().getLastName();
            authenticationResponse.setToken(jwt);
           authenticationResponse.setResponseDto(new ResponseDto(AuthConstants.LOGIN_CODE,userName+","+AuthConstants.LOGIN_MESSAGE));
           authenticationResponse.setUser(optionalUser.get());
        }
        return authenticationResponse;
    }


    @Override
    public Boolean hasCustomerWithPhone(String email) {
        return userRepository.findFirstByPhone(email).isPresent();
    }
}
