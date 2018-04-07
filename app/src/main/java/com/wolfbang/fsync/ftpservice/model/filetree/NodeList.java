package com.wolfbang.fsync.ftpservice.model.filetree;

import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author david
 * @date 18 Mar 2018.
 */

public class NodeList implements Iterable<Node> {

    private TreeSet<Node> mNodeTreeSet = new TreeSet<>(Node.Comparator);

    public NodeList() {
    }

    public int size() {
        return mNodeTreeSet.size();
    }

    public void add(Node node) {
        mNodeTreeSet.add(node);
    }

    public Node find(String name) {
        for (Node child : mNodeTreeSet) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    public boolean remove(Node node) {
        return mNodeTreeSet.remove(node);
    }

    /**
     * @return a new array of nodes, copied from this instance
     */
    public Node[] toArray() {
        return mNodeTreeSet.toArray(new Node[size()]);
    }

    @NonNull
    @Override
    public Iterator<Node> iterator() {
        return mNodeTreeSet.iterator();
    }

}
