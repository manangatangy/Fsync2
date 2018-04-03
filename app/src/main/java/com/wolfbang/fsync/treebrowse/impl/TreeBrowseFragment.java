package com.wolfbang.fsync.treebrowse.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lsmvp.simplemvp.AbstractMvpViewFragment;
import com.lsmvp.simplemvp.ModelUpdater;
import com.lsmvp.simplemvp.ObjectRegistry;
import com.wolfbang.fsync.R;
import com.wolfbang.fsync.application.FsyncApplication;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.FileNode;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Model;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Navigation;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Presenter;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.View;
import com.wolfbang.fsync.adapter.TreeItemRecyclerAdapter;
import com.wolfbang.fsync.adapter.TreeItemRecyclerAdapter.TreeItemClickListener;
import com.wolfbang.fsync.treebrowse._di.DaggerTreeBrowseComponent;
import com.wolfbang.fsync.treebrowse._di.TreeBrowseComponent;
import com.wolfbang.fsync.treebrowse._di.TreeBrowseModule;
import com.wolfbang.shared.BackClickHandler;
import com.wolfbang.shared.DefaultLayoutManager;
import com.wolfbang.shared.view.SingleFragActivity;
import com.wolfbang.shared.view.AnimatingActivity;

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
        implements View, Navigation, BackClickHandler, TreeItemClickListener {

    private static final String TBF_DIRNODE = "TBF_DIRNODE";

    @BindView(R.id.title_text)
    TextView mTextView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static Intent createIntent(Context context, @NonNull DirNode dirNode, String title) {
        Intent intent = new SingleFragActivity.Builder(context, TreeBrowseFragment.class.getName())
                .setDisplayHomeAsUpEnabled(true)
                .setTitle(title)
                .build();

        ObjectRegistry objectRegistry = FsyncApplication.getFsyncApplicationComponent().getObjectRegistry();
        String key = objectRegistry.put(dirNode);
        intent.putExtra(TBF_DIRNODE, key);

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

                String key = args.getString(TBF_DIRNODE, "");
                DirNode dirNode = getObjectRegistry().get(key);
                model.setDirNode(dirNode);
            }
        };
    }

    @Override
    public boolean onBackPressed() {
        getPresenter().onBackClicked();
        return false;
    }
    //endregion

    //region TreeBrowseContract.View
    @Override
    public void setDirNode(DirNode dirNode) {
        mTextView.setText(dirNode.getName());
        getAdapter().setDirNode(dirNode);
    }

    //endregion

    //region TreeBrowseContract.Navigation
    @Override
    public void navigateToBrowseTree(final DirNode dirNode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((AnimatingActivity) getActivity()).useStartAnimations();
                ((AnimatingActivity) getActivity()).startActivity(TreeBrowseFragment.createIntent(getContext(), dirNode, "title"));
            }
        });
    }

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
            adapter.setTreeItemClickListener(this);
            mRecyclerView.setAdapter(adapter);
        }
        return adapter;
    }

    @Override
    public void onTreeItemClick(FileNode fileNode) {
        getPresenter().onItemClicked(fileNode);
    }
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

