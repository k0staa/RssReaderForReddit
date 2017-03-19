package pl.codeaddict.rssreaderforreddit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import pl.codeaddict.rssreaderforreddit.models.RedditPost;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ListView listView =(ListView) findViewById(R.id.listView);
        List<RedditPost> redditPosts = RssReaderForRedditApplication.getContext().getHandleXML().getRedditPostList();

        ArrayAdapter<RedditPost> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, redditPosts);

        ArrayAdapter<RedditPost> adapter2 = new ArrayAdapter<RedditPost>(SecondActivity.this, R.layout.activity_second, redditPosts)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View row;

                LayoutInflater mInflater = (LayoutInflater) SecondActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                if (null == convertView) {
                    row = mInflater.inflate(R.layout.activity_second, null);
                } else {
                    row = convertView;
                }

                TextView tv = (TextView) row.findViewById(android.R.id.text1);
                tv.setText(Html.fromHtml(getItem(position).toString()));
                //tv.setText(getItem(position));

                return row;
            }

        };

        listView.setAdapter(adapter2);
    }


}
