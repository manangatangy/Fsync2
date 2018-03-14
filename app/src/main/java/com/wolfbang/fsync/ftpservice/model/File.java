package com.wolfbang.fsync.ftpservice.model;

import java.util.Date;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public class File {

    public final String name;
    public final Date timeStamp;
    public final Directory parent;

    /**
     * @param name should not include path separators.
     * @param timeStamp
     * @param parent null to indicate root File
     */
    public File(String name, Date timeStamp,
                Directory parent) {
        this.name = name;
        this.timeStamp = timeStamp;
        this.parent = parent;
    }

    public boolean isRoot() {
        return (parent == null);
    }

    /**
     * @return path including all ancestor's paths, separated by /
     */
    public String getPath() {
        return name;
    }
}
