package main;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashPasswords {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashPassword(String plainPassword) {
        return encoder.encode(plainPassword);
    }
    public static boolean verificaPassword(String plainPassword, String hashedPassword) {
        return encoder.matches(plainPassword, hashedPassword);
    }
}


