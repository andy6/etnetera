package com.etnetera.hr.data;

/**
 * Enumeration represents the current level of fanatical irrational admiration
 */
public enum HypeLevelEnum {
    MAD("Funeral"),
    BAD("No future"),
    GOOD("Usable"),
    WELL("Ongoing support"),
    ENJOY("Play and enjoy");

    String level;

    HypeLevelEnum(String level) {
        this.level = level;
    }
}
