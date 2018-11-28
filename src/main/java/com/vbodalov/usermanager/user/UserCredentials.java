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
class UserCredentials {

    private String userName;
    private String password;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
