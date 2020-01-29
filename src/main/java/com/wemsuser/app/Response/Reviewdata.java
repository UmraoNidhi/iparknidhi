package com.wemsuser.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reviewdata {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("rating_id")
    @Expose
    private String ratingId;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;
    @SerializedName("service_type_id")
    @Expose
    private String serviceTypeId;
    @SerializedName("user_rating")
    @Expose
    private String userRating;
    @SerializedName("user_review")
    @Expose
    private String userReview;
    @SerializedName("creation_ip")
    @Expose
    private String creationIp;
    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("browse_type")
    @Expose
    private String browseType;

    @SerializedName("updated_ip")
    @Expose
    private String updatedIp;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
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

    public String getCreationIp() {
        return creationIp;
    }

    public void setCreationIp(String creationIp) {
        this.creationIp = creationIp;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBrowseType() {
        return browseType;
    }

    public void setBrowseType(String browseType) {
        this.browseType = browseType;
    }

    public String getUpdatedIp() {
        return updatedIp;
    }

    public void setUpdatedIp(String updatedIp) {
        this.updatedIp = updatedIp;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }
}
