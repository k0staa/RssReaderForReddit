package pl.codeaddict.rssreaderforreddit;

import android.app.Application;

/**
 * Created by kostek on 2017-03-19.
 */

public class RssReaderForRedditApplication extends Application {
    public static String REDDIT_BASE_URL = "https://www.reddit.com/r/";
    public static String REDDIT_BASE_URL_XML = "/.xml";
    private static MainActivity mContext;

    public static MainActivity getContext() {
        return mContext;
    }

    public static void setContext(MainActivity mContext) {
        RssReaderForRedditApplication.mContext = mContext;
    }


}