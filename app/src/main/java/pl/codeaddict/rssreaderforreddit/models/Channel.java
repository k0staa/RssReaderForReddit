package pl.codeaddict.rssreaderforreddit.models;

/**
 * Created by kostek on 26.03.17.
 */

public class Channel {
    private Long id;
    private String channelName;
    private String channelUrl;

    public Channel() {
    }

    public Channel(String channelName, String channelUrl) {
        this();
        this.channelName = channelName;
        this.channelUrl = channelUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    @Override
    public String toString() {
        return channelName;
    }
}
