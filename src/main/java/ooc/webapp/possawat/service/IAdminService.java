package ooc.webapp.possawat.service;

import ooc.webapp.possawat.model.AppUser;

import java.util.List;

public interface IAdminService {
    List<AppUser> findAll();

    void removeUser(AppUser user);

    Boolean checkExistedUser(AppUser user);

    void addNewUser(AppUser user);

}
