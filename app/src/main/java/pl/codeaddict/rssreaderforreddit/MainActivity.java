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

public class MainActivity extends AppCompatActivity {
    private Button b1;
    private String choosenUrl = "http://tutorialspoint.com/android/sampleXML.xml";
    private HandleXML handleXML;
    private List<UrlAdapterItem> channelSpinner;
    private Spinner spinner;

    public MainActivity() {
        channelSpinner = new ArrayList<>();
        channelSpinner.add(new UrlAdapterItem("All","https://www.reddit.com/r/all/.xml" ));
        channelSpinner.add(new UrlAdapterItem("Polska", "https://www.reddit.com/r/polska/.xml" ));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RssReaderForRedditApplication.setContext(this);
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<UrlAdapterItem> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, channelSpinner);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerListener(this));

        b1 = (Button) findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleXML = new HandleXML(choosenUrl);
                handleXML.fetchXML();

                while (handleXML.parsingComplete) ;
                Log.i("Posts", "values = <<" + handleXML.getRedditPostList() + ">>");
                Intent in = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(in);
            }
        });
    }

    public List<UrlAdapterItem> getChannelSpinner() {
        return channelSpinner;
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
