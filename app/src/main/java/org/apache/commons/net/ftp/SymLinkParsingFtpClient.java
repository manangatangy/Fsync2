package org.apache.commons.net.ftp;

import org.apache.commons.net.ftp.parser.MLSxEntryParser;
import org.apache.commons.net.ftp.parser.SymLinkParsingMLSxEntryParser;
import org.apache.commons.net.io.Util;
import org.apache.commons.net.MalformedServerReplyException;

import java.io.IOException;
import java.net.Socket;

/**
 * @author david
 * @date 14 Mar 2018.
 */

/**
 * This class fixes two bugs with the baseclass {@link FTPClient} in the
 * commons-net-3.6 source;
 * 1. {@link #mlistDir(String)} fails to detect unix symlinks
 * 2. {@link #mlistFile(String)} always throws {@link MalformedServerReplyException}
 */
public class SymLinkParsingFtpClient extends FTPClient {

    private FTPClientConfig __configuration;

    @Override
    public void configure(FTPClientConfig config) {
        super.configure(config);
        this.__configuration = config;
    }

    /**
     * This is identical to baseclass implementation {@link FTPClient#mlistDir(String)}
     * in the commons-net-3.6 source.
     * However because {@link FTPClient#initiateMListParsing(String)} is private, it
     * cannot be override'd (if it was protected, then this method would not be
     * necessary sigh).
     */
    public FTPFile[] mlistDir(String pathname) throws IOException
    {
        FTPListParseEngine engine = initiateMListParsingWithSymLinkDetection( pathname);
        return engine.getFiles();
    }

    /**
     * This method uses {@link SymLinkParsingMLSxEntryParser} instead of the
     * baseclass {@link MLSxEntryParser}. That is the only change from the
     * original implementation; {@link FTPClient#initiateMListParsing(String)} in
     * the commons-net-3.6 source.
     */
    private FTPListParseEngine initiateMListParsingWithSymLinkDetection(String pathname)
            throws IOException
    {
        Socket socket = _openDataConnection_(FTPCmd.MLSD, pathname);
        // Alter the engine.parser to use a better implementation.
        FTPFileEntryParser parser = SymLinkParsingMLSxEntryParser.getInstance();
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
