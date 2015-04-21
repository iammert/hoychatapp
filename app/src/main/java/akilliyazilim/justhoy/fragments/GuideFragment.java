package akilliyazilim.justhoy.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.parse.PushService;
import com.romainpiel.shimmer.Shimmer;

import akilliyazilim.justhoy.R;
import akilliyazilim.justhoy.activity.GuideActivity;
import akilliyazilim.justhoy.activity.HoyActivity;
import akilliyazilim.justhoy.model.PersonModel;
import akilliyazilim.justhoy.tasks.AddHoycuTask;
import akilliyazilim.justhoy.utils.ApplicationPreferences;
import akilliyazilim.justhoy.utils.Utils;
import akilliyazilim.justhoy.views.HalvelticaTextView;
import akilliyazilim.justhoy.views.ShimmerExtendedText;

/**
 * Created by mertsimsek on 13.08.2014.
 */
@SuppressLint("ValidFragment")
public class GuideFragment extends Fragment implements View.OnClickListener {

    int fragment_page;
    HalvelticaTextView textview_top;
    ImageView icon;
    HalvelticaTextView textview_slogan;
    ShimmerExtendedText textview_enter;
    View seperator;
    Shimmer shimmer;
    ImageView imageview_next;

    public GuideFragment(int fragment_page) {
        this.fragment_page = fragment_page;
    }

    public static GuideFragment newInstance(int fragment_page) {
        return new GuideFragment(fragment_page);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.guide_fragment, container, false);

        imageview_next = (ImageView) v.findViewById(R.id.imageview_right_arrow);
        imageview_next.setOnClickListener(this);
        textview_top = (HalvelticaTextView) v.findViewById(R.id.fragment_guide_top_text);
        icon = (ImageView) v.findViewById(R.id.icon_hoy);
        textview_slogan = (HalvelticaTextView) v.findViewById(R.id.textview_guide_first_bottom_slogan);
        textview_enter = (ShimmerExtendedText) v.findViewById(R.id.textview_guide_enter);
        seperator = (View) v.findViewById(R.id.seperator);

        if (fragment_page == 0) {
            textview_top.setTextColor(getResources().getColor(R.color.color_white));
            icon.setBackgroundResource(R.drawable.icon_hoy_white);
            textview_slogan.setTextColor(getResources().getColor(R.color.color_white));
            textview_enter.setVisibility(View.INVISIBLE);
            textview_enter.setClickable(false);
            imageview_next.setVisibility(View.VISIBLE);
            imageview_next.setClickable(true);
            seperator.setVisibility(View.INVISIBLE);

        } else {
            textview_top.setTextColor(getResources().getColor(R.color.color_purple));
            icon.setBackgroundResource(R.drawable.icon_hoy_purple);
            textview_slogan.setVisibility(View.INVISIBLE);
            textview_enter.setTextColor(getResources().getColor(R.color.color_purple));
            textview_enter.setVisibility(View.VISIBLE);
            textview_enter.setClickable(true);
            seperator.setVisibility(View.VISIBLE);
            imageview_next.setVisibility(View.INVISIBLE);
            imageview_next.setClickable(false);
            textview_enter.setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_guide_enter:
                if(Utils.checkConnection(getActivity().getApplicationContext()))
                {
                    textview_enter.setClickable(false);
                    shimmer = new Shimmer();
                    shimmer.start(textview_enter);
                    startLoginFacebook();
                }
                else
                    Toast.makeText(getActivity().getApplicationContext(),getResources().getString(R.string.toast_internet_error),Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageview_right_arrow:
                if(GuideActivity.viewPager!=null)
                    GuideActivity.viewPager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }

    public void startLoginFacebook()
    {
        // start Facebook Login
        Session.openActiveSession(getActivity(), true, new Session.StatusCallback() {

            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if (session.isOpened()) {

                    Request.newMeRequest(session, new Request.GraphUserCallback() {

                        @Override
                        public void onCompleted(final GraphUser user, Response response) {

                                if (user != null) {
                                    PersonModel p = new PersonModel();
                                    p.setUser_id(user.getId());
                                    PushService.subscribe(getActivity().getApplicationContext(), "hoykanali" + user.getId().toString(), HoyActivity.class);
                                    p.setImage_url("https://graph.facebook.com/" + user.getId() + "/picture?type=large");
                                    p.setName(user.getFirstName());
                                    ApplicationPreferences.getInstance(getActivity().getApplicationContext()).saveMeToPrefs(p);
                                    AddHoycuTask task = new AddHoycuTask(p)
                                    {
                                        @Override
                                        protected void onPostExecute(Void aVoid) {
                                            super.onPostExecute(aVoid);
                                            shimmer.cancel();
                                            Intent i = new Intent(getActivity(), HoyActivity.class);
                                            i.putExtra("id",user.getId());
                                            startActivity(i);
                                            getActivity().finish();
                                        }
                                    };
                                    task.execute();

                            }
                        }
                    }).executeAsync();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
    }

}
