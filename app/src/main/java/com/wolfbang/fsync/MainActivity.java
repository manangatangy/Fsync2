package com.wolfbang.fsync;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.wolfbang.fsync.ftpservice.model.mission.FtpEndPoint;
import com.wolfbang.fsync.ftpservice.model.mission.MissionNameData;
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
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @OnClick(R.id.mission_summary_button)
    public void onMissionSummaryButtonClick() {
        useStartAnimations();
        startActivity(MissionSummaryFragment.createIntent(this,
                new MissionNameData(
                        "Mission-One",
                        new FtpEndPoint(
                                "Dell-Mint-Music",
                                "192.168.0.9",
                                "music",
                                "music",
                                "test"        // was "Music/."
                        ),
                        new FtpEndPoint(
                                "Local-Android-Music",
                                "host",
                                "userName",
                                "password",
                                "rootDir"
                        )
                )
        ));
    }

    @OnClick(R.id.feature2_button)
    public void onFeature2ButtonClick() {
        mPathScrollerView.push(new PathElementView(this));
    }

}
