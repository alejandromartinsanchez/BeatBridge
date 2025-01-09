package me.zurdo.beatbridge;

import android.app.Application;

import me.zurdo.beatbridge.auth.CookieStorage;
import me.zurdo.beatbridge.auth.User;
import me.zurdo.beatbridge.auth.ValidateApi;

public class Main extends Application {

    public static final CookieStorage cookies = new CookieStorage();
    public static User registeredUser = ValidateApi.validateToken();

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
