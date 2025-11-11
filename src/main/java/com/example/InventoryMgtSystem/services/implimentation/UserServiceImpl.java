package com.example.InventoryMgtSystem.services.implimentation;

import com.example.InventoryMgtSystem.dtos.UsersDTO;
import com.example.InventoryMgtSystem.dtos.requests.LoginRequest;
import com.example.InventoryMgtSystem.dtos.requests.RegisterRequest;
import com.example.InventoryMgtSystem.dtos.response.Response;
import com.example.InventoryMgtSystem.enums.UserRole;
import com.example.InventoryMgtSystem.exceptions.InvalidCredentialsException;
import com.example.InventoryMgtSystem.exceptions.NotFoundException;
import com.example.InventoryMgtSystem.models.Users;
import com.example.InventoryMgtSystem.repositories.UsersRepository;
import com.example.InventoryMgtSystem.security.JwtUtils;
import com.example.InventoryMgtSystem.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;

    @Override
    public Response registerUser(RegisterRequest registerRequest) {
        UserRole role = UserRole.MANAGER;
        if (registerRequest.getRole() != null){
            role = registerRequest.getRole();
        }
        Users userToSave = Users.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .build();

        usersRepository.save(userToSave);

        return Response.builder()
                .status(200)
                .message("User was successfully registered")
                .build();

    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        Users user = usersRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new NotFoundException("Email Not Found"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Password Does Not Match");
            
        }
        String token = jwtUtils.generateToken(user.getEmail());

        return Response.builder()
                .status(200)
                .message("user logged in Successfully")
                .role(user.getRole())
                .token(token)
                .expirationTime("6 months")
                .build();
    }

    @Override
    public Response getAllUsers() {
        List<Users> users = usersRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        users.forEach(user -> user.setTransactions(null));

        List<UsersDTO> userDTOS = modelMapper.map(users, new TypeToken<List<UsersDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .users(userDTOS)
                .build();
    }

    @Override
    public Users getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        Users user = usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User Not Found"));

        user.setTransactions(null);

        return user;
    }

    @Override
    public Response getUserById(Long id) {

        Users users = usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));

        UsersDTO usersDTO = modelMapper.map(users, UsersDTO.class);

        usersDTO.setTransactions(null);

        return Response.builder()
                .status(200)
                .message("success")
                .user(usersDTO)
                .build();
    }

    @Override
    public Response updateUser(Long id, UsersDTO usersDTO) {

        Users existingUser = usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));

        if (usersDTO.getEmail() != null) existingUser.setEmail(usersDTO.getEmail());
        if (usersDTO.getPhoneNumber() != null) existingUser.setPhoneNumber(usersDTO.getPhoneNumber());
        if (usersDTO.getName() != null) existingUser.setName(usersDTO.getName());
        if (usersDTO.getRole() != null) existingUser.setRole(usersDTO.getRole());

        if (usersDTO.getPassword() != null && !usersDTO.getPassword() .isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(usersDTO.getPassword()));
        }
        usersRepository.save(existingUser);
        return Response.builder()
                .status(200)
                .message("User successfully updated")
                .build();
    }

    @Override
    public Response deleteUser(Long id) {
        usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));
        usersRepository.deleteById(id);
        return Response.builder()
                .status(200)
                .message("Successfully deleted")
                .build();
    }

    @Override
    public Response getUserTransaction(Long id) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));
        UsersDTO usersDTO = modelMapper.map(users, UsersDTO.class);

        usersDTO.getTransactions().forEach(transactionDTO -> {
            transactionDTO.setUser( null);
            transactionDTO.setSupplier( null);
        });
        return Response.builder()
                .status(200)
                .message("success")
                .user(usersDTO)
                .build();
    }
}
