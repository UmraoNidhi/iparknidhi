package com.wemsuser.app.Fragment;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.wemsuser.app.Activity.LoginActivity;
import com.wemsuser.app.Activity.ReviewActivity;
import com.wemsuser.app.Adapter.ReviewAdapter;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.AllReviewrating;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Services.Networkstate;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteReviewFragment extends Fragment {

    private Button write_Review, Submit_button;
    Dialog dialog_review;
    int id1 = 1, id2 = 2, id3 = 3, id4 = 4, id5 = 5;
    String rating5, rating4, rating3, rating2, rating1;
    private ImageView rate1, rate2, rate3, rate4, rate5,
            Image_shine,W_Rating1,W_Rating2,W_Rating3,W_Rating4,W_Rating5;
    private ProgressBar progressBar;
    private EditText editText;
    Boolean flag1 = true, flag2 = true, flag3 = true, flag4 = true, flag5 = true;
    String UserReviewData,UserId,MerchantId,ServiceTypeId,ReviewIntent,Rating_id;
    private ImageView Update_review,User_image;
    private TextView text_userName,User_review,Review_data;
    private ArrayList<AllReviewrating>AllReview=new ArrayList<>();
    private LinearLayout linearLayout;
    String Profile_image,Rating_userId,Rating_UserMessage,Creation_date,Rating_UserRating;
    String strSelectedRate="5";




    public WriteReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_review, container, false);

        UserId= PreferenceUtil.getUserId(getContext());
        MerchantId=PreferenceUtil.getMerchant_Id(getContext());
        ServiceTypeId=PreferenceUtil.getServiceId(getContext());
        Profile_image = PreferenceUtil.getUserImage(getContext());
        Log.e("UserImage",""+Profile_image);

        text_userName = view.findViewById(R.id.Text_name);
        User_review = view.findViewById(R.id.User_review);
        Review_data = view.findViewById(R.id.Review_data);
        User_image = view.findViewById(R.id.Image_user);
        write_Review = view.findViewById(R.id.Text_review);
        Update_review = view.findViewById(R.id.Update_Review);

        W_Rating1 = view.findViewById(R.id.Write_rating1);
        W_Rating2 = view.findViewById(R.id.Write_rating2);
        W_Rating3 = view.findViewById(R.id.Write_rating3);
        W_Rating4 = view.findViewById(R.id.Write_rating4);
        W_Rating5 = view.findViewById(R.id.Write_rating5);
        linearLayout = view.findViewById(R.id.Edit_Linear);

        if (Networkstate.isNetworkAvailable(getContext())){
            new AllReviewList().execute(MerchantId,ServiceTypeId);

        }else {
            Toast.makeText(getContext(),"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
        }



        String ReviewData = PreferenceUtil.getUser_Review(getContext());
        String REviewDate = PreferenceUtil.getUser_ReviewDate(getContext());
        String Review_UserName = PreferenceUtil.getUserName(getContext());
        Log.e("Details",""+ReviewData+"\n"+REviewDate+"\n"+Review_UserName);
        try {
                User_review.setText(ReviewData);
                Review_data.setText(REviewDate);
                text_userName.setText(Review_UserName);
                ShowRating();


        }catch (Exception e){
            e.printStackTrace();
        }





        progressBar = view.findViewById(R.id.Progress_bar);
        write_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowRatingDialog();


            }
        });


        Update_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowRatingDialog();
