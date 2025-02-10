package org.example.haso.domain.business.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BusinessType {

    SUPPLY("공급"),
    DEMAND("수요");

    private final String BusinessTypeName;

}




