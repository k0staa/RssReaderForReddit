package pl.codeaddict.rssreaderforreddit.xml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by kostek on 18.03.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandleXMLTest {

    private static File getFileFromPath(Object obj, String fileName) {
        ClassLoader classLoader = obj.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return new File(resource.getPath());
    }

    @Test
    public void parseXMLAndStoreIt_findXML_returnParsed() throws IOException, ParserConfigurationException, SAXException {
        File file = getFileFromPath(this, "test.xml");
        assertThat(file, notNullValue());
        FileInputStream inputStream = new FileInputStream(file);
/*
        HandleXML obj = new HandleXML();
        obj.parseXML(inputStream);
        assertNotNull(obj.getRedditPostList());
        assertEquals(25, obj.getRedditPostList().size());*/
    }

}