package me.zurdo.beatbridge.auth;

import java.util.Date;

public record User(long id, String username, String password, String email, Role role, Date creation) {
    enum Role {
        OYENTE,
        ARTISTA,
        ADMIN
    }
}
