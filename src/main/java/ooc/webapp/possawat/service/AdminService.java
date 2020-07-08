package ooc.webapp.possawat.service;

import ooc.webapp.possawat.dao.UserDAO;
import ooc.webapp.possawat.model.AppUser;
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
    public void addNewUser() {

    }

    @Override
    public void removeUser(String password) {

    }

}