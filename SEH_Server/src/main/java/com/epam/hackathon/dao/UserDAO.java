package com.epam.hackathon.dao;

import com.epam.hackathon.entity.User;
import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface UserDAO extends GenericDAO<User, Long> {

    Boolean authenticate(String email, String password);

    User findByEmail(String email);
}
