package br.com.compassuol.pb.challenge.msproducts.utils;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.UserRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.UserResponse;
import br.com.compassuol.pb.challenge.msproducts.domain.model.UserModel;

import java.util.HashSet;

import static br.com.compassuol.pb.challenge.msproducts.utils.RoleUtil.rolesDefault;

public class UserUtil {

    public static UserModel userDefault() {
        return UserModel.builder()
                .id(1L)
                .email("john.doe@email.com")
                .password("johndoe123")
                .roles(rolesDefault())
                .build();
    }


    public static UserRequest userRequestDefault() {
        var roles = new HashSet<Long>();
        roles.add(1L);

        return UserRequest.builder()
                .email("john.doe@email.com")
                .password("johndoe123")
                .roles(roles)
                .build();
    }

    public static UserResponse userResponseDefault() {
        return UserResponse.builder()
                .id(1L)
                .email("john.doe@email.com")
                .build();
    }

}
