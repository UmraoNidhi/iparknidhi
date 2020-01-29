package com.wemsuser.app.Fragment;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wemsuser.app.Activity.HomeActivity;
import com.wemsuser.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowUsFragment extends Fragment {
    private ImageView imageInsta,imageFacebook,imageTwitter,ImageBanner;
    LinearLayout facebook,instagram,twitter;



    public FollowUsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeActivity.fab.findViewById(R.id.fab_button).setVisibility(View.GONE);
        HomeActivity.mMenu.findItem(R.id.action_Map).setVisible(false);
        HomeActivity.mMenu.findItem(R.id.action_List).setVisible(false);
        HomeActivity.mMenu.findItem(R.id.Refresh).setVisible(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_follow_us, container, false);

        imageFacebook=view.findViewById(R.id.ImageView1);
        imageInsta=view.findViewById(R.id.ImageView2);
        imageTwitter=view.findViewById(R.id.ImageView3);

        facebook=view.findViewById(R.id.Linear_facebook);
        instagram=view.findViewById(R.id.Linear_insta);
        twitter=view.findViewById(R.id.Linear_twitter);
        ImageBanner=view.findViewById(R.id.image_banner);

        imageInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySelectedLogin(instagram,facebook,twitter);
                Intent insta= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/wemsrsa/"));
                startActivity(insta);
            }
        });

        imageFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySelectedLogin(facebook,instagram,twitter);
                Intent facebook= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/wemssrsa/"));
                startActivity(facebook);
            }
        });

        imageTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySelectedLogin(twitter,facebook,instagram);
                Intent twitter= new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/wemsrsa"));
                startActivity(twitter);
            }
        });

        ImageBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebook= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wemsrsa.com/"));
                startActivity(facebook);
            }
        });




        return view;



    }

    private void displaySelectedLogin(LinearLayout Selected, LinearLayout Unselected,
                                      LinearLayout Unselected1) {

        Selected.setBackgroundColor(Color.parseColor("#FFC107"));
        Unselected.setBackgroundColor(Color.parseColor("#ffffff"));
        Unselected1.setBackgroundColor(Color.parseColor("#ffffff"));


    }

    private void SetBackgroundLogo(ImageView selected){
        selected.setBackgroundResource(R.drawable.whiteface);
        selected.setBackgroundResource(R.drawable.whitetwitter);
        selected.setBackgroundResource(R.drawable.insta2);
    }


}
