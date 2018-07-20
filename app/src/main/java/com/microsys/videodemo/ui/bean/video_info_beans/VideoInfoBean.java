package com.microsys.videodemo.ui.bean.video_info_beans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class VideoInfoBean implements Serializable {

	/**
	 * gate : 0
	 * groupID :
	 * iPAddr : 10.66.6.183
	 * iPPort : 554
	 * jLZT : 0
	 * orderNO :
	 * pos_x : 118.79496169284927
	 * pos_y : 38.057619776888075
	 * puid : tel:1449
	 * userName :
	 * userPwd :
	 * videoID : 13010100001310001212
	 * videoMode : 0
	 * videoName : 勘探码头-值班室
	 */

	private int gate;
	private String groupID;
	private String iPAddr;
	private int iPPort;
	private int jLZT;
	private String orderNO;
	private String pos_x;
	private String pos_y;
	private String puid;
	private String userName;
	private String userPwd;
	private String videoID;
	private int videoMode;
	private String videoName;

	public int getGate() {
		return gate;
	}

	public void setGate(int gate) {
		this.gate = gate;
	}

	public String getGroupID() {
		return groupID == null ? "" : groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getiPAddr() {
		return iPAddr == null ? "" : iPAddr;
	}

	public void setiPAddr(String iPAddr) {
		this.iPAddr = iPAddr;
	}

	public int getiPPort() {
		return iPPort;
	}

	public void setiPPort(int iPPort) {
		this.iPPort = iPPort;
	}

	public int getjLZT() {
		return jLZT;
	}

	public void setjLZT(int jLZT) {
		this.jLZT = jLZT;
	}

	public String getOrderNO() {
		return orderNO == null ? "" : orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
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

	public String getPuid() {
		return puid == null ? "" : puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

	public String getUserName() {
		return userName == null ? "" : userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd == null ? "" : userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getVideoID() {
		return videoID == null ? "" : videoID;
	}

	public void setVideoID(String videoID) {
		this.videoID = videoID;
	}

	public int getVideoMode() {
		return videoMode;
	}

	public void setVideoMode(int videoMode) {
		this.videoMode = videoMode;
	}

	public String getVideoName() {
		try {
			videoName= URLDecoder.decode(videoName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return videoName == null ? "" : videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
}
