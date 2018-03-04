package eu.hquer.wekdroid.enums;

/**
 * Created by mariovor on 03.03.18.
 */

public enum AuthenticationEnum {
    accountType("eu.hquer.wekdroid.account"),
    authTokenType("full access");

    String name;

    AuthenticationEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