//                Toast.makeText(getContext(),"Update",Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }

    private void ShowRatingDialog() {
        dialog_review = new Dialog(getContext());
        dialog_review.setContentView(R.layout.ratereview);
        editText = dialog_review.findViewById(R.id.Edit_review);
        Submit_button = dialog_review.findViewById(R.id.Button_submit);
        dialog_review.show();
        Image_shine = dialog_review.findViewById(R.id.shine_img1);
        Animation animation1 = new TranslateAnimation(0, 320, 0, 0);
        animation1.setDuration(850);
        animation1.setRepeatMode(Animation.RESTART);
        animation1.setFillAfter(false);
        animation1.setRepeatCount(1000);
        animation1.setInterpolator(new AccelerateDecelerateInterpolator());
        Image_shine.startAnimation(animation1);
        rate1 = dialog_review.findViewById(R.id.Rating1);
        rate1.setId(id1);
        rate1.setImageResource(R.drawable.rating_fill);
        rate2 = dialog_review.findViewById(R.id.Rating2);
        rate2.setId(id2);
        rate2.setImageResource(R.drawable.rating_fill);
        rate3 = dialog_review.findViewById(R.id.Rating3);
        rate3.setImageResource(R.drawable.rating_fill);
        rate3.setId(id3);
        rate4 = dialog_review.findViewById(R.id.Rating4);
        rate4.setImageResource(R.drawable.rating_fill);
        rate4.setId(id4);
        rate5 = dialog_review.findViewById(R.id.Rating5);
        rate5.setImageResource(R.drawable.rating_fill);
        rate5.setId(id5);




        rate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidateRating();

                if (!flag1) {
                    strSelectedRate="1";
                    rate1.setImageResource(R.drawable.rating_fill);
                    flag1 = true;
                    flag2 = false;
                    flag3 = false;
                    flag4 = false;
                    flag5 = false;
                    rate2.setBackgroundResource(R.drawable.rating);
                    rate3.setBackgroundResource(R.drawable.rating);
                    rate4.setBackgroundResource(R.drawable.rating);
                    rate5.setBackgroundResource(R.drawable.rating);

                } else if (flag1) {
                    strSelectedRate="0";
                    rate1.setImageResource(R.drawable.rating);
                    flag1 = false;
                    flag2 = false;
                    flag3 = false;
                    flag4 = false;
                    flag5 = false;
                    rate2.setBackgroundResource(R.drawable.rating);
                    rate3.setBackgroundResource(R.drawable.rating);
                    rate4.setBackgroundResource(R.drawable.rating);
                    rate5.setBackgroundResource(R.drawable.rating);
                }
            }
        });
        rate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidateRating();
                if (!flag2) {
                    strSelectedRate="2";
                    rate1.setImageResource(R.drawable.rating_fill);
                    rate2.setImageResource(R.drawable.rating_fill);
                    flag1 =true;
                    flag2 = true;
                    flag3 = false;
                    flag4 = false;
                    flag5 = false;
                    rate3.setBackgroundResource(R.drawable.rating);
                    rate4.setBackgroundResource(R.drawable.rating);
                    rate5.setBackgroundResource(R.drawable.rating);

                } else if (flag2) {
                    strSelectedRate="1";
                    flag1 = true;
                    flag2 = false;
                    flag3 = false;
                    flag4 = false;
                    flag5 = false;
                    rate1.setBackgroundResource(R.drawable.rating_fill);
                    rate2.setImageResource(R.drawable.rating);
                    rate3.setBackgroundResource(R.drawable.rating);
                    rate4.setBackgroundResource(R.drawable.rating);
                    rate5.setBackgroundResource(R.drawable.rating);



                }
            }
        });
        rate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidateRating();
                if (!flag3) {
                    strSelectedRate="3";
                    rate1.setImageResource(R.drawable.rating_fill);
                    rate2.setImageResource(R.drawable.rating_fill);
                    rate3.setImageResource(R.drawable.rating_fill);
                    flag3 = true;
                    flag4 = false;
                    flag5 = false;
                    rate4.setBackgroundResource(R.drawable.rating);
                    rate5.setBackgroundResource(R.drawable.rating);

                } else if (flag3) {
                    strSelectedRate="2";

                    flag1 = true;
                    flag2 = true;
                    rate1.setBackgroundResource(R.drawable.rating_fill);
                    rate2.setBackgroundResource(R.drawable.rating_fill);
                    rate3.setImageResource(R.drawable.rating);
                    flag3 = false;
                    flag4 = false;
                    flag5 = false;
                    rate4.setBackgroundResource(R.drawable.rating);
                    rate5.setBackgroundResource(R.drawable.rating);

                }

            }
        });
        rate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidateRating();
                if (!flag4) {
                    strSelectedRate="4";
                    rate1.setImageResource(R.drawable.rating_fill);
                    rate2.setImageResource(R.drawable.rating_fill);
                    rate3.setImageResource(R.drawable.rating_fill);
                    rate4.setImageResource(R.drawable.rating_fill);
                    flag4 = true;

                    flag5 = false;
                    rate5.setBackgroundResource(R.drawable.rating);
                } else if (flag4) {
                    strSelectedRate="3";
                    flag1 = true;
                    flag2 = true;
                    flag3 = true;
                    rate1.setBackgroundResource(R.drawable.rating_fill);
                    rate2.setBackgroundResource(R.drawable.rating_fill);
                    rate3.setBackgroundResource(R.drawable.rating_fill);
                    rate4.setImageResource(R.drawable.rating);
                    rate5.setBackgroundResource(R.drawable.rating);
                    flag4 = false;
                    flag5 = false;


                }

            }
        });
        rate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidateRating();
                if (!flag5) {
                    strSelectedRate="5";
                    rating5 = String.valueOf(v.getId());
                    rate1.setImageResource(R.drawable.rating_fill);
                    rate2.setImageResource(R.drawable.rating_fill);
                    rate3.setImageResource(R.drawable.rating_fill);
                    rate4.setImageResource(R.drawable.rating_fill);
                    rate5.setImageResource(R.drawable.rating_fill);
                    flag5 = true;
                    flag4 = true;
                    flag3 =true;
                    flag2 = true;
                    flag1 = true;

                } else if (flag5) {
                    strSelectedRate="4";
                    flag1 = false;
                    flag2 =false;
                    flag3 = false;
                    flag4 = false;
                    rate1.setImageResource(R.drawable.rating);
                    rate2.setImageResource(R.drawable.rating);
                    rate3.setImageResource(R.drawable.rating);
                    rate4.setImageResource(R.drawable.rating);
                    rate5.setImageResource(R.drawable.rating);

                    flag5 = false;
                }

            }
        });
        Submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                UserReviewData = editText.getText().toString().trim();

                Log.e("ReviewEdit", "" + UserReviewData + "\n" + strSelectedRate);
                if (!UserReviewData.isEmpty()) {
                    if (!strSelectedRate.equalsIgnoreCase("0")){
                        dialog_review.dismiss();
                        new SendReview().execute(UserId, MerchantId, ServiceTypeId,strSelectedRate, UserReviewData);

                    }else {
                        Toast.makeText(getContext(), "Please Rate Us", Toast.LENGTH_LONG).show();

                    }


                } else {
                    Toast.makeText(getContext(), "Please write your review", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    private void invalidateRating() {
        rate1.setImageResource(0);
        rate2.setImageResource(0);
        rate3.setImageResource(0);
        rate4.setImageResource(0);
        rate5.setImageResource(0);



    }


    public class SendReview extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL = new WebServiceURL();
            List<NameValuePair> userData = new ArrayList<>();
            userData.add(new BasicNameValuePair("user_id", strings[0]));
            userData.add(new BasicNameValuePair("merchant_id", strings[1]));
            userData.add(new BasicNameValuePair("service_type_id", strings[2]));
            userData.add(new BasicNameValuePair("user_rating", strings[3]));
            userData.add(new BasicNameValuePair("user_review", strings[4]));
            JSONObject jsonObject = webServiceURL.RatingReviewService(userData);
            return jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            Log.e("ReviewSubmitted",""+jsonObject.toString());
            JSONObject reviewData;
            progressBar.setVisibility(View.GONE);
            try {
                if (jsonObject != null) {
                    Gson gson = new Gson();
                    ResponseClass responseClass = gson.fromJson(jsonObject.toString(), ResponseClass.class);
                    if (responseClass.getSuccess() == 1) {
                        Toast.makeText(getContext(), "Review Submitted Successfully", Toast.LENGTH_LONG).show();
                        String review = responseClass.getResult().getReviewdata().getUserReview();
                        String UserName = responseClass.getResult().getReviewdata().getUserId();
                        String Review_date = responseClass.getResult().getReviewdata().getCreationDate();
                        String Review_Rating = responseClass.getResult().getReviewdata().getUserRating();
                        String Ratingid = responseClass.getResult().getReviewdata().getRatingId();
                        String Update_date = responseClass.getResult().getReviewdata().getUpdatedDate();

                        PreferenceUtil.setUser_Review(getContext(),review);
                        PreferenceUtil.setUser_ReviewDate(getContext(),Review_date);
                        PreferenceUtil.setUser_Review_Rating(getContext(),Review_Rating);
                        PreferenceUtil.setRating_id(getContext(),Ratingid);


                        String ReviewData = PreferenceUtil.getUser_Review(getContext());
                        String REviewDate = PreferenceUtil.getUser_ReviewDate(getContext());
                        String Review_UserName = PreferenceUtil.getUserName(getContext());
                        String ReviewRating = PreferenceUtil.getUser_Review_Rating(getContext());
                        Rating_id = PreferenceUtil.getRating_id(getContext());



                        if (ReviewData!=null){
                            Submit_button.setVisibility(View.GONE);
                        }else {
                            Submit_button.setVisibility(View.VISIBLE);
                        }



                        Log.e("User_ReviewDetails",""+ReviewData+"\n"+REviewDate+"\n"+Review_UserName+"\n"+ReviewRating);
                        User_review.setText(ReviewData);
                        Review_data.setText(REviewDate);
                        text_userName.setText(toCamelCase(Review_UserName));
                        Glide.with(getContext()).load(Profile_image).into(User_image);
                        ShowRating();



                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void ShowRating() {
        String Rating_Count = PreferenceUtil.getUser_Review_Rating(getContext());
            if (Rating_Count.equalsIgnoreCase("5")){
                W_Rating1.setVisibility(View.VISIBLE);
                W_Rating2.setVisibility(View.VISIBLE);
                W_Rating3.setVisibility(View.VISIBLE);
                W_Rating4.setVisibility(View.VISIBLE);
                W_Rating5.setVisibility(View.VISIBLE);

            }else if (Rating_Count.equalsIgnoreCase("4")){
                W_Rating1.setVisibility(View.VISIBLE);
                W_Rating2.setVisibility(View.VISIBLE);
                W_Rating3.setVisibility(View.VISIBLE);
                W_Rating4.setVisibility(View.VISIBLE);
            }else if (Rating_Count.equalsIgnoreCase("3")){
                W_Rating1.setVisibility(View.VISIBLE);
                W_Rating2.setVisibility(View.VISIBLE);
                W_Rating3.setVisibility(View.VISIBLE);
            }else if (Rating_Count.equalsIgnoreCase("2")){
                W_Rating1.setVisibility(View.VISIBLE);
                W_Rating2.setVisibility(View.VISIBLE);
            }else if (Rating_Count.equalsIgnoreCase("1")){
                W_Rating1.setVisibility(View.VISIBLE);
            }
    }


    public class AllReviewList extends AsyncTask<String,String,JSONObject> {
        ArrayList<AllReviewrating>Allratinglist = new ArrayList<>();

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair> userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("merchant_id",strings[0]));
            userData.add(new BasicNameValuePair("service_type_id",strings[1]));
            JSONObject jsonObject=webServiceURL.AllRatingList(userData);
            return jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            progressBar.setVisibility(View.GONE);
            try {
                if (jsonObject!=null){
                    Log.e("RatingList",""+jsonObject.toString());
                    Gson gson=new Gson();
                    ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                    if (responseClass.getSuccess()==1){
                        String Token = responseClass.getResult().getUserLoginTokenData().getLoginToken();
                        String Login_token = PreferenceUtil.getAccessTokenFromServer(getContext());
                        Allratinglist = responseClass.getResult().getAllReviewrating();
                        for (int i=0;i<Allratinglist.size();i++){
                            Rating_userId = Allratinglist.get(i).getUserid();
                            Rating_UserMessage =  Allratinglist.get(i).getUserReview();
                            Rating_UserRating =  Allratinglist.get(i).getUserRating();
                            if (Rating_userId.equalsIgnoreCase(UserId)){
                                write_Review.setText("Update review");
                            }else {
                                write_Review.setText("Write a review");
                            }
                        }




                        if (Token.equals(Login_token)){
                            AllReview=responseClass.getResult().getAllReviewrating();
                            linearLayout.setVisibility(View.VISIBLE);
                        }else {
                            PreferenceUtil.clearPreferenceObject(getContext());
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);

                        }
                        }else {
                        linearLayout.setVisibility(View.GONE);

                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public class UpdateReview extends AsyncTask<String,String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("rating_id",strings[0]));
            userData.add(new BasicNameValuePair("user_rating",strings[1]));
            userData.add(new BasicNameValuePair("user_review",strings[2]));
            JSONObject jsonObject=webServiceURL.UpdateReview(userData);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (jsonObject!=null){
                Log.e("UpdateReview",""+jsonObject.toString());
            }
            try {
                Gson gson=new Gson();
                ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                if (responseClass.getSuccess()==1){
                    String UpdateRating=responseClass.getResult().getReviewdata().getUserRating();
                    String UpdateReview=responseClass.getResult().getReviewdata().getUserReview();
                    new AllReviewList().execute(MerchantId,ServiceTypeId);

                    Log.e("UpdateReviews",""+UpdateRating+"\n"+UpdateReview);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public static String toCamelCase(final String init) {
        if (init==null)
            return null;

        final StringBuilder ret = new StringBuilder(init.length());

        for (final String word : init.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase());
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length()==init.length()))
                ret.append(" ");
        }

        return ret.toString();
    }
}

