package com.example.InventoryMgtSystem.services;


import com.example.InventoryMgtSystem.dtos.UsersDTO;
import com.example.InventoryMgtSystem.dtos.requests.LoginRequest;
import com.example.InventoryMgtSystem.dtos.requests.RegisterRequest;
import com.example.InventoryMgtSystem.dtos.response.Response;
import com.example.InventoryMgtSystem.models.Users;


public interface UserService {
    Response registerUser(RegisterRequest registerRequest);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUsers();

    Users getCurrentLoggedInUser();

    Response getUserById(Long id);

    Response updateUser(Long id, UsersDTO usersDTO);

    Response deleteUser(Long id);

    Response getUserTransaction(Long id);
}
