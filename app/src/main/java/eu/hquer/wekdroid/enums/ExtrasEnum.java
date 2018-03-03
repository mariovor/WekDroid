package eu.hquer.wekdroid.enums;

public enum ExtrasEnum {
    board_title("board_title"),
    board_id("board_id"),
    list_title("list_title"),
    list_id("list_id");

    String packageName = "eu.hquer.wekdroid";
    String name;

    ExtrasEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return String.format("%s.%s", packageName, name);
    }
}