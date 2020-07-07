package ooc.webapp.possawat.service;

import ooc.webapp.possawat.dao.RoleDAO;
import ooc.webapp.possawat.dao.UserDAO;
import ooc.webapp.possawat.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListingService implements IListingService {

    @Autowired
    private UserDAO UserDAO;

    @Override
    public List<AppUser> findAll() {
        return UserDAO.getAllUsers();
    }
}