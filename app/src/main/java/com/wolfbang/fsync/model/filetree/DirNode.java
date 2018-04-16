package com.wolfbang.fsync.model.filetree;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wolfbang.fsync.model.compare.Action;

import java.util.Date;
import java.util.Iterator;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public class DirNode extends FileNode  implements Iterable<Node> {

    private NodeList mChildren = new NodeList();

    public DirNode(String name, DirNode parent, Date timeStamp) {
        super(name, parent, timeStamp);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.DIR;
    }

    /**
     * @return the NodeList which should not be modified during iteration.
     */
    public NodeList getChildren() {
        return mChildren;
    }

    @NonNull
    @Override
    public Iterator<Node> iterator() {
        return mChildren.iterator();
    }

    @Nullable
    public FileNode findChild(String name) {
        return (FileNode)mChildren.find(name);
    }

    public void add(FileNode fileNode) {
        fileNode.reParent(this);
        mChildren.add(fileNode);
    }

    public boolean remove(FileNode fileNode) {
        fileNode.reParent(null);
        return mChildren.remove(fileNode);
    }

    public int size() {
        return mChildren.size();
    }

    /**
     * @return a new array of nodes, copied from this instance
     */
    public Node[] toChildrenArray() {
        return mChildren.toArray();
    }

    public int getFileCount() {
        int count = 0;
        for (Node child : getChildren()) {
            if (child instanceof DirNode) {
                count += ((DirNode) child).getFileCount();
            } else {
                count++;
            }
        }
        return count;
    }

    public int getDirCount() {
        int count = 0;      // Don't include the root dir in the count.
        for (Node child : getChildren()) {
            if (child instanceof DirNode) {
                count++;
                count += ((DirNode) child).getDirCount();
            }
        }
        return count;
    }

    public String getFilesDirsCountText() {
        int files = getFileCount();
        int dirs = getDirCount() + 1;       // Add one, for this directory
        return files + (files != 1 ? " files" : " file") + " in " + dirs + (dirs != 1 ? " dirs" : " dir");
    }

    /**
     * These two methods with Action parameter allow the
     * {@link com.wolfbang.fsync.adapter.DirTreeItemViewHolder} to also handle
     * {@link com.wolfbang.fsync.model.compare.ActionableDirNode}s
     */
    public int getFileCount(@Nullable Action action) {
        return getFileCount();
    }

    public int getDirCount(@Nullable Action action) {
        return getDirCount();
    }

    public String getFilesDirsCountText(@Nullable Action action) {
        return getFilesDirsCountText();
    }

    /**
     * Remove the node from it's current parent and place it into this tree, preserving
     * it's heritage (creating intermediate descendants as necessary). This means that
     * the node may be descended further from this DirNode that a direct child. The
     * heritage is taken from the nodes current tree position.
     * Most likely this dirnode should be a root.
     * Note: no name-clash checking is performed.
     */
    public FileNode adopt(@NonNull FileNode fileNode) {
        DirNode adoptedParent = this;

        DirNode orphanParent = fileNode.getParent();
        if (orphanParent != null) {
            String heritage = orphanParent.getPathExcRoot();
            orphanParent.remove(fileNode);
            // No need to inflate a new dir if the fileNode is a child of root.
            if (!orphanParent.isRoot()) {
                // Create/find a new parent under this tree.
                adoptedParent = Node.inflateDir(this, heritage);
                if (adoptedParent == null) {
                    return null;
                }
            }
        }
        adoptedParent.add(fileNode);
        return fileNode;
    }

    @Override
    public String toString() {
        return super.toString() + ", [" + size() + " children]";
    }

    @Override
    public String toStringWithPath() {
        return super.toStringWithPath() + ", [" + size() + " children]";
    }

    /**
     * Perform a depth-first tree traversal and dump each node.
     * @param tag - prefix for each node trace
     */
    @Override
    public void dump(String tag) {
        super.dump(tag);
//        Iterator<Node> it = iterator();
//        while (it.hasNext()) {
//            Node child = it.next();
//            child.dump(tag);
//        }
        for (Node child : getChildren()) {
            child.dump(tag);
        }
    }
}
