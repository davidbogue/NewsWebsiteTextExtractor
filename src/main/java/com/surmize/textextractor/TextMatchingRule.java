package com.surmize.textextractor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TextMatchingRule  implements Serializable{

    private List<TextMatchingCondition> matchingConditions = new ArrayList<TextMatchingCondition>();

    public void addCondition(TextMatchingCondition cond){
        getMatchingConditions().add(cond);
    }

    public List<TextMatchingCondition> getMatchingConditions() {
        return matchingConditions;
    }

    public void setMatchingConditions(List<TextMatchingCondition> matchingConditions) {
        this.matchingConditions = matchingConditions;
    }

}