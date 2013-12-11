package com.sklay.track.attr;

import java.util.HashMap;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-4-13
 */
public enum AttrType {
    STRING("s"),
    BOOLEAN("b"),
    INTEGER("i"),
    LONG("l"),
    DOUBLE("d"),
    DATE("t"),
    ARRAY("a"),
    MAP("m"),
    DICT("c");

    public static final Map<String, AttrType> MAPPING = new HashMap<String, AttrType>();

    static {
        for (AttrType type : AttrType.values()) {
            MAPPING.put(type.getShortName(), type);
        }
    }

    public static AttrType valueOf0(String shortName) {
        return MAPPING.get(shortName);
    }

    public static String prefix(String name, AttrType type) {
        return type.getShortName() + "__" + name;
    }

    private String shortName;

    private AttrType(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }
}
