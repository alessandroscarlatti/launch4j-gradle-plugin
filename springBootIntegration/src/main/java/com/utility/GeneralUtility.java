package com.utility;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Wednesday, 5/2/2018
 */
@SpringBootApplication
public class GeneralUtility implements CommandLineRunner {

    private SillyService sillyService;
    private SillyService2 sillyService2;

    public GeneralUtility(SillyService sillyService, SillyService2 sillyService2) {
        this.sillyService = sillyService;
        this.sillyService2 = sillyService2;
    }

    @Time
    @Override
    public void run(String... args) throws Exception {
        System.out.println("hello Spring Boot3!");
        System.out.println(sillyService.tellAJoke("what"));
        System.out.println(sillyService2.tellAJoke("what2"));

//        System.out.println("stdin is " + System.in);
//        Scanner scanner = new Scanner(System.in);
//
//        String line = scanner.nextLine();
//        System.out.println(">>>" + line);
    }


    public static void main(String[] args) {
        SpringApplication.run(GeneralUtility.class, args);
    }
}
