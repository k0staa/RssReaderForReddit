package pl.codeaddict.rssreaderforreddit.xml;

import org.junit.Test;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by kostek on 18.03.17.
 */
public class HandleXMLTest {

    private static File getFileFromPath(Object obj, String fileName) {
        ClassLoader classLoader = obj.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return new File(resource.getPath());
    }

    @Test
    public void parseXMLAndStoreIt_findXML_returnParsed() throws IOException, XmlPullParserException {
        File file = getFileFromPath(this, "test.xml");
        assertThat(file, notNullValue());
        FileInputStream inputStream = new FileInputStream(file);

                XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
        XmlPullParser parser = xmlFactoryObject.newPullParser();

        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(inputStream, null);

        HandleXML obj = new HandleXML();
        obj.parseXMLAndStoreIt(parser);

        while (obj.parsingComplete) ;
        //(obj.getTitle());
        //obj.getLink());
        //obj.getDescription());
    }
}