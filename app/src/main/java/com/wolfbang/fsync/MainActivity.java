package com.wolfbang.fsync;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.daimajia.swipe.SwipeLayout;
import com.wolfbang.fsync.ftpservice.FtpEndPoint;
import com.wolfbang.fsync.model.mission.MissionNameData;
import com.wolfbang.fsync.missionsummary.impl.MissionSummaryFragment;
import com.wolfbang.fsync.view.PathElementView;
import com.wolfbang.fsync.view.PathScrollerView;
import com.wolfbang.fsync.view.PathScrollerView.OnPathElementClickListener;
import com.wolfbang.shared.view.AnimatingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author david
 * @date 09 Mar 2018.
 */

public class MainActivity extends AnimatingActivity {

    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.path_scroller_view)
    PathScrollerView mPathScrollerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Note that the Toolbar defined in the layout has the id "my_toolbar"
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mPathScrollerView.setOnPathElementClickListener(new OnPathElementClickListener() {
            @Override
            public void onPathElementClick(int index, PathElementView pathElementView) {
                mPathScrollerView.pop(index);
            }
        });

        SwipeLayout swipeLayout =  (SwipeLayout)findViewById(R.id.swipe);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
//        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
//            @Override
//            public void onOpen(SwipeLayout layout) {
//                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
//            }
//        });

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @OnClick(R.id.mission_summary_button)
    public void onMissionSummaryButtonClick() {
        useStartAnimations();
        startActivity(MissionSummaryFragment.createIntent(this, getMissionNameData1()));
    }

    @OnClick(R.id.feature2_button)
    public void onFeature2ButtonClick() {
        mPathScrollerView.push(new PathElementView(this));
    }

    private MissionNameData getMissionNameData1() {
        return new MissionNameData("Mission-One", getFtpEndPoint1(), getFtpEndPoint2());
    }
    private FtpEndPoint getFtpEndPoint1() {
        return new FtpEndPoint(
                "Dell-Mint-Music",
                "192.168.0.9",
                "music",
                "music",
                "test"        // was "Music/."
        );
    }
    private FtpEndPoint getFtpEndPoint2() {
        return new FtpEndPoint(
                "Local-Android-Music",
                "192.168.0.9",
                "music",
                "music",
                "test"        // was "Music/."
        );
    }
    private FtpEndPoint getFtpEndPointRef() {
        return new FtpEndPoint(
                "Local-Android-Music",
                "host",
                "userName",
                "password",
                "rootDir"
        );
    }

}
