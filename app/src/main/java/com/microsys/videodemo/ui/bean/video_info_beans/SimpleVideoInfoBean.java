package com.microsys.videodemo.ui.bean.video_info_beans;

import java.io.Serializable;

public class SimpleVideoInfoBean implements Serializable{
	private String videoID;
	private String pos_x;
	private String pos_y;

	public String getVideoID() {
		return videoID == null ? "" : videoID;
	}

	public void setVideoID(String videoID) {
		this.videoID = videoID;
	}

	public String getPos_x() {
		return pos_x == null ? "" : pos_x;
	}

	public void setPos_x(String pos_x) {
		this.pos_x = pos_x;
	}

	public String getPos_y() {
		return pos_y == null ? "" : pos_y;
	}

	public void setPos_y(String pos_y) {
		this.pos_y = pos_y;
	}
}
