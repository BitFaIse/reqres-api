package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class CreateUpdateUserPayload {

    private String name, job;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static @Data class CreateUserResponse {

        private String name, job, id, createdAt;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static @Data class UpdateUserResponse {

        private String name, job, id, createdAt;
    }

}
