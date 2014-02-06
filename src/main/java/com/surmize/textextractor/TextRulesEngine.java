package com.surmize.textextractor;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author David
 */
public class TextRulesEngine {

    List<TextMatchingRule> rules;

    public TextRulesEngine(List<TextMatchingRule> rules) {
        this.rules = rules;
    }

    public TextRulesEngine(String ruleFileName) throws FileNotFoundException {
        rules = new ArrayList<TextMatchingRule>();
        InputStream is = getClass().getResourceAsStream("/" + ruleFileName);
        Scanner sc = new Scanner(is);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.startsWith("//")) {
                TextMatchingRule rule = buildRuleString(line);
                rules.add(rule);
            }
        }
        sc.close();
    }

    public boolean appliesTo(String text) {
        for (TextMatchingRule textMatchingRule : rules) {
            boolean allConditionsMet = true;
            for (TextMatchingCondition condition : textMatchingRule.getMatchingConditions()) {
                if (!conditionMet(text, condition)) {
                    allConditionsMet = false;
                    break;
                }
            }
            if (allConditionsMet) { // a rule was matched so we can return true
                return true;
            }
        }
        return false;
    }

    private boolean conditionMet(String text, TextMatchingCondition condition) {
        boolean conditionMet = false;
        switch (condition.getMatchCriteria()) {
            case EQUALS:
                conditionMet = text.equalsIgnoreCase(condition.getMatcherString());
                break;
            case STARTS_WITH:
                conditionMet = text.startsWith(condition.getMatcherString());
                break;
            case ENDS_WITH:
                conditionMet = text.endsWith(condition.getMatcherString());
                break;
            case CONTAINS:
                conditionMet = text.contains(condition.getMatcherString());
                break;
            case DOES_NOT_START_WITH:
                conditionMet = !text.startsWith(condition.getMatcherString());
                break;
            case DOES_NOT_END_WITH:
                conditionMet = !text.endsWith(condition.getMatcherString());
                break;
            case DOES_NOT_CONTAIN:
                conditionMet = !text.contains(condition.getMatcherString());
                break;
            case MATCHES_PATTERN:
                //TODO implement regex matching
                break;
        }
        return conditionMet;
    }

    private TextMatchingRule buildRuleString(String ruleString) {
        TextMatchingRule rule = new TextMatchingRule();
        String[] conditions = ruleString.split("&");
        for (String conditionString : conditions) {
            conditionString = conditionString.trim();
            String[] conditionParts = conditionString.split("=>");
            if (conditionParts.length == 2) {
                TextMatchingCondition condition = new TextMatchingCondition(StringMatchCriteria.valueOf(conditionParts[0]), conditionParts[1]);
                rule.addCondition(condition);
            }
        }
        return rule;
    }
}
