package me.zurdo.beatbridge.auth;

public record User(long id, String username, String email, Role role, long creation) {
    public enum Role {
        LISTENER,
        ARTIST,
    }
}
