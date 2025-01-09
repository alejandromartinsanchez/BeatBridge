package me.zurdo.beatbridge.music;

public record Song(long id, String name, String lyrics, long artist, long album, int duration, String link) {
}