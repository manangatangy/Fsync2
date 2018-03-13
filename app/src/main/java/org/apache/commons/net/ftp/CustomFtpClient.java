package org.apache.commons.net.ftp;

import org.apache.commons.net.ftp.parser.MLSxEntryParser;
import org.apache.commons.net.io.Util;

import java.io.IOException;
import java.net.Socket;

/**
 * @author david
 * @date 14 Mar 2018.
 */

public class CustomFtpClient extends FTPClient {

    private FTPClientConfig __configuration;

    @Override
    public void configure(FTPClientConfig config) {
        super.configure(config);
        this.__configuration = config;
    }

    public FTPFile[] mlistDir(String pathname) throws IOException
    {
        FTPListParseEngine engine = initiateMListParsing( pathname);
        return engine.getFiles();
    }

    private FTPListParseEngine initiateMListParsing(String pathname) throws IOException
    {
        Socket socket = _openDataConnection_(FTPCmd.MLSD, pathname);
        FTPListParseEngine engine = new FTPListParseEngine(MLSxEntryParser.getInstance(), __configuration);
        if (socket == null)
        {
            return engine;
        }

        try {
            engine.readServerList(socket.getInputStream(), getControlEncoding());
        }
        finally {
            Util.closeQuietly(socket);
            completePendingCommand();
        }
        return engine;
    }

}
