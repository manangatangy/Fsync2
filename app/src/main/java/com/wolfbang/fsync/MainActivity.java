package com.wolfbang.fsync;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.wolfbang.fsync.missionsummary.impl.MissionSummaryData;
import com.wolfbang.fsync.missionsummary.impl.MissionSummaryFragment;
import com.wolfbang.fsync.feature2.impl.Feature2Activity;
import com.wolfbang.fsync.feature2.impl.Feature2Data;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author david
 * @date 09 Mar 2018.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @OnClick(R.id.mission_summary_button)
    public void onMissionSummaryButtonClick() {
        startActivity(MissionSummaryFragment.createIntent(this, new MissionSummaryData()));
    }

    @OnClick(R.id.feature2_button)
    public void onFeature2ButtonClick() {
        startActivity(Feature2Activity.createIntent(this, new Feature2Data()));
    }

}
