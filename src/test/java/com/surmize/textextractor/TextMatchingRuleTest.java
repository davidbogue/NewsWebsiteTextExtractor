/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.surmize.textextractor;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import static org.junit.Assert.*;

import org.junit.*;

/**
 *
 * @author David
 */
public class TextMatchingRuleTest {

    String path = File.separator+"com"+File.separator+"surmize"+File.separator+"textextractor"+File.separator;

    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addCondition method, of class TextMatchingRule.
     */
    @Test
    public void testSerialization() throws FileNotFoundException {
        System.out.println("testSerialization");
        TextMatchingCondition cond = new TextMatchingCondition(StringMatchCriteria.CONTAINS, "someText");
        TextMatchingRule instance = new TextMatchingRule();
        instance.addCondition(cond);
        try {
            File outputFile = new File( this.getClass().getResource( path+"textMatchCondition.xml" ).toURI() );
            FileOutputStream os = new FileOutputStream(outputFile);
            XMLEncoder encoder = new XMLEncoder(os);
            encoder.writeObject(instance);
            encoder.close();
        } catch (URISyntaxException e) {
            fail("bad output file");
            return;
        }

    }


}
