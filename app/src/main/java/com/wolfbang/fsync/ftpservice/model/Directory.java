package com.wolfbang.fsync.ftpservice.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public class Directory extends File {

    public List<File> children = new ArrayList<>();

    public Directory(String name, Date timeStamp) {
        super(name, timeStamp);
    }

}
