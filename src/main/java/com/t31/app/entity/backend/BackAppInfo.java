package com.t31.app.entity.backend;


import java.util.Date;

public class BackAppInfo {

  private long id;
  private String softwareName;
  private String apkName;
  private String supportRom;
  private String interfaceLanguage;
  private double softwareSize;
  private Date updateDate;
  private long devId;
  private String appInfo;
  private long status;
  private Date onSaleDate;
  private Date offSaleDate;
  private Integer flatformId;
  private Integer categoryLevel3;
  private long downloads;
  private Integer createdBy;
  private Date creationDate;
  private long modifyBy;
  private Date modifyDate;
  private Integer categoryLevel1;
  private Integer categoryLevel2;
  private String logoPicPath;
  private String logoLocPath;
  private Integer versionId;

  public BackAppInfo(){

  }
  public BackAppInfo(String softwareName, Integer flatformId, Integer categoryLevel1, Integer categoryLevel2, Integer categoryLevel3){
    this.softwareName=softwareName;
    this.flatformId=flatformId;
    this.categoryLevel1=categoryLevel1;
    this.categoryLevel2=categoryLevel2;
    this.categoryLevel3=categoryLevel3;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getSoftwareName() {
    return softwareName;
  }

  public void setSoftwareName(String softwareName) {
    this.softwareName = softwareName;
  }


  public String getApkName() {
    return apkName;
  }

  public void setApkName(String apkName) {
    this.apkName = apkName;
  }


  public String getSupportRom() {
    return supportRom;
  }

  public void setSupportRom(String supportRom) {
    this.supportRom = supportRom;
  }


  public String getInterfaceLanguage() {
    return interfaceLanguage;
  }

  public void setInterfaceLanguage(String interfaceLanguage) {
    this.interfaceLanguage = interfaceLanguage;
  }


  public double getSoftwareSize() {
    return softwareSize;
  }

  public void setSoftwareSize(double softwareSize) {
    this.softwareSize = softwareSize;
  }

  public long getDevId() {
    return devId;
  }

  public void setDevId(long devId) {
    this.devId = devId;
  }


  public String getAppInfo() {
    return appInfo;
  }

  public void setAppInfo(String appInfo) {
    this.appInfo = appInfo;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }

  public long getDownloads() {
    return downloads;
  }

  public void setDownloads(long downloads) {
    this.downloads = downloads;
  }

  public String getLogoPicPath() {
    return logoPicPath;
  }

  public void setLogoPicPath(String logoPicPath) {
    this.logoPicPath = logoPicPath;
  }

  public String getLogoLocPath() {
    return logoLocPath;
  }

  public void setLogoLocPath(String logoLocPath) {
    this.logoLocPath = logoLocPath;
  }

  public Integer getVersionId() {
    return versionId;
  }

  public void setVersionId(Integer versionId) {
    this.versionId = versionId;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public Date getOnSaleDate() {
    return onSaleDate;
  }

  public void setOnSaleDate(Date onSaleDate) {
    this.onSaleDate = onSaleDate;
  }

  public Date getOffSaleDate() {
    return offSaleDate;
  }

  public void setOffSaleDate(Date offSaleDate) {
    this.offSaleDate = offSaleDate;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Integer getFlatformId() {
    return flatformId;
  }

  public void setFlatformId(Integer flatformId) {
    this.flatformId = flatformId;
  }

  public Integer getCategoryLevel3() {
    return categoryLevel3;
  }

  public void setCategoryLevel3(Integer categoryLevel3) {
    this.categoryLevel3 = categoryLevel3;
  }

  public Integer getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Integer createdBy) {
    this.createdBy = createdBy;
  }

  public long getModifyBy() {
    return modifyBy;
  }

  public void setModifyBy(long modifyBy) {
    this.modifyBy = modifyBy;
  }

  public Integer getCategoryLevel1() {
    return categoryLevel1;
  }

  public void setCategoryLevel1(Integer categoryLevel1) {
    this.categoryLevel1 = categoryLevel1;
  }

  public Integer getCategoryLevel2() {
    return categoryLevel2;
  }

  public void setCategoryLevel2(Integer categoryLevel2) {
    this.categoryLevel2 = categoryLevel2;
  }
}
