package com.surmize.textextractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DivStrategy {

    private BaseCleaner baseCleaner;

    public DivStrategy() throws FileNotFoundException {
        try {
            baseCleaner = new BaseCleaner();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParagraphStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String cleanHtml(Document doc) {
        StringBuilder cleanText = new StringBuilder();
        baseCleaner.intitialCleanse(doc);
        Elements divs = doc.getElementsByTag("div");
        for (Element div : divs) {
            baseCleaner.removeSingleSpaceTextNodes(div);
            List<Node> children = div.childNodes();
            if (baseCleaner.nodesContainConsecutiveBR(children)) {
                Elements brs = div.getElementsByTag("br");
                for (Element br : brs) {
                    br.replaceWith(new TextNode("LINEBREAK", null));
                }
                if (!baseCleaner.elementOnlyContainLink(div) && baseCleaner.elementHasPromisingIdentifier(div)) {
                    cleanText.append(div.text());
                }
            }
        }
        String cText = cleanText.toString();
        return cText.replaceAll("LINEBREAK", "\r\n");
    }

}
