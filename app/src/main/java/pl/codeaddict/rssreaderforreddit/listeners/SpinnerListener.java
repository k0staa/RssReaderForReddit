package pl.codeaddict.rssreaderforreddit.listeners;

import android.view.View;
import android.widget.AdapterView;

import pl.codeaddict.rssreaderforreddit.MainActivity;
import pl.codeaddict.rssreaderforreddit.models.Channel;

/**
 * Created by kostek on 2017-03-19.
 */

public class SpinnerListener implements AdapterView.OnItemSelectedListener {
    private MainActivity mainActivity;

    public SpinnerListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Channel urlAdapterItem = (Channel) mainActivity.getSpinner().getSelectedItem();
        mainActivity.setChoosenUrl(urlAdapterItem.getChannelUrl());
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

}