package com.example.animal.type;

import lombok.Getter;

@Getter
public enum VillagerType {
    DOG(1, "개"),
    FROG(2, "개구리"),
    ANTEATER(3, "개미핥기"),
    GORILLA(4, "고릴라"),
    CAT(5, "고양이"),
    BEAR(6, "곰"),
    CUB(7, "꼬마곰"),
    WOLF(8, "늑대"),
    SQUIRREL(9, "다람쥐"),
    CHICKEN(10, "닭"),
    EAGLE(11, "독수리"),
    PIG(12, "돼지"),
    HORSE(13, "말"),
    OCTOPUS(14, "문어"),
    DEER(15, "사슴"),
    LION(16, "사자"),
    BIRD(17, "새"),
    MOUSE(18, "생쥐"),
    COW(19, "소"),
    ALLIGATOR(20, "악어"),
    SHEEP(21, "양"),
    GOAT(22, "염소"),
    DUCK(23, "오리"),
    MONKEY(24, "원숭이"),
    KANGAROO(25, "캥거루"),
    ELEPHANT(26, "코끼리"),
    RHINO(27, "코뿔소"),
    KOALA(28, "코알라"),
    OSTRICH(29, "타조"),
    RABBIT(30, "토끼"),
    PENGUIN(31, "펭귄"),
    HIPPO(32, "하마"),
    HAMSTER(33, "햄스터"),
    TIGER(34, "호랑이");

    private final int code;
    private final String name;

    VillagerType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static VillagerType fromCode(int code) {
        for (VillagerType type : VillagerType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid VillagerType code: " + code);
    }
}
