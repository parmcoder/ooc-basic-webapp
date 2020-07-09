package ooc.webapp.possawat.service;

import ooc.webapp.possawat.dao.UserDAO;
import ooc.webapp.possawat.model.AppUser;
import ooc.webapp.possawat.utilities.EncryptorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private UserDAO UserDAO;

    @Override
    public List<AppUser> findAll() {
        return this.UserDAO.getAllUsers();
    }

    @Override
    public Boolean addNewUser(AppUser user) {
        if(checkExistedUser(user)){
            return false;
        }
        user.setEncryptedPassword(EncryptorUtils.encryptPassword(user.getEncryptedPassword()));
        this.UserDAO.insertUser(user,0);
        return true;
    }

    @Override
    public Boolean updateUserInfo(String userName, AppUser user) {
        Boolean result = true;
        AppUser appUser = this.UserDAO.findUserAccount(userName);
        appUser.setStatus(user.getStatus());
        if(!user.getUserName().isEmpty() && !checkExistedUser(user)){
            appUser.setUserName(user.getUserName());
        }else{
            result = false;
        }
        this.UserDAO.updateUser(appUser);
        return result;
    }

    @Override
    public AppUser getCurrentInfo(String username) {
        return this.UserDAO.findUserAccount(username);
    }

    @Override
    public Boolean removeUser(AppUser user) {
        if(checkExistedUser(user)){
            this.UserDAO.removeUser(user);
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkExistedUser(AppUser user){
        if(this.UserDAO.findUserAccount(user.getUserName()) != null){
            return true;
        }
        return false;
    }

}