package akilliyazilim.justhoy.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.FrameLayout;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.astuetz.PagerSlidingTabStrip;

import akilliyazilim.justhoy.R;
import akilliyazilim.justhoy.fragments.MessagesFragment;
import akilliyazilim.justhoy.fragments.ShuffleFragment;
import akilliyazilim.justhoy.utils.ApplicationPreferences;
import akilliyazilim.justhoy.views.TypefaceSpan;
import eu.inmite.android.lib.dialogs.ISimpleDialogListener;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

/**
 * Created by mertsimsek on 13.08.2014.
 */
public class HoyActivity extends SherlockFragmentActivity implements ISimpleDialogListener{

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private String own_id;
    private FrameLayout frame_guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoy_activity);
        initializeActionbarText(getResources().getString(R.string.hoy));

        own_id = getIntent().getStringExtra("id");

        if(ApplicationPreferences.getInstance(getApplicationContext()).isDialogShown()) {
            ApplicationPreferences.getInstance(getApplicationContext()).shareDialogShowed(true);
            SimpleDialogFragment.createBuilder(this, getSupportFragmentManager()).setTitle("Hoy!").setMessage("Share if you want to say more \"Hoy!\"").setPositiveButtonText("Share").setNegativeButtonText("Do not").setCancelable(false).show();
        }
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setShouldExpand(true);
        tabs.setIndicatorColor(getResources().getColor(R.color.color_purple_light));
        tabs.setIndicatorHeight(20);
        tabs.setBackgroundColor(getResources().getColor(R.color.color_purple));
        tabs.setTextColor(getResources().getColor(R.color.color_white));
        tabs.setDividerColor(getResources().getColor(R.color.transparent));
        tabs.setAllCaps(false);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
    }

    public SpannableString initializeActionbarText(String text)
    {
        SpannableString s = new SpannableString("     " + text);
        s.setSpan(new TypefaceSpan(this, "helvelticabold.otf"), 0, s.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(s);
        return s;
    }

    public SpannableString getSpannableText(String text)
    {
        SpannableString s = new SpannableString(text);
        s.setSpan(new TypefaceSpan(this, "helveltica.otf"), 0, s.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.menu, (com.actionbarsherlock.view.Menu) menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onPositiveButtonClicked(int i) {
        //TODO share app
    }

    @Override
    public void onNegativeButtonClicked(int i) {

    }

    @Override
    public void onNeutralButtonClicked(int i) {

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = { getResources().getString(R.string.shuffle), getResources().getString(R.string.messages) };

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getSpannableText(TITLES[position]);
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position)
        {
            if(position == 0)
                return new ShuffleFragment(own_id);
            else
                return new MessagesFragment();
        }

    }

}
