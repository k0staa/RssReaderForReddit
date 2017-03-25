package pl.codeaddict.rssreaderforreddit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import pl.codeaddict.rssreaderforreddit.models.UrlAdapterItem;

import static pl.codeaddict.rssreaderforreddit.RssReaderForRedditApplication.REDDIT_BASE_URL;
import static pl.codeaddict.rssreaderforreddit.RssReaderForRedditApplication.REDDIT_BASE_URL_XML;

public class AddChannelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);
        final EditText addChannellText = (EditText) findViewById(R.id.addChannellEditText);

        Button buttonAddChannell = (Button) findViewById(R.id.buttonAddChannel);
        buttonAddChannell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addChannellText != null && !addChannellText.getText().toString().isEmpty()) {
                    String channel = addChannellText.getText().toString();
                    List<UrlAdapterItem> channelSpinner = RssReaderForRedditApplication.getContext().getChannelSpinnerList();
                    channelSpinner.add(new UrlAdapterItem(channel, REDDIT_BASE_URL + channel + REDDIT_BASE_URL_XML));
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }
        });
    }
}
