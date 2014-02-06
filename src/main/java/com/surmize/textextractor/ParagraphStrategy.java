package com.surmize.textextractor;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David
 */
public class ParagraphStrategy {

    private BaseCleaner baseCleaner;

    public ParagraphStrategy() {
        try {
            baseCleaner = new BaseCleaner();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParagraphStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String cleanHtml(Document doc) {
        StringBuilder cleanText = new StringBuilder();
        baseCleaner.intitialCleanse(doc);
        Elements paragraphs = doc.getElementsByTag("p");
        for (Element p : paragraphs) {
            if (!baseCleaner.elementOnlyContainLink(p) && baseCleaner.elementHasPromisingIdentifier(p)) {
                if (StringUtils.isNotBlank(p.text()) && baseCleaner.isGoodText(p.text())) {
                    cleanText.append(p.text()).append("\r\n\r\n");
                }
            }
        }
        return cleanText.toString();
    }
}
