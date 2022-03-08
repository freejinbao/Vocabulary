package com.marcoedu.plentyvocabulary.word;

public class WordBean {

    //cursor.execute('''create table words(
    //id integer primary key autoincrement, idx integer,
    // word text, meaning text, meaning_zh text, collected integer,
    // learned integer, review integer, grp integer)''')
    public int id;
    public int idx;
    public String word;
    public String meaning;
    public String meaning_zh;
    public int collected;
    public int learned;
    public int review;
    public int grp;

    public WordBean() {

    }

    public WordBean(int id, int idx, String word, String meaning, String meaning_zh,
                    int collected, int learned, int review, int grp) {
        this.id = id;
        this.idx = idx;
        this.word = word;
        this.meaning = meaning;
        this.meaning_zh = meaning_zh;
        this.collected = collected;
        this.learned = learned;
        this.review = review;
        this.grp = grp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaningZh() {
        return meaning_zh;
    }

    public void setMeaningZh(String meaning_zh) {
        this.meaning_zh = meaning_zh;
    }

    public int getCollected() {
        return collected;
    }

    public void setCollected(int collected) {
        this.collected = collected;
    }

    public int getLearned() {
        return learned;
    }

    public void setLearned(int learned) {
        this.learned = learned;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getGrp() {
        return grp;
    }

    public void setGrp(int grp) {
        this.grp = grp;
    }
}
