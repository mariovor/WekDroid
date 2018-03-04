package eu.hquer.wekdroid.enums;

/**
 * Created by mariovor on 04.03.18.
 */

public enum SharedPrefEnum {
    BASE_PREF("eu.hquer.wekdroid.basePref"),
    USER_ID("userID"),
    BASE_URL("baseUrl");

    String name;

    SharedPrefEnum(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
