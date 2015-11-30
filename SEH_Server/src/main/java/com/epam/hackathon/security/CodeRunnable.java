package com.epam.hackathon.security;

import com.epam.hackathon.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@Transactional
public class CodeRunnable implements Runnable {

    @Autowired
    private CodeService codeService;

    @Override
    public void run() {
        System.out.println("Executor run");
        System.out.println(codeService.getCode());
    }
}



