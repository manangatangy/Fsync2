package com.wolfbang.fsync.treebrowse.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.lsmvp.simplemvp.AbstractMvpViewFragment;
import com.lsmvp.simplemvp.ModelUpdater;
import com.lsmvp.simplemvp.ObjectRegistry;
import com.wolfbang.fsync.R;
import com.wolfbang.fsync.adapter.DirTreeItemViewHolder.DirTreeItemClickListener;
import com.wolfbang.fsync.adapter.TreeItemRecyclerAdapter;
import com.wolfbang.fsync.application.FsyncApplication;
import com.wolfbang.fsync.ftpservice.model.compare.Action;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;
import com.wolfbang.fsync.ftpservice.model.mission.MissionNameData;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Model;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Navigation;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Presenter;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.View;
import com.wolfbang.fsync.treebrowse._di.DaggerTreeBrowseComponent;
import com.wolfbang.fsync.treebrowse._di.TreeBrowseComponent;
import com.wolfbang.fsync.treebrowse._di.TreeBrowseModule;
import com.wolfbang.fsync.view.PathElementView;
import com.wolfbang.fsync.view.PathScrollerView;
import com.wolfbang.fsync.view.PathScrollerView.OnPathElementClickListener;
import com.wolfbang.shared.BackClickHandler;
import com.wolfbang.shared.DefaultLayoutManager;
import com.wolfbang.shared.view.AnimatingActivity;
import com.wolfbang.shared.view.SingleFragActivity;

import butterknife.BindView;

/**
 * @author david
 * @date 19 Mar 2018.
 */

/*
implements TreeItemClickListener



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new DefaultLayoutManager(getContext()));
    }


    @Override
    public void refreshList(List<String> stuff) {
        getAdapter().setDirNode(stuff);
    }


    void onItemClicked() {
    }

 */
public class TreeBrowseFragment
        extends AbstractMvpViewFragment<Presenter, Model, TreeBrowseComponent>
        implements View, Navigation, BackClickHandler,
                   DirTreeItemClickListener,
                   OnPathElementClickListener {

    private static final String TBF_ACTION = "TBF_ACTION";
    private static final String TBF_DIRNODE = "TBF_DIRNODE";
    private static final String TBF_MISSION_NAME_DATA = "TBF_MISSION_NAME_DATA";

    @BindView(R.id.path_scroller_view)
    PathScrollerView mPathScrollerView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static Intent createIntent(Context context, @Nullable Action action,
                                      @NonNull DirNode dirNode,
                                      MissionNameData missionNameData, String title) {
        Intent intent = new SingleFragActivity.Builder(context, TreeBrowseFragment.class.getName())
                .setDisplayHomeAsUpEnabled(true)
                .setTitle(title)
                .build();

        ObjectRegistry objectRegistry = FsyncApplication.getFsyncApplicationComponent().getObjectRegistry();

        String key1 = objectRegistry.put(action);
        intent.putExtra(TBF_ACTION, key1);
        String key2 = objectRegistry.put(dirNode);
        intent.putExtra(TBF_DIRNODE, key2);
        String key3 = objectRegistry.put(missionNameData);
        intent.putExtra(TBF_MISSION_NAME_DATA, key3);

        return intent;
    }

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
        return R.layout.fragment_tree_browse;
    }

    @Override
    protected void onBound() {
        super.onBound();
        mRecyclerView.setLayoutManager(new DefaultLayoutManager(getContext()));
        mPathScrollerView.setOnPathElementClickListener(this);
//        DirNode dirNode = getPresenter().getDirNode();
//        mTextView.setText(dirNode.getName());
//        getAdapter().setDirNode(dirNode);
    }

    @Nullable
    @Override
    protected ModelUpdater<Model> getModelInitializer() {
        return new ModelUpdater<Model>() {
            @Override
            public void updateModel(Model model) {
                Bundle args = getArguments();

                String key1 = args.getString(TBF_ACTION, "");
                Action action = getObjectRegistry().get(key1);
                String key2 = args.getString(TBF_DIRNODE, "");
                DirNode dirNode = getObjectRegistry().get(key2);
                model.setBaseAndCurrentDir(action, dirNode);
                String key3 = args.getString(TBF_MISSION_NAME_DATA, "");
                MissionNameData missionNameData = getObjectRegistry().get(key3);
                model.setMissionNameData(missionNameData);
            }
        };
    }

    @Override
    public boolean onBackPressed() {
        return getPresenter().onBackClicked();
    }
    //endregion

    //region TreeBrowseContract.View
    @Override
    public void populatePathElements(String[] pathNames) {
        mPathScrollerView.clear();
        for (String pathName : pathNames) {
            addPathElement(pathName);
        }
    }

    @Override
    public void addPathElement(String pathName) {
        PathElementView pathElementView = new PathElementView(getContext());
        pathElementView.setText(pathName);
        mPathScrollerView.push(pathElementView);
    }

    @Override
    public void popPathElement() {
        mPathScrollerView.pop();
    }

    @Override
    public void populateList(Node[] nodes) {
        getAdapter().setNodeItems(nodes);
        getAdapter().notifyDataSetChanged();
    }

    //endregion

    //region TreeBrowseContract.Navigation
//    @Override
//    public void navigateToBrowseTree(final DirNode dirNode) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                ((AnimatingActivity) getActivity()).useStartAnimations();
//                ((AnimatingActivity) getActivity()).startActivity(TreeBrowseFragment.createIntent(getContext(), dirNode, "title"));
//            }
//        });
//    }

    @Override
    public void navigateBack() {
        ((AnimatingActivity) getActivity()).useFinishAnimations();
        navigateExit();
    }
    //endregion

    private TreeItemRecyclerAdapter getAdapter() {
        TreeItemRecyclerAdapter adapter = (TreeItemRecyclerAdapter)mRecyclerView.getAdapter();
        if (adapter == null) {
            adapter = new TreeItemRecyclerAdapter();
            adapter.setDirTreeItemClickListener(this);
            adapter.setMissionNameData(getPresenter().getMissionNameData());
            mRecyclerView.setAdapter(adapter);
        }
        return adapter;
    }

    //region OnPathElementClickListener
    @Override
    public void onPathElementClick(int index, PathElementView pathElementView) {
        getPresenter().onPathElementClicked(index);
//        mPathScrollerView.pop(index);
    }
    //endregion

    //region TreeItemClickListener
    @Override
    public void onDirTreeItemClick(DirNode dirNode) {
        getPresenter().onListItemClicked(dirNode);
    }
    //endregion

}

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

//    @OnClick(R.id.sync_scan_button)
//    public void onButtonClick() {
//        getPresenter().onScanButtonClicked();
//    }
//
//
//    private void setEndPointDetails(String name, FtpEndPoint endPoint, EndPointDetailView view) {
//        view.getHeadingRowView().setLabel("End Point " + name);
//        view.getHeadingRowView().setValue(endPoint.getEndPointName());
//        view.getHostNameRowView().setValue(endPoint.getHost());
//        view.getUserNameRowView().setValue(endPoint.getUserName());
//        view.getPasswordRowView().setValue(endPoint.getPassword());
//        view.getRootDirRowView().setValue(endPoint.getRootDir());
//    }

