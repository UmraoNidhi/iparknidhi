package com.wemsuser.app.Response;

import android.service.autofill.UserData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Result {
    @SerializedName("vehicleData")
    @Expose
    private String vehicleData;
    @SerializedName("userData")
    @Expose
    private MyUserData userData;

    public String getVehicleData() {
        return vehicleData;
    }

    public void setVehicleData(String vehicleData) {
        this.vehicleData = vehicleData;
    }

    public MyUserData getUserData() {
        return userData;
    }

    public void setUserData(MyUserData userData) {
        this.userData = userData;
    }

    @SerializedName("termsandcondiData")
    @Expose
    private TermsandcondiDatum termsandcondiData;

    public TermsandcondiDatum getTermsandcondiData() {
        return termsandcondiData;
    }

    public void setTermsandcondiData(TermsandcondiDatum termsandcondiData) {
        this.termsandcondiData = termsandcondiData;
    }
    @SerializedName("privacypolicyData")
    @Expose
    private ArrayList<PrivacypolicyDatum> privacypolicyData = null;

    public ArrayList<PrivacypolicyDatum> getPrivacypolicyData() {
        return privacypolicyData;
    }

    public void setPrivacypolicyData(ArrayList<PrivacypolicyDatum> privacypolicyData) {
        this.privacypolicyData = privacypolicyData;
    }

    @SerializedName("faqData")
    @Expose
    private ArrayList<FaqDatum> faqData = null;

    public ArrayList<FaqDatum> getFaqData() {
        return faqData;
    }

    public void setFaqData(ArrayList<FaqDatum> faqData) {
        this.faqData = faqData;
    }

    @SerializedName("aboutData")
    @Expose
    private ArrayList<AboutDatum> aboutData = null;

    public ArrayList<AboutDatum> getAboutData() {
        return aboutData;
    }

    public void setAboutData(ArrayList<AboutDatum> aboutData) {
        this.aboutData = aboutData;
    }

    @SerializedName("serviceData")
    @Expose
    private ArrayList<ServiceDatum> serviceData = null;

    public ArrayList<ServiceDatum> getServiceData() {
        return serviceData;
    }

    public void setServiceData(ArrayList<ServiceDatum> serviceData) {
        this.serviceData = serviceData;
    }

    @SerializedName("vehicleTypeData")
    @Expose
    private ArrayList<VehicleTypeDatum> vehicleTypeData = null;

    public ArrayList<VehicleTypeDatum> getVehicleTypeData() {
        return vehicleTypeData;
    }

    public void setVehicleTypeData(ArrayList<VehicleTypeDatum> vehicleTypeData) {
        this.vehicleTypeData = vehicleTypeData;
    }

    @SerializedName("fuelTypeData")
    @Expose
    private ArrayList<FuelTypeDatum> fuelTypeData = null;

    public ArrayList<FuelTypeDatum> getFuelTypeData() {
        return fuelTypeData;
    }

    public void setFuelTypeData(ArrayList<FuelTypeDatum> fuelTypeData) {
        this.fuelTypeData = fuelTypeData;
    }

    @SerializedName("userVehicleTypeData")
    @Expose
    private ArrayList<UserVehicleTypeDatum> userVehicleTypeData = null;

    public ArrayList<UserVehicleTypeDatum> getUserVehicleTypeData() {
        return userVehicleTypeData;
    }

    public void setUserVehicleTypeData(ArrayList<UserVehicleTypeDatum> userVehicleTypeData) {
        this.userVehicleTypeData = userVehicleTypeData;
    }

    @SerializedName("userVehicleTypeList")
    @Expose
    private ArrayList<UserVehicleTypeList> userVehicleTypeList = null;

    public ArrayList<UserVehicleTypeList> getUserVehicleTypeList() {
        return userVehicleTypeList;
    }

    public void setUserVehicleTypeList(ArrayList<UserVehicleTypeList> userVehicleTypeList) {
        this.userVehicleTypeList = userVehicleTypeList;
    }

    @SerializedName("Banner")
    @Expose
    private Banner banner;
    @SerializedName("serviceProviderData")
    @Expose
    private ArrayList<ServiceProviderDatum> serviceProviderData = null;

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public ArrayList<ServiceProviderDatum> getServiceProviderData() {
        return serviceProviderData;
    }

    public void setServiceProviderData(ArrayList<ServiceProviderDatum> serviceProviderData) {
        this.serviceProviderData = serviceProviderData;
    }

    @SerializedName("serviceProviderServices")
    @Expose
    private ArrayList<ServiceProviderService> serviceProviderServices = null;
    @SerializedName("serviceProviderDetails")
    @Expose
    private ServiceProviderDetails serviceProviderDetails;
    @SerializedName("similarServiceProvider")
    @Expose
    private ArrayList<SimilarServiceProvider> similarServiceProvider = null;

    public ArrayList<ServiceProviderService> getServiceProviderServices() {
        return serviceProviderServices;
    }

    public void setServiceProviderServices(ArrayList<ServiceProviderService> serviceProviderServices) {
        this.serviceProviderServices = serviceProviderServices;
    }

    public ServiceProviderDetails getServiceProviderDetails() {
        return serviceProviderDetails;
    }

    public void setServiceProviderDetails(ServiceProviderDetails serviceProviderDetails) {
        this.serviceProviderDetails = serviceProviderDetails;
    }

    public ArrayList<SimilarServiceProvider> getSimilarServiceProvider() {
        return similarServiceProvider;
    }

    public void setSimilarServiceProvider(ArrayList<SimilarServiceProvider> similarServiceProvider) {
        this.similarServiceProvider = similarServiceProvider;
    }


    @SerializedName("serviceProviderMapData")
    @Expose
    private ArrayList<ServiceProviderMapDatum> serviceProviderMapData = null;

    public ArrayList<ServiceProviderMapDatum> getServiceProviderMapData() {
        return serviceProviderMapData;
    }

    public void setServiceProviderMapData(ArrayList<ServiceProviderMapDatum> serviceProviderMapData) {
        this.serviceProviderMapData = serviceProviderMapData;
    }

    @SerializedName("Contactreasondata")
    @Expose
    private ArrayList<Contactreasondatum> contactreasondata = null;

    public ArrayList<Contactreasondatum> getContactreasondata() {
        return contactreasondata;
    }

    public void setContactreasondata(ArrayList<Contactreasondatum> contactreasondata) {
        this.contactreasondata = contactreasondata;
    }

    @SerializedName("user_id")
    @Expose
    private String userId;
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


    @SerializedName("Feedbackquestiondata")
    @Expose
    private ArrayList<Feedbackquestiondatum> feedbackquestiondata = null;

    public ArrayList<Feedbackquestiondatum> getFeedbackquestiondata() {
        return feedbackquestiondata;
    }

    public void setFeedbackquestiondata(ArrayList<Feedbackquestiondatum> feedbackquestiondata) {
        this.feedbackquestiondata = feedbackquestiondata;
    }
    @SerializedName("feedbackdata")
    @Expose
    private Feedbackdata feedbackdata;

    public Feedbackdata getFeedbackdata() {
        return feedbackdata;
    }

    public void setFeedbackdata(Feedbackdata feedbackdata) {
        this.feedbackdata = feedbackdata;
    }

    @SerializedName("reviewdata")
    @Expose
    private Reviewdata reviewdata;

    public Reviewdata getReviewdata() {
        return reviewdata;
    }

    public void setReviewdata(Reviewdata reviewdata) {
        this.reviewdata = reviewdata;
    }

    @SerializedName("AllReviewrating")
    @Expose
    private ArrayList<AllReviewrating> allReviewrating = null;

    public ArrayList<AllReviewrating> getAllReviewrating() {
        return allReviewrating;
    }

    public void setAllReviewrating(ArrayList<AllReviewrating> allReviewrating) {
        this.allReviewrating = allReviewrating;
    }

    @SerializedName("Subscriptiondata")
    @Expose
    private ArrayList<Subscriptiondatum> subscriptiondata = null;

    public ArrayList<Subscriptiondatum> getSubscriptiondata() {
        return subscriptiondata;
    }

    public void setSubscriptiondata(ArrayList<Subscriptiondatum> subscriptiondata) {
        this.subscriptiondata = subscriptiondata;
    }

    @SerializedName("UserOrderdata")
    @Expose
    private ArrayList<UserOrderdatum> userOrderdata = null;

    public ArrayList<UserOrderdatum> getUserOrderdata() {
        return userOrderdata;
    }

    public void setUserOrderdata(ArrayList<UserOrderdatum> userOrderdata) {
        this.userOrderdata = userOrderdata;
    }


    @SerializedName("NotoificationData")
    @Expose
    private ArrayList<NotoificationDatum> notoificationData = null;

    public ArrayList<NotoificationDatum> getNotoificationData() {
        return notoificationData;
    }

    public void setNotoificationData(ArrayList<NotoificationDatum> notoificationData) {
        this.notoificationData = notoificationData;
    }

    @SerializedName("BannerData")
    @Expose
    private BannerData bannerData;

    public BannerData getBannerData() {
        return bannerData;
    }

    public void setBannerData(BannerData bannerData) {
        this.bannerData = bannerData;
    }

    @SerializedName("CallData")
    @Expose
    private Integer callData;
    @SerializedName("PaidCallData")
    @Expose
    private Integer paidCallData;

    public Integer getCallData() {
        return callData;
    }

    public void setCallData(Integer callData) {
        this.callData = callData;
    }

    public Integer getPaidCallData() {
        return paidCallData;
    }

    public void setPaidCallData(Integer paidCallData) {
        this.paidCallData = paidCallData;
    }


    @SerializedName("read_status")
    @Expose
    private String readStatus;

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    @SerializedName("TotalRating")
    @Expose
    private TotalRating totalRating;

    public TotalRating getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(TotalRating totalRating) {
        this.totalRating = totalRating;
    }

    @SerializedName("userLoginTokenData")
    @Expose
    private UserLoginTokenData userLoginTokenData;

    public UserLoginTokenData getUserLoginTokenData() {
        return userLoginTokenData;
    }

    public void setUserLoginTokenData(UserLoginTokenData userLoginTokenData) {
        this.userLoginTokenData = userLoginTokenData;
    }

}
