package com.surmize.textextractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author David
 */
public class ParagraphStrategyTest {

    /**
     * Test of cleanHtml method, of class ParagraphStrategy.
     */
    String path = File.separator+"com"+File.separator+"surmize"+File.separator+"textextractor"+File.separator;

    @Test
    public void testTheStreet() throws IOException {
        try {
           testCleanHtml("TheStreetHtml.txt","TheStreetClean.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("file not found");
        }
    }
    
    @Test
    public void testBaronsBlog() throws IOException {
        try {
           testCleanHtml("BaronsBlogHtml.txt","BaronsBlogClean.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("file not found");
        }
    }

    @Test
    public void testCleanCnn() throws IOException {
        try {
           testCleanHtml("cnnHtml.txt","cnnClean.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("file not found");
        }
    }
  
    @Test
    public void testBloomberg() throws IOException {
        try {
            testCleanHtml("BloombergHtml.txt","BloombergClean.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("file not found");
        }
    }
    
    @Test
    public void testCleanNewYorkTimes() throws IOException {
        try {
            testCleanHtml("NYTHtml.txt","NYTClean.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("file not found");
        }
    }
  
    @Test
    public void testCleanNasdaqBlog() throws IOException {
        try {
            testCleanHtml("NasdaqHtml.txt","NasdaqClean.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("file not found");
        }
    }
    
    @Test
    public void testCleanMarketWatch() throws IOException {
        try {
            testCleanHtml("MarketWatchHtml.txt","MarketWatchClean.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("file not found");
        }
    }
   
    @Test
    public void testCleanBusinessWeek() throws IOException {
        try {
            testCleanHtml("BusinessWeekHtml.txt","BusinessWeekClean.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("file not found");
        }
    }
    
    @Test
    public void testSeekingAlpha() throws IOException {
        try {
            testCleanHtml("SeekingAlphaHtml.txt","SeekingAlphaClean.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("file not found");
        }
    }
    
    @Test
    public void testPropertyMentor() throws IOException {
        try {
            testCleanHtml("PropertyMentorHtml.txt","PropertyMentorClean.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("file not found");
        }
    }
   
    @Test
    public void testUsaToday() throws IOException {
        try {
            testCleanHtml("usaTodayHtml.txt","usaTodayClean.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("file not found");
        }
    }
   
    @Test
    public void testBBC() throws IOException {
        try {
            testCleanHtml("BbcHtml.txt","BbcClean.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("file not found");
        }
    }
   
    @Test
    public void testOnion() throws IOException {
        try {
            testCleanHtml("OnionHtml.txt","OnionClean.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("file not found");
        }
    }
   
    @Test
    public void testBoston() throws IOException {
        try {
            testCleanHtml("BostonHtml.txt","BostonClean.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("file not found");
        }
    }

    @Test
    public void testHouston() throws IOException {
        try {
            testCleanHtml("HoustonHtml.txt","HoustonClean.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("file not found");
        }
    }

    private void testCleanHtml(String htmlFile, String cleanFile) throws FileNotFoundException, URISyntaxException {
        File fileHTML = new File( this.getClass().getResource( path + htmlFile ).toURI() );
        String html = new Scanner(fileHTML, "UTF-8").useDelimiter("\\Z").next();

        File fileClean = new File( this.getClass().getResource( path + cleanFile ).toURI() );
        String expectedClean = new Scanner(fileClean, "UTF-8").useDelimiter("\\Z").next();

        ParagraphStrategy ps = new ParagraphStrategy();
        String cleanedText = ps.cleanHtml(Jsoup.parse(html));
//        System.out.println(StringUtils.difference(expectedClean, cleanedText));
        System.out.println(cleanedText);
        assertEquals(expectedClean.trim(), cleanedText.trim());
    }
}
