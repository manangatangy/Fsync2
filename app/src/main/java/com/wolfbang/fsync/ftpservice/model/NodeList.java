package com.wolfbang.fsync.ftpservice.model;

import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author david
 * @date 18 Mar 2018.
 */

public class NodeList implements Iterable<Node> {

    private TreeSet<Node> mChildren = new TreeSet<>(Node.Comparator);

    public NodeList() {

    }

    public int size() {
        return mChildren.size();
    }

    public void add(Node node) {
        mChildren.add(node);
    }

    public Node find(String name) {
        for (Node child : mChildren) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    @NonNull
    @Override
    public Iterator<Node> iterator() {
        return mChildren.iterator();
    }

}
