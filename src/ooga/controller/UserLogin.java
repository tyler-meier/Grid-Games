package ooga.controller;

import ooga.data.UserProfile;


public interface UserLogin {
    UserProfile getProfile(String username, String password);
}
