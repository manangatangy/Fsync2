package com.wolfbang.fsync.model.mission;

import android.support.annotation.NonNull;

import com.wolfbang.fsync.model.filetree.DirNode;
import com.wolfbang.fsync.model.filetree.Node;

/**
 * @author David Weiss
 * @date 16/4/18
 */
public class CannedDataEndPoint extends EndPoint {

    private String rootDir;
    private String[] paths;

    public enum CannedDataEndPointError implements EndPointError {
        ERROR_IN_DATA;

        @NonNull
        public String getError() {
            return this.name();
        }
    }

    public CannedDataEndPoint(String endPointName, String rootDir, String[] paths) {
        super(endPointName);
        this.rootDir = rootDir;
        this.paths = paths;
    }

    public String getRootDir() {
        return rootDir;
    }

    @Override
    public EndPointResponse<DirNode> fetchFileTree() {
        DirNode root = new DirNode(rootDir, null, Node.parseDateForInflation("2018-01-01 00:00:00.000"));
        String error = null;
        for (String path : paths) {
            if (null == Node.inflateFile(root, path)) {
                error = path + " cannot be inflated";
                break;
            }
        }
        if (error != null) {
            return new EndPointResponse<>(null, CannedDataEndPointError.ERROR_IN_DATA, error);
        } else {
            return new EndPointResponse<>(root, null, null);
        }
    }

}
