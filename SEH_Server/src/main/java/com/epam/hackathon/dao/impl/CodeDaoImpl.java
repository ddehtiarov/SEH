package com.epam.hackathon.dao.impl;

import com.epam.hackathon.dao.CodeDAO;
import com.epam.hackathon.dao.UserDAO;
import com.epam.hackathon.entity.Code;
import com.epam.hackathon.entity.User;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static org.hibernate.criterion.Restrictions.eq;


@Repository
@Transactional
public class CodeDaoImpl extends GenericDAOImpl<Code, Long> implements CodeDAO {


    @Autowired
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }


    @Override
    public Code findCode() {
        Criteria criteria = getSession().createCriteria(Code.class);
        criteria.setMaxResults(1);
        return (Code) criteria.uniqueResult();
    }
}
