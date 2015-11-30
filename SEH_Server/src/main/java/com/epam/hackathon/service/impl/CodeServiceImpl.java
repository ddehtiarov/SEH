package com.epam.hackathon.service.impl;

import com.epam.hackathon.dao.CodeDAO;
import com.epam.hackathon.dao.UserDAO;
import com.epam.hackathon.entity.Code;
import com.epam.hackathon.entity.User;
import com.epam.hackathon.service.CodeService;
import com.epam.hackathon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CodeServiceImpl implements CodeService {

    @Autowired
    private CodeDAO codeDao;

    @Override
    public void update(Code code) {
        codeDao.save(code);
    }


    @Override
    public Code getCode() {
        return codeDao.findCode();
    }

    }
