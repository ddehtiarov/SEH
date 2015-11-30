package com.epam.hackathon;

//import com.epam.hackathon.security.CodeRunnable;
import com.epam.hackathon.security.CodeRunnable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
        System.out.println("Executor start");
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(new CodeRunnable(),5, TimeUnit.SECONDS);
        Thread.currentThread().sleep(1000000);
        System.out.println("Executor end");


    }
}
