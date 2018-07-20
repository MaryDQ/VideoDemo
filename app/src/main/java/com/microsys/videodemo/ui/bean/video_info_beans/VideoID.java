package com.microsys.videodemo.ui.bean.video_info_beans;

public class VideoID {
    private String videoID;

    public VideoID(String videoId) {
        this.videoID = videoId;
    }

    public String getVideoId() {
        return videoID == null ? "" : videoID;
    }

    public void setVideoId(String videoId) {
        this.videoID = videoId;
    }
}
