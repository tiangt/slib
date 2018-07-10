package com.niko.slib.bean;

/**
 * Created by niko on 16/2/23.
 */
public class LetterBean implements Sortable {
    private String mLetter;

    @Override
    public String getKey() {
        return mLetter;
    }

    @Override
    public Type getType() {
        return Type.CATEGORY;
    }

    public String getLetter() {
        return mLetter;
    }

    public void setLetter(String letter) {
        mLetter = letter;
    }
}
