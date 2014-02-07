package com.surmize.textextractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author David
 */
public class SpanStrategyTest {
    
    /**
     * Test of cleanHtml method, of class ParagraphStrategy.
     */
    String path = File.separator+"com"+File.separator+"surmize"+File.separator+"textextractor"+File.separator;

    @Test
    public void testCleanMinyanville() throws IOException {
        try {
           testCleanHtml("MinyanvilleHtml.txt","MinyanvilleClean.txt");
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

        SpanStrategy ps = new SpanStrategy();
        String cleanedText = ps.cleanHtml(Jsoup.parse(html));
//        System.out.println(StringUtils.difference(expectedClean, cleanedText));
        System.out.println(cleanedText);
//        assertEquals(expectedClean.trim(), cleanedText.trim());
    }
}
