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

    /**
     * The mListDir and initiateMListParsing methods are copied verbatim from the
     * commons-net-3.6 source. The only change to these methods is that an instance
     * of XfsfdhshshshtthXX is passed to FTPListParseEngine() ctor instead of the
     * previous parameter: MLSxEntryParser.getInstance().
     */
    public FTPFile[] mlistDir(String pathname) throws IOException
    {
        FTPListParseEngine engine = initiateMListParsing( pathname);
        return engine.getFiles();
    }

    private FTPListParseEngine initiateMListParsing(String pathname) throws IOException
    {
        Socket socket = _openDataConnection_(FTPCmd.MLSD, pathname);
        // Alter the engine.parser ...
        FTPFileEntryParser parser = MLSxEntryParser.getInstance();
        FTPListParseEngine engine = new FTPListParseEngine(parser, __configuration);
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
