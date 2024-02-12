package me.geuxy.library;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public class Library {

    private final String name, url;
    private final int bytes;

}
