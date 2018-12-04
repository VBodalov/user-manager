package com.vbodalov.usermanager.user;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String userName;
    private String password;
    private boolean blocked;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
