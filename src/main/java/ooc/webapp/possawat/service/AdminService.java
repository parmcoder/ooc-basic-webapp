package ooc.webapp.possawat.service;

import ooc.webapp.possawat.dao.UserDAO;
import ooc.webapp.possawat.model.AppUser;
import ooc.webapp.possawat.utilities.EncryptorUtils;
import ooc.webapp.possawat.utilities.WebUtils;
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
    public void addNewUser(AppUser user) {
        if(checkExistedUser(user)){
            return;
        }
        user.setEncryptedPassword(EncryptorUtils.encryptPassword(user.getEncryptedPassword()));
        this.UserDAO.insertUser(user,0);
    }

    @Override
    public void removeUser(AppUser user) {
        if(checkExistedUser(user)){
            this.UserDAO.removeUser(user);
        }
    }

    @Override
    public Boolean checkExistedUser(AppUser user){
        if(this.UserDAO.findUserAccount(user.getUserName()) != null){
            return true;
        }
        return false;
    }

}