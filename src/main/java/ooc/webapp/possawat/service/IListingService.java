package ooc.webapp.possawat.service;

import ooc.webapp.possawat.model.AppUser;

import java.util.List;

public interface IListingService {
    List<AppUser> findAll();
}
