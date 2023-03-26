package com.example.searchena.util;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class KeepWatch {
    public static final ConcurrentHashMap<String, Integer> watchMap = new ConcurrentHashMap<>();
    public static final ArrayList<String> watchList = new ArrayList<>();
}
