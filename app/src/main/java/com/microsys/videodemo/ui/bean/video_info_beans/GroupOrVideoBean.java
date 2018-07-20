package com.microsys.videodemo.ui.bean.video_info_beans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class GroupOrVideoBean implements Serializable {

    private List<GroupsBean> groups;
    private List<VideosBean> videos;

    public List<GroupsBean> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupsBean> groups) {
        this.groups = groups;
    }

    public List<VideosBean> getVideos() {
        return videos;
    }

    public void setVideos(List<VideosBean> videos) {
        this.videos = videos;
    }

    public static class GroupsBean implements Serializable{
        /**
         * groupID : 13050300412161001075
         * groupName : 娉ㄩ噰涓�1�7绔�1�7
         * jLZT : 0
         * orderNO : 1
         * parentID : 13010100002000130503
         */

        private String groupID;
        private String groupName;
        private int jLZT;
        private String orderNO;
        private String parentID;


        public String getGroupName() {
            try {
                groupName= URLDecoder.decode(groupName,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return groupName== null ? "" : groupName;
        }

        public String getGroupID() {
            return groupID == null ? "" : groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
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

        public String getParentID() {
            return parentID == null ? "" : parentID;
        }

        public void setParentID(String parentID) {
            this.parentID = parentID;
        }
    }

    public static class VideosBean implements Serializable {
        /**
         * gate : 0
         * groupID : 3401a6964e1057a28888f5fef5bfef1d
         * iPAddr : 10.67.62.186
         * iPPort : 554
         * jLZT : 0
         * orderNO : 1
         * pos_x :
         * pos_y :
         * puid : PSIA/streaming/channels/101
         * userName : admin
         * userPwd : admin12345
         * videoID : fa9ba076f8ba53e49aaa2ba294dacddc
         * videoMode : 0
         * videoName : IPdome
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
                videoName=URLDecoder.decode(videoName,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return videoName==null?" ":videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }
    }
}
