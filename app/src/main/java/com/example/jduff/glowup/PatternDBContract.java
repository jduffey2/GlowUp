package com.example.jduff.glowup;

import android.provider.BaseColumns;

/**
 * Created by jduff on 3/16/2017.
 */

public final class PatternDBContract {
    private PatternDBContract() {}

    public static class PatternsTable implements BaseColumns {
        public static final String TABLE_NAME = "patterns";
        public static final String COLUMN_NAME = "name";
    }

    public static class ElementTable implements BaseColumns {
        public static final String TABLE_NAME = "elements";
        public static final String COLUMN_INDEX_ID = "indexID";
        public static final String COLUMN_RING_ID = "ringID";
        public static final String COLUMN_PATTERN_ID = "patternID";
        public static final String COLUMN_LENGTH = "length";
        public static final String COLUMN_RED = "red";
        public static final String COLUMN_GREEN = "green";
        public static final String COLUMN_BLUE = "blue";
    }
 }
