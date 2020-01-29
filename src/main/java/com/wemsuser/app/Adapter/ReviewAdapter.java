package com.wemsuser.app.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.wemsuser.app.Activity.ReviewActivity;
import com.wemsuser.app.R;
import com.wemsuser.app.Response.AllReviewrating;
import com.wemsuser.app.Response.ResponseClass;
import com.wemsuser.app.Services.WebServiceURL;
import com.wemsuser.app.utility.PreferenceUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<AllReviewrating>mReview;
    private ImageView rate1,rate2,rate3,rate4,rate5,Image_shine;
    Dialog dialog_review;
    private EditText editText;
    private Button button;
    Boolean flag1=false,flag2=false,flag3=false,flag4=false,flag5=false;
    int id1=1,id2=2,id3=3,id4=4,id5=5;
    String Updaterating5,Updaterating4,Updaterating3,Updaterating2,Updaterating1;
    String UserReviewData,UserId,RatingId,MerchantId,ServiceId;
    private ArrayList<AllReviewrating>AllReview=new ArrayList<>();





    public ReviewAdapter(ReviewActivity reviewActivity, ArrayList<AllReviewrating> reviewList) {
        this.context=reviewActivity;
        this.mReview=reviewList;
    }

    public ReviewAdapter(Context context, ArrayList<AllReviewrating> allReview) {
        this.mReview=allReview;
        this.context=context;
    }

    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reviewxml,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder( ReviewAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(toCamelCase(mReview.get(i).getUserName()));
        myViewHolder.text_review.setText(mReview.get(i).getUserReview());
        myViewHolder.textDate.setText(mReview.get(i).getCreationDate());
        String User_Image = mReview.get(i).getUserImage();
        Log.e("FeedbackImage",""+User_Image);

        try {
            if (User_Image!=null){
                String newImage = User_Image.replace("/thumb","");
                Glide.with(context).load(newImage).into(myViewHolder.UserImage);

            }else {
                myViewHolder.UserImage.setBackgroundResource(R.drawable.user);
            }

        }catch (Exception e){
            e.printStackTrace();
        }



        String Rating=mReview.get(i).getUserRating();
        if (Rating.equals("5.00")){
            myViewHolder.rating1.setBackgroundResource(R.drawable.rating_fill);
            myViewHolder.rating2.setBackgroundResource(R.drawable.rating_fill);
            myViewHolder.rating3.setBackgroundResource(R.drawable.rating_fill);
            myViewHolder.rating4.setBackgroundResource(R.drawable.rating_fill);
            myViewHolder.rating5.setBackgroundResource(R.drawable.rating_fill);
        }else if (Rating.equals("4.00")){
            myViewHolder.rating1.setBackgroundResource(R.drawable.rating_fill);
            myViewHolder.rating2.setBackgroundResource(R.drawable.rating_fill);
            myViewHolder.rating3.setBackgroundResource(R.drawable.rating_fill);
            myViewHolder.rating4.setBackgroundResource(R.drawable.rating_fill);
        }else if (Rating.equals("3.00")){
            myViewHolder.rating1.setBackgroundResource(R.drawable.rating_fill);
            myViewHolder.rating2.setBackgroundResource(R.drawable.rating_fill);
            myViewHolder.rating3.setBackgroundResource(R.drawable.rating_fill);

        }else if (Rating.equals("2.00")){
            myViewHolder.rating1.setBackgroundResource(R.drawable.rating_fill);
            myViewHolder.rating2.setBackgroundResource(R.drawable.rating_fill);

        }else if (Rating.equals("1.00")){
            myViewHolder.rating1.setBackgroundResource(R.drawable.rating_fill);
        }

    }

    @Override
    public int getItemCount() {
        return mReview.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView,text_review,textDate;
        ImageView UpdateReview;
        CircleImageView UserImage;
        ImageView rating1,rating2,rating3,rating4,rating5;

        public MyViewHolder( View itemView) {
            super(itemView);

            UserId= PreferenceUtil.getUserId(context);
            MerchantId=PreferenceUtil.getMerchant_Id(context);
            ServiceId=PreferenceUtil.getServiceId(context);

            textView=itemView.findViewById(R.id.Text_name);
            UserImage = itemView.findViewById(R.id.User_Image);
            text_review=itemView.findViewById(R.id.Text_review);
            textDate=itemView.findViewById(R.id.Text_data);
            rating1=itemView.findViewById(R.id.Image_rating1);
            rating2=itemView.findViewById(R.id.Image_rating2);
            rating3=itemView.findViewById(R.id.Image_rating3);
            rating4=itemView.findViewById(R.id.Image_rating4);
            rating5=itemView.findViewById(R.id.Image_rating5);
            UpdateReview=itemView.findViewById(R.id.Update_Review);

            UpdateReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RatingId=mReview.get(getLayoutPosition()).getRatingId();
                    dialog_review = new Dialog(context);
                    dialog_review.setContentView(R.layout.ratereview);
                    editText = dialog_review.findViewById(R.id.Edit_review);
                    button = dialog_review.findViewById(R.id.Button_submit);
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
                    rate2 = dialog_review.findViewById(R.id.Rating2);
                    rate3 = dialog_review.findViewById(R.id.Rating3);
                    rate4 = dialog_review.findViewById(R.id.Rating4);
                    rate5 = dialog_review.findViewById(R.id.Rating5);
                    rate1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!flag1) {
                                rate1.setImageResource(R.drawable.rating_fill);
                                flag1 = true;
                                rate1.setId(id1);
                            } else if (flag1) {
                                rate1.setImageResource(R.drawable.rating);
                                flag1 = false;
                            }
                        }
                    });
                    rate2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!flag2) {
                                rate1.setImageResource(R.drawable.rating_fill);
                                rate2.setImageResource(R.drawable.rating_fill);
                                flag2 = true;
                                rate2.setId(id2);
                            } else if (flag2) {
                                rate2.setImageResource(R.drawable.rating);
                                flag2 = false;

                            }
                        }
                    });
                    rate3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!flag3) {
                                rate1.setImageResource(R.drawable.rating_fill);
                                rate2.setImageResource(R.drawable.rating_fill);
                                rate3.setImageResource(R.drawable.rating_fill);
                                flag3 = true;
                                rate3.setId(id3);
                            } else if (flag3) {
                                rate3.setImageResource(R.drawable.rating);
                                flag3 = false;
                            }

                        }
                    });
                    rate4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!flag4) {
                                rate1.setImageResource(R.drawable.rating_fill);
                                rate2.setImageResource(R.drawable.rating_fill);
                                rate3.setImageResource(R.drawable.rating_fill);
                                rate4.setImageResource(R.drawable.rating_fill);
                                flag4 = true;
                                rate4.setId(id4);
                            } else if (flag4) {
                                rate4.setImageResource(R.drawable.rating);
                                flag4 = false;

                            }

                        }
                    });
                    rate5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!flag5) {
                                Updaterating5 = String.valueOf(v.getId());
                                rate1.setImageResource(R.drawable.rating_fill);
                                rate2.setImageResource(R.drawable.rating_fill);
                                rate3.setImageResource(R.drawable.rating_fill);
                                rate4.setImageResource(R.drawable.rating_fill);
                                rate5.setImageResource(R.drawable.rating_fill);
                                flag5 = true;
                                rate5.setId(id5);
                            } else if (flag5) {
                                rate5.setImageResource(R.drawable.rating);
                                flag5 = false;
                            }

                        }
                    });
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UserReviewData = editText.getText().toString().trim();

                            Log.e("ReviewEdit", "" + UserReviewData + "\n" + rating4);
                            if (!UserReviewData.isEmpty()) {
                                dialog_review.dismiss();
                                Updaterating5 = String.valueOf(rate5.getId());
                                Updaterating4 = String.valueOf(rate4.getId());
                                Updaterating3 = String.valueOf(rate3.getId());
                                Updaterating2 = String.valueOf(rate2.getId());
                                Updaterating1 = String.valueOf(rate1.getId());
                                if (Updaterating5.equalsIgnoreCase("5")) {
                                    new UpdateReview().execute(RatingId,  Updaterating5, UserReviewData);
                                } else if (Updaterating4.equalsIgnoreCase("4")) {
                                    new UpdateReview().execute(RatingId,  Updaterating4, UserReviewData);
                                } else if (Updaterating3.equalsIgnoreCase("3")) {
                                    new UpdateReview().execute(RatingId, Updaterating3, UserReviewData);
                                } else if (Updaterating2.equalsIgnoreCase("2")) {
                                    new UpdateReview().execute(RatingId,  Updaterating2, UserReviewData);
                                } else if (Updaterating1.equalsIgnoreCase("1")) {
                                    new UpdateReview().execute(RatingId, Updaterating1, UserReviewData);

                                }

                            } else {
                                Toast.makeText(context, "Please write your review", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }
            });

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
                    new AllRatingList().execute(MerchantId,ServiceId);

                    Log.e("UpdateReviews",""+UpdateRating+"\n"+UpdateReview);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class AllRatingList extends AsyncTask<String,String,JSONObject>{
        @Override
        protected JSONObject doInBackground(String... strings) {
            WebServiceURL webServiceURL=new WebServiceURL();
            List<NameValuePair>userData=new ArrayList<>();
            userData.add(new BasicNameValuePair("merchant_id",strings[0]));
            userData.add(new BasicNameValuePair("service_type_id",strings[1]));
            JSONObject jsonObject=webServiceURL.AllRatingList(userData);

            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (jsonObject!=null){
                Log.e("AllReview",""+jsonObject.toString());
            }
            try {
                Gson gson=new Gson();
                ResponseClass responseClass=gson.fromJson(jsonObject.toString(),ResponseClass.class);
                if (responseClass.getSuccess()==1){
                    mReview =responseClass.getResult().getAllReviewrating();
                    new ReviewAdapter(context,mReview);
                    notifyDataSetChanged();
//                    for (int i=0;i<AllReview.size();i++){
//                        String Review=AllReview.get(i).getUserReview();
//                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static String toCamelCase(final String init) {
        if (init == null)
            return null;

        final StringBuilder ret = new StringBuilder(init.length());

        for (final String word : init.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase());
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length() == init.length()))
                ret.append(" ");
        }

        return ret.toString();
    }
}
