package com.wolfbang.fsync.ftpservice;

import android.util.Log;

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

                    FTPFile[] files = ftpClient.mlistDir();
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
