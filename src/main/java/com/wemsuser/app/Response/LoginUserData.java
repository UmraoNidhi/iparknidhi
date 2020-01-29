package com.wemsuser.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginUserData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("user_title")
    @Expose
    private String userTitle;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_email_otp")
    @Expose
    private String userEmailOtp;
    @SerializedName("user_email_verify")
    @Expose
    private String userEmailVerify;
    @SerializedName("user_phone")
    @Expose
    private String userPhone;
    @SerializedName("user_phone_otp")
    @Expose
    private String userPhoneOtp;
    @SerializedName("user_phone_verify")
    @Expose
    private String userPhoneVerify;
    @SerializedName("user_phone1")
    @Expose
    private Object userPhone1;
    @SerializedName("user_password")
    @Expose
    private String userPassword;
    @SerializedName("user_password_otp")
    @Expose
    private String userPasswordOtp;
    @SerializedName("user_token")
    @Expose
    private String userToken;
    @SerializedName("user_image")
    @Expose
    private String userImage;
    @SerializedName("user_image1")
    @Expose
    private String userImage1;
    @SerializedName("user_image2")
    @Expose
    private String userImage2;
    @SerializedName("user_image3")
    @Expose
    private String userImage3;
    @SerializedName("user_address")
    @Expose
    private String userAddress;
    @SerializedName("user_city")
    @Expose
    private String userCity;
    @SerializedName("user_state")
    @Expose
    private String userState;
    @SerializedName("user_country")
    @Expose
    private String userCountry;
    @SerializedName("user_pincode")
    @Expose
    private String userPincode;
    @SerializedName("department_id")
    @Expose
    private Object departmentId;
    @SerializedName("designation_id")
    @Expose
    private Object designationId;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user_label")
    @Expose
    private String userLabel;
    @SerializedName("company_id")
    @Expose
    private Object companyId;
    @SerializedName("user_logins")
    @Expose
    private String userLogins;
    @SerializedName("user_from")
    @Expose
    private String userFrom;
    @SerializedName("network_id")
    @Expose
    private String networkId;
    @SerializedName("browse_type")
    @Expose
    private String browseType;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("area_covered_from")
    @Expose
    private String areaCoveredFrom;
    @SerializedName("area_covered_to")
    @Expose
    private Object areaCoveredTo;
    @SerializedName("website_url")
    @Expose
    private Object websiteUrl;
    @SerializedName("forgot_password_otp")
    @Expose
    private String forgotPasswordOtp;
    @SerializedName("last_login_ip")
    @Expose
    private String lastLoginIp;
    @SerializedName("last_login_date")
    @Expose
    private String lastLoginDate;
    @SerializedName("last_login_lat")
    @Expose
    private String lastLoginLat;
    @SerializedName("last_login_long")
    @Expose
    private String lastLoginLong;
    @SerializedName("creation_ip")
    @Expose
    private String creationIp;
    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @SerializedName("creation_lat")
    @Expose
    private String creationLat;
    @SerializedName("creation_long")
    @Expose
    private String creationLong;
    @SerializedName("created_by")
    @Expose
    private Object createdBy;
    @SerializedName("update_ip")
    @Expose
    private String updateIp;
    @SerializedName("update_date")
    @Expose
    private String updateDate;
    @SerializedName("update_lat")
    @Expose
    private String updateLat;
    @SerializedName("update_long")
    @Expose
    private String updateLong;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmailOtp() {
        return userEmailOtp;
    }

    public void setUserEmailOtp(String userEmailOtp) {
        this.userEmailOtp = userEmailOtp;
    }

    public String getUserEmailVerify() {
        return userEmailVerify;
    }

    public void setUserEmailVerify(String userEmailVerify) {
        this.userEmailVerify = userEmailVerify;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPhoneOtp() {
        return userPhoneOtp;
    }

    public void setUserPhoneOtp(String userPhoneOtp) {
        this.userPhoneOtp = userPhoneOtp;
    }

    public String getUserPhoneVerify() {
        return userPhoneVerify;
    }

    public void setUserPhoneVerify(String userPhoneVerify) {
        this.userPhoneVerify = userPhoneVerify;
    }

    public Object getUserPhone1() {
        return userPhone1;
    }

    public void setUserPhone1(Object userPhone1) {
        this.userPhone1 = userPhone1;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPasswordOtp() {
        return userPasswordOtp;
    }

    public void setUserPasswordOtp(String userPasswordOtp) {
        this.userPasswordOtp = userPasswordOtp;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserImage1() {
        return userImage1;
    }

    public void setUserImage1(String userImage1) {
        this.userImage1 = userImage1;
    }

    public String getUserImage2() {
        return userImage2;
    }

    public void setUserImage2(String userImage2) {
        this.userImage2 = userImage2;
    }

    public String getUserImage3() {
        return userImage3;
    }

    public void setUserImage3(String userImage3) {
        this.userImage3 = userImage3;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserPincode() {
        return userPincode;
    }

    public void setUserPincode(String userPincode) {
        this.userPincode = userPincode;
    }

    public Object getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Object departmentId) {
        this.departmentId = departmentId;
    }

    public Object getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Object designationId) {
        this.designationId = designationId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }

    public Object getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Object companyId) {
        this.companyId = companyId;
    }

    public String getUserLogins() {
        return userLogins;
    }

    public void setUserLogins(String userLogins) {
        this.userLogins = userLogins;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getBrowseType() {
        return browseType;
    }

    public void setBrowseType(String browseType) {
        this.browseType = browseType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAreaCoveredFrom() {
        return areaCoveredFrom;
    }

    public void setAreaCoveredFrom(String areaCoveredFrom) {
        this.areaCoveredFrom = areaCoveredFrom;
    }

    public Object getAreaCoveredTo() {
        return areaCoveredTo;
    }

    public void setAreaCoveredTo(Object areaCoveredTo) {
        this.areaCoveredTo = areaCoveredTo;
    }

    public Object getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(Object websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getForgotPasswordOtp() {
        return forgotPasswordOtp;
    }

    public void setForgotPasswordOtp(String forgotPasswordOtp) {
        this.forgotPasswordOtp = forgotPasswordOtp;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginLat() {
        return lastLoginLat;
    }

    public void setLastLoginLat(String lastLoginLat) {
        this.lastLoginLat = lastLoginLat;
    }

    public String getLastLoginLong() {
        return lastLoginLong;
    }

    public void setLastLoginLong(String lastLoginLong) {
        this.lastLoginLong = lastLoginLong;
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

    public String getCreationLat() {
        return creationLat;
    }

    public void setCreationLat(String creationLat) {
        this.creationLat = creationLat;
    }

    public String getCreationLong() {
        return creationLong;
    }

    public void setCreationLong(String creationLong) {
        this.creationLong = creationLong;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateLat() {
        return updateLat;
    }

    public void setUpdateLat(String updateLat) {
        this.updateLat = updateLat;
    }

    public String getUpdateLong() {
        return updateLong;
    }

    public void setUpdateLong(String updateLong) {
        this.updateLong = updateLong;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
