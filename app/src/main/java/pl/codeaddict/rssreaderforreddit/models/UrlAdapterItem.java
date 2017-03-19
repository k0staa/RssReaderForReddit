package pl.codeaddict.rssreaderforreddit.models;

/**
 * Created by kostek on 2017-03-19.
 */

public class UrlAdapterItem {
    private String name;
    private String url;

    public UrlAdapterItem(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return name;
    }
}
