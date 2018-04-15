package com.wolfbang.fsync.ftpservice.model.filetree;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author david
 * @date 16 Mar 2018.
 */

public abstract class Node implements Comparable<Node> {

    public abstract NodeType getNodeType();
    public abstract String getName();
    public abstract DirNode getParent();
    public abstract Date getTimeStamp();

    public String getPath() {
        return (getParent() != null
                ? (getParent().getPath() + "/") : "") + getName();
    }

    public String getPathExcRoot() {
        return (getParent() != null && !getParent().isRoot()
                ? (getParent().getPathExcRoot() + "/") : "") + getName();
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
            details = details + "', '" + getTimeStampAsText() + "'";
        }
        return details;
    }

    public String getTimeStampAsText() {
        return formatDate(getTimeStamp()) ;
    }

    /**
     * Add missing nodes in the root, according to the path; the last path name for a file.
     * @return the terminal node or null (if node-type mismatch or terminal node file
     * timestamp during traversal)
     */
    public static FileNode inflateFile(@NonNull DirNode root, @NonNull String path) {
        return (FileNode)inflate(root, path, NodeType.FILE);
    }

    /**
     * Add missing nodes in the root, according to the path; the last path name for a dir.
     * @return the terminal node or null (if node-type mismatch during traversal)
     */
    public static DirNode inflateDir(@NonNull DirNode root, @NonNull String path) {
        return (DirNode)inflate(root, path, NodeType.DIR);
    }

    /**
     * Parse the path and use the dir names to traverse the node tree (from the root).
     * Create any nodes specified in the path but missing from the tree.
     * This call will fail (with no new nodes created) if a path name already exists in
     * the root-tree but is; 1) of the wrong type (file/directory), or; 2) if the last
     * path name is already existing as a file but the timestamp is different.
     * @param root - the DirNode to which this new set of nodes will be attached.
     * @param path - a spec of the form "dirName/dirName/fileName/timestamp" for
     * FILE terminalNodeType, or "dirName/dirName/dirName" for DIR terminalNodeType.
     * @param terminalNodeType
     * @return null in the case of mismatch error, or FileNode/DirNode depending on
     * the specified terminalNodeType.
     */
    public static Node inflate(@NonNull DirNode root, @NonNull String path, NodeType terminalNodeType) {

        String[] names = path.split("/");

        int numberOfDirNames = names.length - 2;
        int minimumNames = 2;
        String createTimestamp = names[names.length - 1];
        if (terminalNodeType == NodeType.DIR) {
            numberOfDirNames = names.length;
            minimumNames = 1;
            createTimestamp = null;
        }

        if (names.length < minimumNames) {
            return null;
        }

        DirNode parent = root;
        for (int index = 0; index < numberOfDirNames; index++) {
            // The last name should be of a non-directory
            String name = names[index];
            FileNode child = parent.findChild(name);
            if (child == null) {
                // No more common ancestors; start creating dir nodes.
                child = new DirNode(name, parent, null);
                parent.add(child);
                Log.d("inflate", "create-dir " + child.toStringWithPath());
            }
            if (NodeType.DIR != child.getNodeType()) {
                Log.d("inflate", "Mismatch on node type (expecting DIR) for same path, index:"
                        + index + ", path:" + path);
                return null;          // Mismatch on node type for same path
            }
            parent = (DirNode)child;
        }
        if (terminalNodeType == NodeType.DIR) {
            return parent;
        }

        String name = names[names.length - 2];          // Filename (last path name)
        FileNode child = parent.findChild(name);
        if (child == null) {
            child = new FileNode(name, parent, parseDate(createTimestamp));
            parent.add(child);
            Log.d("inflate", "create-file" + child.toStringWithPath());
        } else if (NodeType.FILE != child.getNodeType()) {
            Log.d("inflate", "Mismatch on node type (expecting FILE) for same path, index:"
                    + (names.length) + ", path:" + path);
            return null;          // Mismatch on node type for same path
        } else {
            String actualTimeStamp = formatDate(child.getTimeStamp());
            if (!createTimestamp.equals(actualTimeStamp)) {
                Log.d("inflate", "Mismatch on last node timestamp, actual '"
                        + actualTimeStamp + "', expected '" + createTimestamp + "'");
                return null;        // Mismatch on last node timestamp
            }
        }

        return child;
    }

    //region comparison
    public static final java.util.Comparator<Node> Comparator = new java.util.Comparator<Node>() {
        @Override
        public int compare(Node node1, Node node2) {
            return node1.compareTo(node2);
        }
    };

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Node) ? (compareTo((Node)obj) == 0) : super.equals(obj);
    }

    @Override
    public int compareTo(Node node) {
        return this.getName().compareTo(node.getName());
    }
    //endregion

    private static String mDateFormat = "d MMM yyyy HH:mm:ss Z";
    private static String mDateFormatWithBreak = "d MMM yyyy\nHH:mm:ss Z";
    // TODO should Locale be used in this formatter ?
    private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(mDateFormat);
    private static SimpleDateFormat mSimpleDateFormatWithBreak = new SimpleDateFormat(mDateFormatWithBreak);

    public static String formatDate(Date date) {
        return (date == null) ? "<null-date>" : mSimpleDateFormat.format(date);
    }

    public static String formatDateWithBreak(Date date) {
        return (date == null) ? "<null-date>" : mSimpleDateFormatWithBreak.format(date);
    }

    public static Date parseDate(String timeStamp) {
        try {
            return mSimpleDateFormat.parse(timeStamp);
        } catch (ParseException e) {
            return null;
        }
    }

    public void dump(String tag) {
        Log.d(tag, toStringWithPath());
    }

}
