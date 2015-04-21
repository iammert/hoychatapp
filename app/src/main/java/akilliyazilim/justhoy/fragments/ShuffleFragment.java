package akilliyazilim.justhoy.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockFragment;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import akilliyazilim.justhoy.R;
import akilliyazilim.justhoy.activity.MessagingActivity;
import akilliyazilim.justhoy.adapters.ShuffleGridAdapter;
import akilliyazilim.justhoy.model.PersonModel;
import akilliyazilim.justhoy.tasks.GetHoycularTask;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable;

/**
 * Created by mertsimsek on 14.08.2014.
 */
@SuppressLint("ValidFragment")
public class ShuffleFragment extends SherlockFragment implements AdapterView.OnItemClickListener,View.OnClickListener{

    GridView grid;
    SmoothProgressBar progress;
    int[] colors ;
    ShuffleGridAdapter adapter;
    String user_id;

    public ShuffleFragment(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_shuffle,container,false);

        Resources resources = getResources();
        colors = new int[]{resources.getColor(R.color.color_purple), resources.getColor(R.color.color_purple), resources.getColor(R.color.color_purple)};

        grid = (GridView) v.findViewById(R.id.grid);
        FloatingActionButton floatingActionButton = (FloatingActionButton) v.findViewById(R.id.button_floating_action);
        floatingActionButton.setShadow(true);
        floatingActionButton.attachToListView(grid);
        floatingActionButton.setOnClickListener(this);
        grid.setOnItemClickListener(this);
        progress = (SmoothProgressBar) v.findViewById(R.id.progress);
        progress.setIndeterminateDrawable(new SmoothProgressDrawable.Builder(getActivity())
                .interpolator(new DecelerateInterpolator())
                .colors(colors)
                .sectionsCount(2)
                .strokeWidth(8f)            //You should use Resources#getDimension
                .speed(1.5f)                 //2 times faster
                .progressiveStartSpeed(2)
                .progressiveStopSpeed(3.4f)
                .reversed(true)
                .mirrorMode(true)
                .progressiveStart(true)
                .build());
        progress.setVisibility(View.INVISIBLE);



        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        executeShufflePeople();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getActivity(), MessagingActivity.class);
        PersonModel stranger = (PersonModel)parent.getAdapter().getItem(position);
        i.putExtra("stranger_name",stranger.getName());
        i.putExtra("stranger_image_url",stranger.getImage_url());
        i.putExtra("stranger_id",stranger.getUser_id());
        i.putExtra("message_unread","read");
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_floating_action:
                executeShufflePeople();
                break;
        }
    }

    public void executeShufflePeople()
    {
        GetHoycularTask get_hoycular_task = new GetHoycularTask(user_id)
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(ArrayList<PersonModel> personModels) {
                super.onPostExecute(personModels);
                adapter = new ShuffleGridAdapter(getActivity().getApplicationContext(),personModels);
                grid.setAdapter(adapter);
                progress.setVisibility(View.INVISIBLE);
            }
        };
        get_hoycular_task.execute();
    }
}
