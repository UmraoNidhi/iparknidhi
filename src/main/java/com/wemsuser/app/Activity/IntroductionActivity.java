package com.wemsuser.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wemsuser.app.Fragment.FirstFragment;
import com.wemsuser.app.Fragment.SecondFragment;
import com.wemsuser.app.Fragment.ThreeFragment;
import com.wemsuser.app.R;

import me.relex.circleindicator.CircleIndicator;

public class IntroductionActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TextView textView,text_signIn;
    private RelativeLayout relativeLayout;
    private float alphaValue = 1f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);


        relativeLayout=(RelativeLayout)findViewById(R.id.rel_layout);
        relativeLayout.setAlpha(alphaValue);

        textView=findViewById(R.id.skip_tv);
        text_signIn=findViewById(R.id.login_tv);
        text_signIn.setText(Html.fromHtml("Not a member yet?"+"<b>"+" Join Now "+"</b>"));
        text_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IntroductionActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        mViewPager=findViewById(R.id.pager);
        mViewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager()));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.xyv);
        indicator.setViewPager(mViewPager);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IntroductionActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public static class SimplePagerAdapter extends FragmentPagerAdapter {
        public SimplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i==0){
                return new FirstFragment();
            }else if(i==1){
                return new SecondFragment();

            }else {
                return new ThreeFragment();

            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
