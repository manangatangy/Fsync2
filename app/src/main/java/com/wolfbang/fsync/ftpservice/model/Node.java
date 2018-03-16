package com.wolfbang.fsync.ftpservice.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author david
 * @date 16 Mar 2018.
 */

public abstract class Node implements Comparable<Node> {

    enum NodeType {
        FILE,
        DIR,
        SYMLINK
    }

    public abstract NodeType getNodeType();
    public abstract String getName();
    public abstract DirNode getParent();
    public abstract Date getTimeStamp();

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Node) ? (compareTo((Node)obj) == 0) : super.equals(obj);
    }

    @Override
    public int compareTo(Node node) {
        return this.getName().compareTo(node.getName());
    }

    public String getPath() {
        return (getParent() != null ? (getParent().getPath() + "/") : "") + getName();
    }

    public boolean isRoot() {
        return (getParent() == null);
    }

    @Override
    public String toString() {
        return "'" + getName() + "', " + getDetails();
    }

    public String toStringWithPath() {
        return "'" + getPath() + "', " + getDetails();
    }

    public String getDetails() {
        String details = getNodeType().name();
        if (getTimeStamp() != null) {
            details = details + "', '" + Node.formatDate(getTimeStamp()) + "'";
        }
        return details;
    }

    public void dump(String tag) {
        Log.d(tag, toStringWithPath());
    }

    private static String mDateFormat = "yyyy-MM-dd HH:mm:ss.SSS z";
    // TODO should Locale be used in this formatter ?
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

    /**
     * Parse the path and create the corresponding tree of nodes.  All but the last
     * names will be that of DirNodes.  If the nodes already exist in the specified
     * root tree, then any new nodes will be attached under the correct DirNode.
     * This call will fail (with no new nodes created) if the name already exists in
     * the root-tree but is; 1) of the wrong type (file/directory), or; 2) if the type
     * is a file and the timestamp is different.  Note that if the name already existed,
     * and is file type, and the timestamp is matching, then no new node would be
     * created anyway -- it is simply a check for an existing path.
     * @param root - the DirNode to which this new set of nodes will be attached.
     * @param path - a spec of the form "dirName/dirName/fileName/file-date-string"
     * @return either the same root that was passed in, or null in the case of error.
     */
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
            Node child = parent.findChild(name);
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

}
