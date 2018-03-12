package com.wolfbang.fsync.ftpservice.model;

import java.util.Date;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public class File {
    public final String name;
    public final Date timeStamp;

    public File(String name, Date timeStamp) {
        this.name = name;
        this.timeStamp = timeStamp;
    }
}
