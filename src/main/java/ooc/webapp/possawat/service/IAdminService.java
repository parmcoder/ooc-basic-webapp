package ooc.webapp.possawat.service;

import ooc.webapp.possawat.model.AppUser;

import java.util.List;

public interface IAdminService {
    List<AppUser> findAll();

    Boolean removeUser(AppUser user);

    Boolean checkExistedUser(AppUser user);

    Boolean addNewUser(AppUser user);

    Boolean updateUserInfo(String userName, AppUser user);

    AppUser getCurrentInfo(String username);

}
