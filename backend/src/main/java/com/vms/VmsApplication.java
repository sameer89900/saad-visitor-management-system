package com.vms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application Class — Real Time Smart Visitor Management System using QR
 *
 * @author Shaik Sameer
 * @version 1.0
 */
@SpringBootApplication
public class VmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(VmsApplication.class, args);
        System.out.println("====================================");
        System.out.println("VMS Backend Started!");
        System.out.println("API: http://localhost:8080/api");
        System.out.println("Login: admin / admin123");
        System.out.println("====================================");
    }
}
