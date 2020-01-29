package com.wemsuser.app.Services;

import com.google.gson.JsonParser;
import com.wemsuser.app.Activity.FeedbackActivity;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.List;

public class WebServiceURL {
    private String baseURLOld="http://rsa.algosoftech.in/api/";
    private String baseURL="https://www.wemsrsa.com/api/";
    private String baseURLIP="13.58.163.63/";
    private String userLoginURL=baseURL+"userlogin";
    private String userRegistrationURL=baseURL+"user-registration";
    private String TermsConditionURL=baseURL+"terms-condition";
    private String PrivacyPolicyURL=baseURL+"privacy-policy";
    private String FaqURL=baseURL+"faq";
    private String ServiceURL=baseURL+"services";
    private String AboutUsURL=baseURL+"webView/AboutUS";
    private String VehicleTypeURL=baseURL+"vehicleType";
    private String fuelTypeURL=baseURL+"fuelType";
    private String AddVehicleURL=baseURL+"addVehicle";
    private String UserCarListURL=baseURL+"userVehicleList";
    private String DeleteCarDetailsURL=baseURL+"deleteUserVehicle";
    private String ServiceProviderListURL=baseURL+"serviceProviderList";
    private String ServiceProviderMapURL=baseURL+"serviceProviderMap";
    private String ProviderDetailListURL=baseURL+"serviceProviderDetails";
    private String ContactSebjectListURL=baseURL+"contactreasondata";
    private String ContactUsURL=baseURL+"contactdata";
    private String SendRequestURL=baseURL+"servicerequest";
    private String RatingReviewURL=baseURL+"rating-review";
    private String FeedbackQuestionURL=baseURL+"Feedback-questions";
    private String SendFeedbackURL=baseURL+"Feedback";
    private String AllRatingListURL=baseURL+"AllRatingReview";
    private String SubscriptionURL=baseURL+"subscriptionDetails";
    private String USerOrderDataURL=baseURL+"userOrderDetails";
    private String UserCallDataURL=baseURL+"UserMerchantCall";
    private String UploadUserImageURL=baseURL+"userImage";
    private String NotificationListUrl=baseURL+"userMerchantNotification";
    private String AdvertiseMentURL=baseURL+"advertisement";
    private String Forget_PasswordURL=baseURL+"user-forgotpassword";
    private String Reset_passwordURL=baseURL+"user-resetpassword";
    private String PaymentOrderSuccessURL=baseURL+"orderSuccess";
    private String PaymentDataURL=baseURL+"paymentPacakgeData";
    private String NotificationStatusURL=baseURL+"userMerchantNotificationReadStatus";
    private String UpdateReviewURL=baseURL+"updateRating";
    private String AvarageRatingURL=baseURL+"totalRating";
    private String OverallFeedbackURL = baseURL+"overallFeedback";
    private String LoginTokenDataURL = baseURL+"loggedUserTokendata";




    JSONParser jsonParser=new JSONParser();

    public JSONObject userLogin(List<NameValuePair> userData) {
        return jsonParser.getJSONFromUrl(userLoginURL,userData);
    }

    public JSONObject userRegistration(List<NameValuePair> userData) {
        return jsonParser.getJSONFromUrl(userRegistrationURL,userData);
    }

    public JSONObject TermCondition(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(TermsConditionURL,userData);
    }
    public JSONObject PrivacyPolicy(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(PrivacyPolicyURL,userData);
    }

    public JSONObject FAQ(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(FaqURL,userData);
    }

    public JSONObject AboutUs(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(AboutUsURL,userData);
    }

    public JSONObject ServiceList(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(ServiceURL,userData);
    }
    public JSONObject VehicleList(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(VehicleTypeURL,userData);
    }

    public JSONObject FuelList(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(fuelTypeURL,userData);
    }

    public JSONObject Add_VehicleList(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(AddVehicleURL,userData);
    }

    public JSONObject UserCar_List(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(UserCarListURL,userData);
    }
    public JSONObject Delete_carDetails(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(DeleteCarDetailsURL,userData);
    }

    public JSONObject Service_providerList(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(ServiceProviderListURL,userData);
    }
    public JSONObject Service_providerMap(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(ServiceProviderMapURL,userData);
    }

    public JSONObject DetailService_ProviderList(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(ProviderDetailListURL,userData);
    }

    public JSONObject ContactSubjectList(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(ContactSebjectListURL,userData);
    }

    public JSONObject ContactUs(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(ContactUsURL,userData);
    }

    public JSONObject SendRequestForService(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(SendRequestURL,userData);
    }

    public JSONObject RatingReviewService(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(RatingReviewURL,userData);
    }
    public JSONObject feedbackQuestionList(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(FeedbackQuestionURL,userData);
    }
    public JSONObject SendFeedback(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(SendFeedbackURL,userData);
    }

    public JSONObject AllRatingList(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(AllRatingListURL,userData);
    }

    public JSONObject SubscriptionList(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(SubscriptionURL,userData);

    }

    public JSONObject UserOrderData(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(USerOrderDataURL,userData);
    }

    public JSONObject UserCallData(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(UserCallDataURL,userData);
    }
    public JSONObject UploadUserProfile(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(UploadUserImageURL,userData);
    }

    public JSONObject NotificationList(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(NotificationListUrl,userData);
    }

    public JSONObject BannerAds(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(AdvertiseMentURL,userData);
    }

    public JSONObject ForgetPassword(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(Forget_PasswordURL,userData);
    }

    public JSONObject ResetPassword(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(Reset_passwordURL,userData);
    }

    public JSONObject PaymentSuccess(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(PaymentOrderSuccessURL,userData);
    }

    public JSONObject PaymentData(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(PaymentDataURL,userData);
    }
    public JSONObject NotificationData(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(NotificationStatusURL,userData);
    }

    public JSONObject UpdateReview(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(UpdateReviewURL,userData);
    }

    public JSONObject AvarageRating(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(AvarageRatingURL,userData);
    }

    public JSONObject AppFeedback(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(OverallFeedbackURL,userData);
    }

    public JSONObject LoginToken(List<NameValuePair>userData){
        return jsonParser.getJSONFromUrl(LoginTokenDataURL,userData);
    }















}
