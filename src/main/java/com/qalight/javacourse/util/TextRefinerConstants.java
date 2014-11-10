package com.qalight.javacourse.util;

import java.util.regex.Pattern;

public class TextRefinerConstants {
    public static final String NON_BREAKING_HYPHEN = "&#8";
    public static final String NON_BREAKING_SPACE_CHARACTER_ENTITY = "&nbsp;";
    public static final String NON_BREAKING_SPACE_NUMERIC_ENTITY = "&#160;";
    public static final String NON_BREAKING_SPACE_LATIN_1 = "\\xA0";
    public static final String SPACE_LENGTH_N_CHARACTER_ENTITY = "&ensp;";
    public static final String SPACE_LENGTH_N_NUMERIC_ENTITY = "&#8194;";
    public static final String SPACE_LENGTH_M_CHARACTER_ENTITY = "&emsp;";
    public static final String SPACE_LENGTH_M_NUMERIC_ENTITY = "&#8195;";
    public static final String NARROW_SPACE_CHARACTER_ENTITY = "&thinsp;";
    public static final String NARROW_SPACE_NUMERIC_ENTITY = "&#8201;";
    public static final String ZERO_WIDTH_NON_JOINER_CHARACTER_ENTITY = "&zwnj;";
    public static final String ZERO_WIDTH_NON_JOINER_NUMERIC_ENTITY = "&#8204;";
    public static final String LONG_DASH = "—";

    public static final Pattern WHITESPACES_PATTERN =
            Pattern.compile("(\\s+)|("+NON_BREAKING_SPACE_CHARACTER_ENTITY+")|("+NON_BREAKING_SPACE_NUMERIC_ENTITY+")" +
                    "|("+NON_BREAKING_SPACE_LATIN_1+")|("+SPACE_LENGTH_N_CHARACTER_ENTITY+")" +
                    "|("+SPACE_LENGTH_N_NUMERIC_ENTITY+")|("+SPACE_LENGTH_M_CHARACTER_ENTITY+")" +
                    "|("+SPACE_LENGTH_M_NUMERIC_ENTITY+")|("+NARROW_SPACE_CHARACTER_ENTITY+")" +
                    "|("+NARROW_SPACE_NUMERIC_ENTITY+")|("+ZERO_WIDTH_NON_JOINER_CHARACTER_ENTITY+")" +
                    "|("+ZERO_WIDTH_NON_JOINER_NUMERIC_ENTITY+")|("+LONG_DASH+")");
    public static final Pattern HYPHEN_AND_APOSROFE_PATTERN = Pattern.compile("(.+-)|(-.+)|('.+)");
    public static final Pattern EMAIL_PATTERN =
            Pattern.compile("(^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))");
    public static final Pattern URL_PATTERN =
            Pattern.compile("(<http://.*)|(<https://.*)|(<ftp://.*)|(<www\\..*)|(http://.*)|(https://.*)|(ftp://.*)|(www\\..*)");
    public static final Pattern CLEAN_PATTERN = Pattern.compile("[^a-zA-Zа-яА-Я-іІїЇєЄёЁґҐ']");
}
