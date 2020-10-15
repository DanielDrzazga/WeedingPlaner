package weddingplanner.application.models;

import lombok.Getter;

/**
 * Create by Daniel Drzazga on 15.10.2020
 **/

@Getter
public enum RoleEnum {

    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String value;

    RoleEnum(String value) {
        this.value = value;
    }

}
