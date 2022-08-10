package com.mhosain.cart.service;

import com.mhosain.cart.domain.User;
import com.mhosain.cart.dto.LoginDTO;
import com.mhosain.cart.dto.UserDTO;

public interface UserService {
    void saveUser(UserDTO userDTO);

    boolean isNotUniqueUsername(UserDTO user);

    boolean isNotUniqueEmail(UserDTO user);

    User verifyUser(LoginDTO loginDTO);
}
