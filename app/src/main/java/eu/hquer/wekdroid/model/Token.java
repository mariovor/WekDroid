package eu.hquer.wekdroid.model;

/**
 * Created by mariovor on 22.02.18.
 */

public class Token {
    String id;
    String token;
    String tokenExpires;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenExpires() {
        return tokenExpires;
    }

    public void setTokenExpires(String tokenExpires) {
        this.tokenExpires = tokenExpires;
    }
}
