package com.example.androidtest.model.articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("article_id")
    @Expose
    private Integer articleId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("date_created")
    @Expose
    private DateCreated dateCreated;
    @SerializedName("last_modified")
    @Expose
    private LastModified lastModified;
    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DateCreated getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(DateCreated dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LastModified getLastModified() {
        return lastModified;
    }

    public void setLastModified(LastModified lastModified) {
        this.lastModified = lastModified;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

}