package akilliyazilim.justhoy.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.Session;

import akilliyazilim.justhoy.R;
import akilliyazilim.justhoy.fragments.GuideFragment;
import cn.taurusxi.guidebackgroundcoloranimation.library.ColorAnimationView;


public class GuideActivity extends FragmentActivity implements View.OnClickListener{

    private static final int[] resource = new int[]{R.drawable.icon_hoy_white, R.drawable.icon_hoy_purple};
    private static final String TAG = GuideActivity.class.getSimpleName();
    public static Dialog progressDialog;
    FrameLayout frame_guide;
    MyFragmentStatePager adpter;
    public static ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        frame_guide = (FrameLayout) findViewById(R.id.frame_guide);
        frame_guide.setOnClickListener(this);
        adpter = new MyFragmentStatePager(getSupportFragmentManager());
        ColorAnimationView colorAnimationView = (ColorAnimationView) findViewById(R.id.ColorAnimationView);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adpter);

        colorAnimationView.setmViewPager(viewPager, resource.length, getResources().getColor(R.color.color_purple),getResources().getColor(R.color.color_white));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.frame_guide:
                int index = viewPager.getCurrentItem();
                if(index == 0)
                    viewPager.setCurrentItem(1);
                break;
        }
    }

    public class MyFragmentStatePager extends FragmentStatePagerAdapter {

        public MyFragmentStatePager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new GuideFragment(position);
        }

        @Override
        public int getCount() {
            return resource.length;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode,
                resultCode, data);
    }

}
