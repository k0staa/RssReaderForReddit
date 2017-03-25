package pl.codeaddict.rssreaderforreddit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pl.codeaddict.rssreaderforreddit.listeners.SpinnerListener;
import pl.codeaddict.rssreaderforreddit.models.UrlAdapterItem;
import pl.codeaddict.rssreaderforreddit.xml.HandleXML;

import static pl.codeaddict.rssreaderforreddit.RssReaderForRedditApplication.REDDIT_BASE_URL;
import static pl.codeaddict.rssreaderforreddit.RssReaderForRedditApplication.REDDIT_BASE_URL_XML;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private Button buttonFetch, buttonChannelView;
    private String choosenUrl = "";
    private HandleXML handleXML;
    private List<UrlAdapterItem> channelSpinnerList;
    private Spinner spinner;

    public MainActivity() {
        channelSpinnerList = new ArrayList<>();
        channelSpinnerList.add(new UrlAdapterItem("All", REDDIT_BASE_URL + "all" + REDDIT_BASE_URL_XML));
        channelSpinnerList.add(new UrlAdapterItem("Polska", REDDIT_BASE_URL + "polska" + REDDIT_BASE_URL_XML));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RssReaderForRedditApplication.setContext(this);
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<UrlAdapterItem> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, channelSpinnerList);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerListener(this));

        buttonFetch = (Button) findViewById(R.id.fetchButton);
        buttonFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItem() == null) {
                    return;
                }
                handleXML = new HandleXML(choosenUrl);
                handleXML.fetchXML();

                while (handleXML.parsingComplete) ;
                Intent in = new Intent(MainActivity.this, ViewPostsActivity.class);
                startActivity(in);
            }
        });

        buttonChannelView = (Button) findViewById(R.id.addChannelViewbutton);
        buttonChannelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddChannelActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    public List<UrlAdapterItem> getChannelSpinnerList() {
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

    public HandleXML getHandleXML() {
        return handleXML;
    }
}
