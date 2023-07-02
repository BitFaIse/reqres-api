package model;

import lombok.Data;

public @Data class LoginPayload {

    private String email, password;

    public static @Data class LoginResponse {

        private String id;
        private String token;
    }
}
