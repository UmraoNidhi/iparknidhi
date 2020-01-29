package com.wemsuser.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutDatum {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("full_description")
    @Expose
    private String fullDescription;
    @SerializedName("banner_image")
    @Expose
    private String bannerImage;
    @SerializedName("banner_title")
    @Expose
    private String bannerTitle;
    @SerializedName("banner_sub_title")
    @Expose
    private String bannerSubTitle;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getBannerSubTitle() {
        return bannerSubTitle;
    }

    public void setBannerSubTitle(String bannerSubTitle) {
        this.bannerSubTitle = bannerSubTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
