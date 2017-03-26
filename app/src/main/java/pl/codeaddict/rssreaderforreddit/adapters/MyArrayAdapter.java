package pl.codeaddict.rssreaderforreddit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pl.codeaddict.rssreaderforreddit.RssReaderForRedditApplication;
import pl.codeaddict.rssreaderforreddit.models.Channel;

/**
 * Created by kostek on 2017-03-26.
 */

public class MyArrayAdapter extends ArrayAdapter<Channel> {

    private int textViewResourceId;

    public MyArrayAdapter(Context context, int textViewResourceId, List<Channel> objects) {
        super(context, textViewResourceId, objects);
        this.textViewResourceId = textViewResourceId;

    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;
        TextView text;
        LayoutInflater mInflater = (LayoutInflater) RssReaderForRedditApplication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = mInflater.inflate(textViewResourceId, parent, false);
        } else {
            view = convertView;
        }


        text = (TextView) view;
        Channel item;

        item = getItem(position);

        text.setText(item.toString());
        if (position == 0) {
            ViewGroup.LayoutParams l = text.getLayoutParams();
            l.height = 1;
            text.setLayoutParams(l);
        } else {
            ViewGroup.LayoutParams l = text.getLayoutParams();
            //you can change height value to yours
            l.height = 96;
            text.setLayoutParams(l);
        }

        return view;
    }
}