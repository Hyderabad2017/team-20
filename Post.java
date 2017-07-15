package com.example.dattamber.sdhs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dattamber on 17/06/2017.
 */

public class Post {

    public String category;
    public String content;
    public Long id;
    public String title;
    public String team;
    public String url;
    public String filePath;
    public String date;
    public Long likeCount= Long.valueOf(0);
    public String published;
    public Map<String, Long> likes = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(Long pid, String category, String title, String content, String url, String team,String published, String date,String filePath) {
        this.category = category;
        this.content = content;
        this.id = pid;
        this.title = title;
        this.team = team;
        this.url = url;
        this.date = date;
        this.filePath = filePath;
        this.published = published;
        this.likes.put("dattu", (long) 1);
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("category", category);
        result.put("content", content);
        result.put("id", id);
        result.put("title", title);
        result.put("team",team);
        result.put("url",url);
        result.put("date",date);
        result.put("published",published);
        result.put("filePath",filePath);
        result.put("likes",likes);
        result.put("likeCount",likeCount);
        return result;
    }

}

