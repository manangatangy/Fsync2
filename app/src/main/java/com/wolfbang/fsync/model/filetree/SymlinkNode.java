package com.wolfbang.fsync.model.filetree;

import java.util.Date;

/**
 * @author david
 * @date 14 Mar 2018.
 */

public class SymlinkNode extends FileNode {

    public SymlinkNode(String name, DirNode parent, Date timeStamp) {
        super(name, parent, timeStamp);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.SYMLINK;
    }

}
