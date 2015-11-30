package com.epam.hackathon.dao;

import com.epam.hackathon.entity.Code;
import com.epam.hackathon.entity.User;
import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface CodeDAO extends GenericDAO<Code, Long> {
    Code findCode();
}
