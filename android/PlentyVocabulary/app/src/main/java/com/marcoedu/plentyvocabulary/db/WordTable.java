package com.marcoedu.plentyvocabulary.db;

public class WordTable {
    public static String NAME = "words";

    //cursor.execute('''create table words(
    //id integer primary key autoincrement, idx integer,
    // word text, meaning text, meaning_zh text, collected integer,
    // learned integer, review integer, grp integer)''')
    public static String COL_ID = "id";
    public static String COL_IDX = "idx";
    public static String COL_WORD = "word";
    public static String COL_MEANING = "meaning";
    public static String COL_MEANING_ZH = "meaning_zh";
    public static String COL_COLLECTED = "collected";
    public static String COL_LEARNED = "learned";
    public static String COL_REVIEW = "review";
    public static String COL_GROUP = "grp";

}
