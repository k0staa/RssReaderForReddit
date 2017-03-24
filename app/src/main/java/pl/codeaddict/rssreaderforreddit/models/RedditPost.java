package pl.codeaddict.rssreaderforreddit.models;

/**
 * Created by kostek on 2017-03-19.
 */

public class RedditPost {
    private String author;
    private String link;
    private String title;
    private String content;

    public RedditPost(String author, String link, String title, String content) {
        this.author = author;
        this.link = link;
        this.title = title;
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "<h2>Author: " + author +" </h2>"
                + "<a href=\"" + link + "\" >" + link + "</a>"
                + "<p>Title: " + title + "</p><br/>"
                + "<div> " + content + "</div>";
    }
}
