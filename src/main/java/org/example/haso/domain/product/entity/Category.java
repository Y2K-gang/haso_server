package org.example.haso.domain.product.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category {

    FISH("어류"),
    MOLLUSK("패류"),
    CEPHALOPOD("두족류"),
    CRUSTACEAN("갑각류"),
    SEAWEED("해조류"),
    OTHER("기타");

    private final String categoryName;

//    public String getCategoryName() {
//        return categoryName;
//    }
}
