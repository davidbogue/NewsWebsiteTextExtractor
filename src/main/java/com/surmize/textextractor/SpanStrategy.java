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

/**
 * @author David
 */
public class SpanStrategy {

    private BaseCleaner baseCleaner;

    public SpanStrategy() throws FileNotFoundException {
        try {
            baseCleaner = new BaseCleaner();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParagraphStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String cleanHtml(Document doc) {
        StringBuilder cleanText = new StringBuilder();
        baseCleaner.intitialCleanse(doc);
        Elements spans = doc.getElementsByTag("span");
        for (Element span : spans) {
            baseCleaner.removeSingleSpaceTextNodes(span);
            List<Node> children = span.childNodes();
            if (baseCleaner.nodesContainConsecutiveBR(children)) {
                Elements brs = span.getElementsByTag("br");
                for (Element br : brs) {
                    br.replaceWith(new TextNode("LINEBREAK", null));
                }
                if (!baseCleaner.elementOnlyContainLink(span) && baseCleaner.elementHasPromisingIdentifier(span)) {
                    cleanText.append(span.text());
                }
            }
        }
        String cText = cleanText.toString();
        return cText.replaceAll("LINEBREAK", "\r\n");
    }

}
