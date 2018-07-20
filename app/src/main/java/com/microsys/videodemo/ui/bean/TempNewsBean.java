package com.microsys.videodemo.ui.bean;

import java.io.Serializable;

public class TempNewsBean implements Serializable {

    /**
     * uniquekey : 37050c18835b92179e7463cdf3880776
     * title : 一万赞！这就是未来中国的样子
     * date : 2018-06-01 09:25
     * category : 头条
     * author_name : 人民网app
     * url : http://mini.eastday.com/mobile/180601092505280.html
     * thumbnail_pic_s : http://02.imgmini.eastday.com/mobile/20180601/20180601092505_f9d00a8bc961e57f46c742c908777a10_13_mwpm_03200403.jpg
     * thumbnail_pic_s02 : http://02.imgmini.eastday.com/mobile/20180601/20180601092505_f9d00a8bc961e57f46c742c908777a10_8_mwpm_03200403.jpg
     * thumbnail_pic_s03 : http://02.imgmini.eastday.com/mobile/20180601/20180601092505_f9d00a8bc961e57f46c742c908777a10_5_mwpm_03200403.jpg
     */

    private String uniquekey;
    private String title;
    private String date;
    private String category;
    private String author_name;
    private String url;
    private String thumbnail_pic_s;
    private String thumbnail_pic_s02;
    private String thumbnail_pic_s03;

    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getThumbnail_pic_s02() {
        return thumbnail_pic_s02;
    }

    public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
        this.thumbnail_pic_s02 = thumbnail_pic_s02;
    }

    public String getThumbnail_pic_s03() {
        return thumbnail_pic_s03;
    }

    public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
        this.thumbnail_pic_s03 = thumbnail_pic_s03;
    }
}
