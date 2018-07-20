package com.microsys.videodemo.ui.bean.video_info_beans;

import java.io.Serializable;

public class VideoGroupBean implements Serializable {
    private String groupID;
    private String groupName;
    private String parentID;
    private String remark;
    private String pos_x;
    private String pos_y;
    private String icoType;
    private String iperatorID;
    private String createDate;
    private String changeDate;
    private int JLZT;
    private String orderNO;

    public String getGroupID() {
        return groupID == null ? "" : groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
//        try {
//            groupName = URLDecoder.decode(groupName, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        return groupName == null ? "" : groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getParentID() {
        return parentID == null ? "" : parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getIcoType() {
        return icoType == null ? "" : icoType;
    }

    public void setIcoType(String icoType) {
        this.icoType = icoType;
    }

    public String getIperatorID() {
        return iperatorID == null ? "" : iperatorID;
    }

    public void setIperatorID(String iperatorID) {
        this.iperatorID = iperatorID;
    }

    public String getCreateDate() {
        return createDate == null ? "" : createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getChangeDate() {
        return changeDate == null ? "" : changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public int getJLZT() {
        return JLZT;
    }

    public void setJLZT(int JLZT) {
        this.JLZT = JLZT;
    }

    public String getOrderNO() {
        return orderNO == null ? "" : orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }
}
