package pl.codeaddict.rssreaderforreddit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import pl.codeaddict.rssreaderforreddit.adapters.MyArrayAdapter;
import pl.codeaddict.rssreaderforreddit.dao.ChannelsDataSource;
import pl.codeaddict.rssreaderforreddit.listeners.SpinnerListener;
import pl.codeaddict.rssreaderforreddit.models.Channel;
import pl.codeaddict.rssreaderforreddit.models.RedditPost;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private static Channel DEFAULT_CHANNEL;
    private static int MAX_CHANNELS = 50;
    private Button buttonFetch, buttonAddChannelView, buttonDelete;
    private String choosenUrl = "";
    private List<Channel> channelSpinnerList;
    private Spinner spinner;
    private ChannelsDataSource dataSource;
    private List<RedditPost> redditPosts;

    public MainActivity() {
        channelSpinnerList = new ArrayList<>();
        DEFAULT_CHANNEL = new Channel(" ", " ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new ChannelsDataSource(this);
        dataSource.open();
        channelSpinnerList.addAll(dataSource.getAllChannel());
        if(channelSpinnerList.isEmpty()){
            DEFAULT_CHANNEL = dataSource.createChannel(DEFAULT_CHANNEL);
            channelSpinnerList.add(DEFAULT_CHANNEL);
        }
        setContentView(R.layout.activity_main);
        RssReaderForRedditApplication.setContext(this);
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<Channel> adapter = new MyArrayAdapter(this,
                android.R.layout.simple_spinner_item, channelSpinnerList);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerListener(this));

        buttonFetch = (Button) findViewById(R.id.fetchButton);
        buttonFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItem() == null || spinner.getSelectedItem().equals(DEFAULT_CHANNEL)) {
                    return;
                }
               new ParseXMLTask().execute(choosenUrl);

            }
        });

        buttonAddChannelView = (Button) findViewById(R.id.addChannelViewbutton);
        buttonAddChannelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(channelSpinnerList.size() >= MAX_CHANNELS){
                    showExceptionAlert("There is a problem", "You exceed maximum amount of channels");

                }
                Intent intent = new Intent(MainActivity.this, AddChannelActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        buttonDelete = (Button) findViewById(R.id.deleteButton);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinner.getSelectedItem() == null || spinner.getSelectedItem().equals(DEFAULT_CHANNEL)) {
                    return;
                }
                Channel channelToDelete = (Channel) spinner.getSelectedItem();
                dataSource.deleteChannel(channelToDelete);
                channelSpinnerList.remove(channelToDelete);
                spinner.setSelection(0);
            }
        });
    }

    private void showExceptionAlert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public List<Channel> getChannelSpinnerList() {
        return channelSpinnerList;
    }

    public String getChoosenUrl() {
        return choosenUrl;
    }

    public void setChoosenUrl(String choosenUrl) {
        this.choosenUrl = choosenUrl;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public ChannelsDataSource getDataSource() {
        return dataSource;
    }

    public List<RedditPost> getRedditPosts() {
        return redditPosts;
    }

    private class ParseXMLTask extends AsyncTask<String, Integer, AsyncTaskResult<List<RedditPost>>> {

        @Override
        protected AsyncTaskResult<List<RedditPost>> doInBackground(String... urlString) {
            AsyncTaskResult<List<RedditPost>> result;
            try {
                URL url = new URL(urlString[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream stream = conn.getInputStream();
                result = new AsyncTaskResult<>(parseXML(stream));
                stream.close();
            } catch (Exception e) {
                Log.e("Parsing exception", "Problem with connection during XML parse");
                result = new AsyncTaskResult<>(e);
            }
            return result;
        }

        @Override
        protected void onCancelled(AsyncTaskResult<List<RedditPost>> listAsyncTaskResult) {
            super.onCancelled(listAsyncTaskResult);
        }

        @Override
        protected void onPostExecute(AsyncTaskResult<List<RedditPost>> result) {
            if (result.getError() != null) {
                showExceptionAlert("There is a problem", "Channel fetching problem. Is channel really exist ?");
            } else if (isCancelled()) {
                // cancel handling here
            } else {
                redditPosts = result.getResult();
                Intent in = new Intent(MainActivity.this, ViewPostsActivity.class);
                startActivity(in);
                // result handling here
            }
        }

        private List<RedditPost> parseXML(InputStream inputStream) throws IOException, SAXException, ParserConfigurationException {
            List<RedditPost> redditPostsParsed = new ArrayList<>();
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

                author = authorNode.getFirstChild().getTextContent().substring(3); //first 3 letters are /u/
                link = linkNode.getAttributes().getNamedItem("href").getTextContent();
                title = titleNode.getTextContent();
                content = contentNode.getTextContent();
                redditPostsParsed.add(new RedditPost(author, link, title, content));
            }
            return redditPostsParsed;
        }

    }

    private class AsyncTaskResult<T> {
        private T result;
        private Exception error;

        public T getResult() {
            return result;
        }

        public Exception getError() {
            return error;
        }

        public AsyncTaskResult(T result) {
            super();
            this.result = result;
        }

        public AsyncTaskResult(Exception error) {
            super();
            this.error = error;
        }
    }

}
