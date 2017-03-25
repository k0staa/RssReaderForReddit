package pl.codeaddict.rssreaderforreddit;

import android.app.Application;

/**
 * Created by kostek on 2017-03-19.
 */

public class RssReaderForRedditApplication extends Application {

    private static MainActivity mContext;

    public static MainActivity getContext() {
        return mContext;
    }

    public static void setContext(MainActivity mContext) {
        RssReaderForRedditApplication.mContext = mContext;
    }


}