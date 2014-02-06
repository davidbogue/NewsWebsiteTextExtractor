package com.surmize.textextractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public class BaseCleaner {

    private TextRulesEngine questionableTextRules;
    private TextRulesEngine promisingTextRules;
    private TextRulesEngine deletionRules;
    private TextRulesEngine textRemovalRules;

    public BaseCleaner() throws FileNotFoundException {
        questionableTextRules = new TextRulesEngine("questionableIdentifier.rules");
        promisingTextRules = new TextRulesEngine("promisingIdentifiers.rules");
        deletionRules = new TextRulesEngine("deletionIdentifiers.rules");
        textRemovalRules = new TextRulesEngine("textRemoval.rules");
    }

    public void intitialCleanse(Document doc) {
        // NEED TO IMPLEMENT THE RULES ENGINE TO REMOVE CONTENT
        removeElements(doc, deletionRules);
        removeListsOfLinks(doc);
        removeMailToLinks(doc);
    }

    public boolean isGoodText(String text){
        return !textRemovalRules.appliesTo(text);
    }

    public boolean elementOnlyContainLink(Element elem) {
        Elements anchors = elem.getElementsByTag("a");
        if (anchors.size() == 1) {
            return anchors.text().equals(elem.text());
        }
        return false;
    }

    public boolean elementHasPromisingIdentifier(Element elem) {
        //recursively go through classes and check for promising and questionable
        // in none found get parent of element and try again
        // if root node found and it is undetermined, then return true
        Set<String> classNames = elem.classNames();
        for (String className : classNames) {
            className = className.toLowerCase();
            if (questionableTextRules.appliesTo(className) || questionableTextRules.appliesTo(elem.id().toLowerCase())) {
                return false;
            }
            if (promisingTextRules.appliesTo(className) || promisingTextRules.appliesTo(elem.id().toLowerCase())) {
                return true;
            }
        }
        Element parent = elem.parent();
        if (parent == null) {
            return true;
        }
        return elementHasPromisingIdentifier(parent);
    }

    public boolean nodesContainConsecutiveBR(List<Node> nodes) {
        if (nodes == null) {
            return false;
        }
        for (Node node : nodes) {
            if (node.nodeName().equals("br")) {
                if (node.nextSibling() != null && node.nextSibling().nodeName().equals("br")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeSingleSpaceTextNodes(Element elem) {
        List<TextNode> textNodes = elem.textNodes();
        for (TextNode textNode : textNodes) {
            if (textNode.getWholeText().equals(" ") || textNode.getWholeText().trim().equals("") || textNode.getWholeText().trim().equals("\n")) {
                textNode.remove();
            }
        }
    }

    private void removeElements(Document doc, TextRulesEngine deleteRules) {
        Elements elements = doc.getAllElements();
        for (Element elem : elements) {
            Set<String> classNames = elem.classNames();
            for (String className : classNames) {
                className = className.toLowerCase();
                if (deleteRules.appliesTo(className) || deleteRules.appliesTo(elem.id().toLowerCase())) {
                    elem.remove();
                    break;
                }
            }
        }
    }

    private void removeListsOfLinks(Document doc) {
        Elements uls = doc.getElementsByTag("ul");
        for (Element ul : uls) {
            Elements lineItems = ul.getElementsByTag("li");
            int countWithoutLinks = 0;
            int countWithLinks = 0;
            for (Element li : lineItems) {
                if (li.getElementsByTag("a").size() > 0) {
                    countWithLinks++;
                } else {
                    countWithoutLinks++;
                }
            }
            if (countWithLinks > countWithoutLinks) {
                ul.remove();
            }
        }
    }

    private void removeMailToLinks(Document doc) {
        Elements anchors = doc.getElementsByTag("a");
        for (Element anchor : anchors) {
            if (anchor.attr("href").toLowerCase().contains("mailto")) {
                anchor.remove();
            }
        }
    }
}
