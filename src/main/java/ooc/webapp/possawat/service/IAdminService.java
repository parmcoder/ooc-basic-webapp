package ooc.webapp.possawat.service;

import ooc.webapp.possawat.model.AppUser;

import java.util.List;

public interface IAdminService {
    List<AppUser> findAll();
    void addNewUser();
    void removeUser(String password);
}
