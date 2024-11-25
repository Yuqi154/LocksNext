package org.hiedacamellia.locksnext.core.enums;

public enum LockType {
    EASY("easy"),
    NORMAL("normal"),
    HARD("hard"),
    MODERN("modern");


    final String name;
    LockType(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
