package com.myblog.myblog34;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class A {


    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("Testing"));

    }

}
