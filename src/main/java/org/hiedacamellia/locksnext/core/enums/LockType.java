package org.hiedacamellia.locksnext.core.enums;

public enum LockType {
    SIMPLE("simple"),
    CLASSIC("classic"),
    MODERN("modern"),
    HARD("hard");


    final String name;
    LockType(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
