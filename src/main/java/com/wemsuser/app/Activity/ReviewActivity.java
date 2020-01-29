package com.wemsuser.app.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import com.wemsuser.app.Fragment.AllReviewFragment;
import com.wemsuser.app.Fragment.WriteReviewFragment;
import com.wemsuser.app.R;


public class ReviewActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    public static ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        tabLayout = findViewById(R.id.simpleTabLayout);
        viewPager = findViewById(R.id.simpleViewPager);
        tabLayout.setupWithViewPager(viewPager);

        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {
        Pager adapter = new Pager(getSupportFragmentManager());
        adapter.addFragment(new AllReviewFragment(), "  Review List  ");
        adapter.addFragment(new WriteReviewFragment(), " Write Review");
        viewPager.setAdapter(adapter);
    }




}
