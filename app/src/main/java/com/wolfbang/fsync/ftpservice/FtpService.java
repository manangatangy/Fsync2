package com.wolfbang.fsync.ftpservice;

import android.support.annotation.Nullable;
import android.util.Log;

import com.wolfbang.fsync.ftpservice.model.Directory;
import com.wolfbang.fsync.ftpservice.model.File;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public class FtpService {

    String server = "192.168.0.9";
    String user = "music";
    String password = "music";
    String dir = "/home/music";

    public void connectAndList() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server);
            if(ftpClient.login(user, password)) {
                if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                    ftpClient.enterLocalPassiveMode();
                    String systemType = ftpClient.getSystemType();
                    String currentDir = ftpClient.printWorkingDirectory();

                    Log.d("FtpService", "Remote systemType:" + systemType);
                    Log.d("FtpService", "workingDirectory:" + currentDir);

                    FTPFile[] files = ftpClient.mlistDir(".profile");
                    for (FTPFile file : files) {
                        Date date = file.getTimestamp().getTime();
                        String dateStr = new SimpleDateFormat().format(date);
                        Log.d("FtpService", "File:" + file.getName() + ", Time:" + dateStr);
                    }
                }
                ftpClient.logout();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        try {
            ftpClient.disconnect();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private File visit(FTPClient ftpClient, String path) throws IOException{
        FTPFile ftpFile = ftpClient.mlistFile(path);
        File file = makeFile(ftpFile);
        if (file != null ) {
            populateAndTraverseIfDirectory(ftpClient, file);
        }
        return file;
    }

    private void populateAndTraverseIfDirectory(FTPClient ftpClient, File file) throws IOException {
        if (file instanceof Directory) {
            Directory directory = (Directory)file;
            populate(ftpClient, directory);
            for (File child : directory.children) {
                populateAndTraverseIfDirectory(ftpClient, child);
            }
        }
    }

    private void populate(FTPClient ftpClient, Directory directory) throws IOException {
        FTPFile[] ftpFiles = ftpClient.mlistDir("");
        for (FTPFile ftpFile : ftpFiles) {
            File file = makeFile(ftpFile);
            if (file != null) {
                directory.children.add(file);
            }
        }
    }

    private File makeFile(@Nullable FTPFile ftpFile) {
        if (ftpFile != null) {
            String name = ftpFile.getName();
            Date date = ftpFile.getTimestamp().getTime();
            if (ftpFile.isFile()) {
                return new File(name, date);
            } else if (ftpFile.isDirectory()) {
                return new Directory(name, date);
            }
        }
        return null;
    }

    /**
     * Populate the children of this Directory with Files.
     * Some of the children may be more Directories, but
     * they will not be recursed into here.  This approach means
     * FTPFile instances will not be existent for the entire
     * tree traversal.
     */

}


    /*
    http://www.mysamplecode.com/2012/03/apache-commons-ftpclient-java-example.html
    https://commons.apache.org/proper/commons-net/apidocs/index.html?org/apache/commons/net/ftp/FTPClient.html
    https://commons.apache.org/proper/commons-net/apidocs/org/apache/commons/net/ftp/FTPClient.html#retrieveFile(java.lang.String,%20java.io.OutputStream)
    https://commons.apache.org/proper/commons-net/apidocs/org/apache/commons/net/ftp/FTPClient.html#storeFile(java.lang.String,%20java.io.InputStream)
    https://commons.apache.org/proper/commons-net/apidocs/index.html?org/apache/commons/net/ftp/FTPClientConfig.html


    FTPClient ftpClient = new FTPClient();
ftpClient.connect(InetAddress.getByName(server));
ftpClient.login(user, password);
ftpClient.changeWorkingDirectory(serverRoad);
ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

BufferedInputStream buffIn = null;
buffIn = new BufferedInputStream(new FileInputStream(file));
ftpClient.enterLocalPassiveMode();
ftpClient.storeFile("test.txt", buffIn);
buffIn.close();
ftpClient.logout();
ftpClient.disconnect();

ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));

    FTPClient f=FTPClient();
    FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
    conf.setServerLanguageCode("fr");
    f.configure(conf);
    f.connect(server);
    f.login(username, password);
    FTPFile[] files = listFiles(directory);
     */
