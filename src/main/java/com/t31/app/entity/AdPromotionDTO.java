package com.t31.app.entity;

import java.util.Date;

public class AdPromotionDTO {
    private int id;
    private int appId;
    private String adPicPath;
    private int adPV;
    private int carouseIPosition;
    private Date startTime;
    private Date endTime;
    private int createdBy;
    private Date creationDate;
    private int modifyBy;
    private Date modifyDate;

    @Override
    public String toString() {
        return "AdPromotionDTO{" +
                "id=" + id +
                ", appId=" + appId +
                ", adPicPath='" + adPicPath + '\'' +
                ", adPV=" + adPV +
                ", carouseIPosition=" + carouseIPosition +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createdBy=" + createdBy +
                ", creationDate=" + creationDate +
                ", modifyBy=" + modifyBy +
                ", modifyDate=" + modifyDate +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAdPicPath() {
        return adPicPath;
    }

    public void setAdPicPath(String adPicPath) {
        this.adPicPath = adPicPath;
    }

    public int getAdPV() {
        return adPV;
    }

    public void setAdPV(int adPV) {
        this.adPV = adPV;
    }

    public int getCarouseIPosition() {
        return carouseIPosition;
    }

    public void setCarouseIPosition(int carouseIPosition) {
        this.carouseIPosition = carouseIPosition;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(int modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
