package ru.job4j;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@SpringBootApplication
public class Job4AuthApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Job4AuthApplication.class, args);

    }

}
