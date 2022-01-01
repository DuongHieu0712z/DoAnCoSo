package com.ctk43.doancoso.Model;

import java.security.PrivateKey;

public class Setting {
    private String Title;
    private String Content;
    private int Images;

    public Setting(String title, String content, int images) {
        Title = title;
        Content = content;
        Images = images;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getImages() {
        return Images;
    }

    public void setImages(int images) {
        Images = images;
    }
}
