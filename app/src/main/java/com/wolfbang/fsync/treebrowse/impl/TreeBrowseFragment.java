package com.wolfbang.fsync.treebrowse.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lsmvp.simplemvp.AbstractMvpViewFragment;
import com.lsmvp.simplemvp.ModelUpdater;
import com.wolfbang.fsync.R;
import com.wolfbang.fsync.application.FsyncApplication;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Model;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Navigation;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Presenter;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.View;
import com.wolfbang.fsync.treebrowse._di.DaggerTreeBrowseComponent;
import com.wolfbang.fsync.treebrowse._di.TreeBrowseComponent;
import com.wolfbang.fsync.treebrowse._di.TreeBrowseModule;
import com.wolfbang.shared.BackClickHandler;

/**
 * @author david
 * @date 19 Mar 2018.
 */

/*
implements TreeItemClickListener

@BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new DefaultLayoutManager(getContext()));
    }


    @Override
    public void refreshList(List<String> stuff) {
        getAdapter().setDirNode(stuff);
    }

    private NavMenuRecyclerAdapter getAdapter() {
        NavMenuRecyclerAdapter adapter = (NavMenuRecyclerAdapter)mRecyclerView.getAdapter();
        if (adapter == null) {
            adapter = new NavMenuRecyclerAdapter(getContext());
            adapter.setNavMenuItemClickListener(this);
            mRecyclerView.setAdapter(adapter);
        }
        return adapter;
    }

    void onItemClicked() {
    }

 */
public class TreeBrowseFragment
        extends AbstractMvpViewFragment<Presenter, Model, TreeBrowseComponent>
        implements View,
                   Navigation,
                   BackClickHandler {

    //region SimpleMVP
    @NonNull
    @Override
    protected TreeBrowseComponent createComponent() {
        return DaggerTreeBrowseComponent.builder()
                                        .fsyncApplicationComponent(FsyncApplication.getFsyncApplicationComponent())
                                        .treeBrowseModule(new TreeBrowseModule())
                                        .build();
    }

    @Override
    protected void doInjection(@NonNull TreeBrowseComponent component) {
        component.inject(this);
    }

    @NonNull
    @Override
    protected Presenter createPresenter(@NonNull TreeBrowseComponent component) {
        return component.providePresenter();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mission_summary;
    }

    @Override
    protected void onBound() {
        super.onBound();
    }

    @Nullable
    @Override
    protected ModelUpdater<Model> getModelInitializer() {
        return new ModelUpdater<Model>() {
            @Override
            public void updateModel(Model model) {
                Bundle args = getArguments();

//                String key = args.getString(MSF_MISSION_DATA, "");
//                MissionData missionData = getObjectRegistry().get(key);
//                model.setMissionData(missionData);
            }
        };
    }
    //endregion

    //region MissionSummaryContract.View
//    @Override
//    public void setMissionName(String missionName) {
//        mHeadingRowView.setValue(missionName);
//    }
//
//    @Override
//    public void setEndPointDetailsA(EndPoint endPoint) {
//        FtpEndPoint ftpEndPoint = (FtpEndPoint)endPoint;
//        setEndPointDetails("A", ftpEndPoint, mEndPointA);
//    }
//
//    @Override
//    public void setEndPointDetailsB(EndPoint endPoint) {
//        FtpEndPoint ftpEndPoint = (FtpEndPoint)endPoint;
//        setEndPointDetails("B", ftpEndPoint, mEndPointB);
//    }
//
//    @Override
//    public void setSomeField(final String someValue) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
////                mTextView.setText(someValue);
//            }
//        });
//    }
//
//    @Override
//    public void showError(final String errorMsg) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle( "Error" )
//                       .setMessage(errorMsg)
//                       .setPositiveButton("OK", null)
//                       .show();
//            }
//        });
//    }
//
//    @Override
//    public void showLoadingState(final boolean show) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (show) {
//                    showProgressDialog();
//                } else {
//                    hideProgressDialog();
//                }
//            }
//        });
//    }
    //endregion

//    @OnClick(R.id.sync_scan_button)
//    public void onButtonClick() {
//        getPresenter().onSyncScanButtonClicked();
//    }
//
    @Override
    public boolean onBackPressed() {
        getPresenter().onBackClicked();
        return false;
    }
//
//    private void setEndPointDetails(String name, FtpEndPoint endPoint, EndPointDetailView view) {
//        view.getHeadingRowView().setLabel("End Point " + name);
//        view.getHeadingRowView().setValue(endPoint.getEndPointName());
//        view.getHostNameRowView().setValue(endPoint.getHost());
//        view.getUserNameRowView().setValue(endPoint.getUserName());
//        view.getPasswordRowView().setValue(endPoint.getPassword());
//        view.getRootDirRowView().setValue(endPoint.getRootDir());
//    }

}
