package com.wemsuser.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallData {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;
    @SerializedName("service_type_id")
    @Expose
    private String serviceTypeId;
    @SerializedName("creation_ip")
    @Expose
    private String creationIp;
    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @SerializedName("browse_type")
    @Expose
    private String browseType;

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

    public String getBrowseType() {
        return browseType;
    }

    public void setBrowseType(String browseType) {
        this.browseType = browseType;
    }
}
