package pl.codeaddict.rssreaderforreddit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import pl.codeaddict.rssreaderforreddit.dao.ChannelsDataSource;
import pl.codeaddict.rssreaderforreddit.models.Channel;

import static pl.codeaddict.rssreaderforreddit.RssReaderForRedditApplication.REDDIT_BASE_URL;
import static pl.codeaddict.rssreaderforreddit.RssReaderForRedditApplication.REDDIT_BASE_URL_XML;

public class AddChannelActivity extends AppCompatActivity {
    private ChannelsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);
        final EditText addChannelText = (EditText) findViewById(R.id.addChannellEditText);
        dataSource = RssReaderForRedditApplication.getContext().getDataSource();
        Button buttonAddChannel = (Button) findViewById(R.id.buttonAddChannel);
        buttonAddChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addChannelText != null && !addChannelText.getText().toString().isEmpty()) {
                    String channel = addChannelText.getText().toString();
                    List<Channel> channelSpinner = RssReaderForRedditApplication.getContext().getChannelSpinnerList();
                    Channel newChannel = new Channel(channel, REDDIT_BASE_URL + channel + REDDIT_BASE_URL_XML);
                    newChannel = dataSource.createChannel(newChannel);
                    channelSpinner.add(newChannel);
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
