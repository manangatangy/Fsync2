package com.wolfbang.fsync;

import com.wolfbang.fsync.ftpservice.model.DirNode;
import com.wolfbang.fsync.ftpservice.model.Node;

import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * @author david
 * @date 15 Mar 2018.
 */

public class FileNodeTest {

    @Test
    public void testLoad() {
        // Syntax for names; N-XXX [N=directory level, root is 0] [XXX=dir-name, xxx=file-name]
        DirNode root = new DirNode("root", null, Node.parseDate("2018-01-01 00:00:00.000 AEDT"));
        assertEquals(root, Node.inflate(root, "1-AAA/2-aaa/2018-03-15 00:00:00.000 AEDT"));
        root.dump("tfp");

        // Inflating same path will create nothing, return same root,
        assertEquals(root, Node.inflate(root, "1-AAA/2-aaa/2018-03-15 00:00:00.000 AEDT"));
        // Mismatch on timestamp
        assertNull(Node.inflate(root, "1-AAA/2-aaa/2018-03-15 00:00:00.001 AEDT"));
        // Mismatch on node type - new=child
        assertNull(Node.inflate(root, "1-AAA/2018-03-15 00:00:00.000 AEDT"));
        // Mismatch on node type - new=dir
        assertNull(Node.inflate(root, "1-AAA/2-aaa/3-ccc/2018-03-15 00:00:00.000 AEDT"));

        // Now create diverging tree
        assertEquals(root, Node.inflate(root, "1-AAA/2-bbb/2018-03-15 00:00:00.000 AEDT"));
        assertEquals(root, Node.inflate(root, "1-AAA/2-ccc/2018-03-15 00:00:00.000 AEDT"));
        assertEquals(root, Node.inflate(root, "1-BBB/2-aaa/2018-03-15 00:00:00.000 AEDT"));
        assertEquals(root, Node.inflate(root, "1-BBB/2-BBB/3-aaa/2018-03-15 00:00:00.000 AEDT"));
        assertEquals(root, Node.inflate(root, "1-eee/2018-03-15 00:00:00.000 AEDT"));
        assertEquals(root, Node.inflate(root, "1-ddd/2018-03-15 00:00:00.000 AEDT"));
        assertEquals(root, Node.inflate(root, "1-ccc/2018-03-15 00:00:00.000 AEDT"));

        root.dump("tfp");

    }
}
