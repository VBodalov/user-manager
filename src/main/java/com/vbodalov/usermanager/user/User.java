package com.vbodalov.usermanager.user;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "user")
class User {

    @Id
    @SequenceGenerator(name = "sqn_user")
    @GeneratedValue(generator = "sqn_user", strategy = SEQUENCE)
    private Long id;

    @Column(name = "user_name", length = 20, nullable = false)
    private String userName;

    @Column(name = "password", length = 10, nullable = false)
    private String password;

    @Column(name = "active")
    private boolean active;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
