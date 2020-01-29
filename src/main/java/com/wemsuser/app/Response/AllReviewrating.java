package com.wemsuser.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllReviewrating {

    @SerializedName("user_image")
    @Expose
    private String userImage;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("merchantid")
    @Expose
    private String merchantid;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("merchant_name")
    @Expose
    private String merchantName;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("rating_id")
    @Expose
    private String ratingId;
    @SerializedName("user_rating")
    @Expose
    private String userRating;
    @SerializedName("user_review")
    @Expose
    private String userReview;
    @SerializedName("creation_date")
    @Expose
    private String creationDate;

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
