package com.wolfbang.fsync.ftpservice.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public class FileNode {

    enum NodeType {
        FILE,
        DIR,
        SYMLINK
    }

    private final String mName;
    private final DirNode mParent;
    private final Date mTimeStamp;

    /**
     * @param name should not include path separators.
     * @param parent null to indicate root File
     * @param timeStamp
     */
    public FileNode(String name, DirNode parent, Date timeStamp) {
        mName = name;
        mParent = parent;
        mTimeStamp = timeStamp;
    }

    public NodeType getNodeType() {
        return NodeType.FILE;
    }

    public String getName() {
        return mName;
    }

    public DirNode getParent() {
        return mParent;
    }

    public Date getTimeStamp() {
        return mTimeStamp;
    }

    public String getPath() {
        return (getParent() != null ? (getParent().getPath() + "/") : "") + getName();
    }

    public boolean isRoot() {
        return (getParent() == null);
    }

    public String toString() {
        return "'" + getName() + "', " + getDetails();
    }

    public String toStringWithPath() {
        return "'" + getPath() + "', " + getDetails();
    }

    public String getDetails() {
        String details = getNodeType().name();
        if (getTimeStamp() != null) {
            details = details + "', '" + formatDate(getTimeStamp()) + "'";
        }
        return details;
    }

    public void dump(String tag) {
        Log.d(tag, toStringWithPath());
    }

    // "name/name/name/date-string"
    public static DirNode inflate(@NonNull DirNode root, @NonNull String path) {

        String[] names = path.split("/");
        if (names.length < 2) {
            return null;
        }
        int numberOfNames = names.length - 1;
        String expectedTimeStamp = names[numberOfNames];
        DirNode parent = root;
        for (int index = 0; index < numberOfNames; index++) {
            // The last name should be of a non-directory
            NodeType expectedChildType = (index == (numberOfNames - 1)) ? NodeType.FILE : NodeType.DIR;
            String name = names[index];
            FileNode child = parent.findChild(name);
            if (child == null) {
                // No more common ancestors; start creating nodes.
                child = (expectedChildType == NodeType.FILE)
                        ? new FileNode(name, parent, parseDate(expectedTimeStamp))
                        : new DirNode(name, parent, parseDate(expectedTimeStamp));
                parent.getChildren().add(child);
                Log.d("ftp", "create " + child.toStringWithPath());
            }
            if (expectedChildType != child.getNodeType()) {
                Log.d("ftp", "Mismatch on node type for same path, index:"
                        + index + ", path:" + path);
                return null;        // Mismatch on node type for same path
            }
            if (expectedChildType == NodeType.DIR) {
                parent = (DirNode)child;
            } else {
                // Check timestamps are equal.
                String actualTimeStamp = formatDate(child.getTimeStamp());
                if (!expectedTimeStamp.equals(actualTimeStamp)) {
                    Log.d("ftp", "Mismatch on last node timestamp, actual '"
                            + actualTimeStamp + "', expected '" + expectedTimeStamp + "'");
                    return null;        // Mismatch on last node timestamp
                }
            }
        }
        return root;
    }

    private static String mDateFormat = "yyyy-MM-dd HH:mm:ss.SSS z";
    private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(mDateFormat);

    public static String formatDate(Date date) {
        return mSimpleDateFormat.format(date);
    }

    public static Date parseDate(String timeStamp) {
        try {
            return mSimpleDateFormat.parse(timeStamp);
        } catch (ParseException e) {
            return null;
        }
    }
}
