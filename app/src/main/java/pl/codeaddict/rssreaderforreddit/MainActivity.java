package pl.codeaddict.rssreaderforreddit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pl.codeaddict.rssreaderforreddit.adapters.MyArrayAdapter;
import pl.codeaddict.rssreaderforreddit.dao.ChannelsDataSource;
import pl.codeaddict.rssreaderforreddit.listeners.SpinnerListener;
import pl.codeaddict.rssreaderforreddit.models.Channel;
import pl.codeaddict.rssreaderforreddit.xml.HandleXML;

import static pl.codeaddict.rssreaderforreddit.RssReaderForRedditApplication.REDDIT_BASE_URL;
import static pl.codeaddict.rssreaderforreddit.RssReaderForRedditApplication.REDDIT_BASE_URL_XML;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private static Channel DEFAULT_CHANNEL;
    private Button buttonFetch, buttonChannelView, buttonDelete;
    private String choosenUrl = "";
    private HandleXML handleXML;
    private List<Channel> channelSpinnerList;
    private Spinner spinner;
    private ChannelsDataSource dataSource;

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

    public HandleXML getHandleXML() {
        return handleXML;
    }

    public ChannelsDataSource getDataSource() {
        return dataSource;
    }
}
