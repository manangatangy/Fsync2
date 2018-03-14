package com.wolfbang.fsync.ftpservice.model;

import java.util.Date;

/**
 * @author david
 * @date 14 Mar 2018.
 */

public class Symlink extends File {

    public Symlink(String name, Date timeStamp,
                   Directory parent) {
        super(name, timeStamp, parent);
    }

    @Override
    public String getPath() {
        return parent.getPath() + "/" + name;
    }

}
