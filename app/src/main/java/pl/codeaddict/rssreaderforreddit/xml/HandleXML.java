package pl.codeaddict.rssreaderforreddit.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import pl.codeaddict.rssreaderforreddit.models.RedditPost;

/**
 * Created by kostek on 18.03.17.
 */

public class HandleXML {
    private List<RedditPost> redditPostList;
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    private Thread thread;
    public volatile boolean parsingComplete = true;

    public HandleXML() {
        redditPostList = new ArrayList<>();
    }

    public HandleXML(String url) {
        this();
        this.urlString = url;
    }


    public void parseXML(InputStream inputStream) throws IOException, SAXException, ParserConfigurationException {
        String author, link, title, content;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputStream);

        // iterate through <entry> tags
        NodeList entryList = doc.getElementsByTagName("entry");
        for (int entryNo = 0; entryNo < entryList.getLength(); entryNo++) {
            Element entryNode = (Element) entryList.item(entryNo);
            Node authorNode = entryNode.getElementsByTagName("author").item(0);
            Node titleNode = entryNode.getElementsByTagName("title").item(0);
            Node linkNode = entryNode.getElementsByTagName("link").item(0);
            Node contentNode = entryNode.getElementsByTagName("content").item(0);

            author = authorNode.getFirstChild().getTextContent();
            link = linkNode.getAttributes().getNamedItem("href").getTextContent();
            title = titleNode.getTextContent();
            content = contentNode.getTextContent();
            redditPostList.add(new RedditPost(author, link, title, content));
        }
    }

    public void fetchXML() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream = conn.getInputStream();
                    parseXML(stream);
                    parsingComplete = false;
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public List<RedditPost> getRedditPostList() {
        return redditPostList;
    }
}