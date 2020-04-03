package ooga.controller;

import ooga.data.DataObject;

public interface UserLogin {
    DataObject getProfile(String username, String password);
}
