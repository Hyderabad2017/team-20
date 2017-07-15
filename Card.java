package com.example.dattamber.sdhs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dattamber on 17/06/2017.
 */

public class Card {
    public String title;
    public String category;
    public String team;
    public String published;
    public String thumbnail;
    public String path;
    public String date;
    public String url;
    public Long likeCount;
    public String content;
    public Long id;
    public String filePath;
    public Map<String,Long> likes = new HashMap<>();

    public String getfilePath() {
        return filePath;
    }

    public void setfilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getpid() {
        return id;
    }

    public void setpid(Long id) {
        this.id = id;
    }

    public Long getlikeCount() {
        return likeCount;
    }

    public Map<String,Long> getlikeslist(){
        return likes;
    }

    public void setlikeslist(Map<String,Long> likes)
    {
        this.likes = likes;
    }
    public void setlikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public String getthumbnail() {
        return thumbnail;
    }

    public void setthumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getpath() {
        return path;
    }

    public void setpath(String path) {
        this.path = path;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }


    public String getpublished() {
        return published;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String getdate() {
        return date;
    }

    public void setpublished(String published) {
        this.published = published;
    }

    public String geturl() {
        return url;
    }

    public void seturl(String url) {
        this.url = url;
    }

    public String getcategory() {
        return category;
    }

    public void setcategory(String category) {
        this.category = category;
    }

    public String getcontent() {
        return content;
    }

    public void setcontent(String content) {
        this.content = content;
    }

    public String getteam() {
        return team;
    }

    public void setteam(String team) {
        this.team = team;
    }
}
