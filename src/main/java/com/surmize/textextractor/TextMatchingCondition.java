package com.surmize.textextractor;

import java.io.Serializable;

public class TextMatchingCondition implements Serializable{

    private StringMatchCriteria matchCriteria;
    private String matcherString;

    public TextMatchingCondition(StringMatchCriteria matchCriteria, String matcherString) {
        this.matchCriteria = matchCriteria;
        this.matcherString = matcherString;
    }

    public StringMatchCriteria getMatchCriteria() {
        return matchCriteria;
    }

    public void setMatchCriteria(StringMatchCriteria matchCriteria) {
        this.matchCriteria = matchCriteria;
    }

    public String getMatcherString() {
        return matcherString;
    }

    public void setMatcherString(String matcherString) {
        this.matcherString = matcherString;
    }

}