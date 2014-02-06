package com.surmize.textextractor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebsiteTextExtractor {

    private ParagraphStrategy paragraphStrategy;
    private DivStrategy divStrategy;
    private SpanStrategy spanStrategy;

    private static String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11";

    public WebsiteTextExtractor() throws FileNotFoundException{
        this.paragraphStrategy = new ParagraphStrategy();
        this.divStrategy = new DivStrategy();
        this.spanStrategy = new SpanStrategy();
    }

    public String getHtmlFromUrl(String url) throws IOException {
        Document doc;
        try{
            doc = Jsoup.connect(url).timeout(10*1000).userAgent(userAgent).referrer("http://www.google.com").get();
        }catch(SocketTimeoutException ste){
            // if I get a socket timeout.. try again with a 15 second wait
            doc = Jsoup.connect(url).timeout(15*1000).userAgent(userAgent).referrer("http://www.bing.com").get();
        }
        return doc.outerHtml();
    }

    public String cleanFromURL(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            return cleanHtml(doc);
        } catch (IOException ex) {
            Logger.getLogger(WebsiteTextExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String cleanFromString(String content) {
        Document doc = Jsoup.parse(content);
        return cleanHtml(doc);
    }

    private String cleanHtml(Document doc) {
        //test URL to see if we know best approach for this domain.
        String cleanText = "";
        String paragraphCleanText = paragraphStrategy.cleanHtml(doc.clone());
        String divCleanText = divStrategy.cleanHtml(doc.clone());
        String spanCleanText = spanStrategy.cleanHtml(doc.clone());

        if(cleanTextLooksLegit(paragraphCleanText)){
            cleanText = paragraphCleanText;
        }else{
            cleanText = (paragraphCleanText.length()> divCleanText.length())? paragraphCleanText : divCleanText;
            cleanText = (cleanText.length()> spanCleanText.length())? cleanText : spanCleanText;
        }
        return cleanText;
    }

    private boolean cleanTextLooksLegit(String text) {
        // here is a good candidate for some machine learning
        // for now just use a simple algorithm
        // need to enhance this algorithm so it can look at paragraphs, and identify parts of speech
        if (text.length() < 500) {
            return false;
        }
        return true;
    }

}
