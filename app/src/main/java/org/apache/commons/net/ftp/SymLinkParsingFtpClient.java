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
 * 1. {@link #mlistFile(String)} always throws {@link MalformedServerReplyException}
 * 2. {@link #mlistDir(String)} fails to detect unix symlinks
 *
 * Note that this class cannot determine the file/directory status of symlinks,
 * although mListDir(symLinkedDir) will return the contents of the dir even though
 * it is a symLinked path.
 *
 * FTPFile results (single or multi)
 * ---------------------------------
 * "."              ==> file=N,dir=Y,symlink=N
 * ".."             ==> file=N,dir=Y,symlink=N
 *
 * "symLinkFile"    ==> file=N,dir=N,symlink=Y
 * "symLinkDir"     ==> file=N,dir=N,symlink=Y
 *
 * "normalFile"     ==> file=Y,dir=N,symlink=N
 * "normalDir"      ==> file=N,dir=Y,symlink=N
 *
 */
public class SymLinkParsingFtpClient extends FTPClient {

    private FTPClientConfig __configuration;

    @Override
    public void configure(FTPClientConfig config) {
        super.configure(config);
        this.__configuration = config;
    }

    /**
     * This is identical to base class implementation {@link FTPClient#mlistDir(String)}
     * in the commons-net-3.6 source, except that this version doesn't throw an
     * MalformedServerReplyException if there is no leading space.  In fact, the
     * base class version insistence on a leading space contradicts the comment on
     * the first line of {@link MLSxEntryParser#parseFTPEntry(String)} which states:
     * "leading space means no facts are present". lol
     * Therefore this version simply omits to check for leading space.
     *
     * path-parameter
     * --------------
     * 'nonExistent'    ==> PATH_NOT_FOUND
     * 'existentFile'   ==> single-result for this path
     * 'existentDir'    ==> single-result for this path [even if path is a symLinkedDir]
     * ''               ==> same result as for "working-dir"
     * null             ==> same result as for "working-dir"
     *
     */
    public FTPFile mlistFile(String pathname) throws IOException {
        boolean success = FTPReply.isPositiveCompletion(sendCommand(FTPCmd.MLST, pathname));
        if (success){
            String reply = getReplyStrings()[1];
            /* check the response makes sense.
             * Fact(s) can be absent, so at least 3 chars are needed.
             */
            if (reply.length() < 3) {
                throw new MalformedServerReplyException("Invalid server reply (MLST): '" + reply + "'");
            }
            // TODO doesn't seem to handle date. "250-modify=hkhgkhkh" expects "modify" ?
            String entry = reply.replace("250-modify=", "modify=");
            return SymLinkParsingMLSxEntryParser.parseEntry(entry);
        } else {
            return null;
        }
    }

    /**
     * This is identical to baseclass implementation {@link FTPClient#mlistDir(String)}
     * in the commons-net-3.6 source.
     * However because {@link FTPClient#initiateMListParsing(String)} is private, it
     * cannot be override'd (if it was protected, then this method would not be
     * necessary sigh).
     *
     * path-parameter
     * --------------
     * 'nonExistent'    ==> PATH_NOT_FOUND
     * 'existentFile'   ==> PATH_NOT_FOUND
     * 'existentDir'    ==> multi-result for this path
     * ''               ==> same result as for "working-dir"
     * null             ==> same result as for "working-dir"
     *
     */
    public FTPFile[] mlistDir(String pathname) throws IOException {
        FTPListParseEngine engine = initiateMListParsingWithSymLinkDetection( pathname);
        return engine.getFiles();
    }

    /**
     * This method uses {@link SymLinkParsingMLSxEntryParser} instead of the
     * baseclass {@link MLSxEntryParser}. That is the only change from the
     * original implementation; {@link FTPClient#initiateMListParsing(String)} in
     * the commons-net-3.6 source.
     * If {@link FTPClient#initiateMListParsing(String)} were protected and not private,
     * then it could be overridden by this method, and I would not need to implement
     * {@link SymLinkParsingFtpClient#mlistDir(String)}.
     */
    private FTPListParseEngine initiateMListParsingWithSymLinkDetection(String pathname)
            throws IOException {
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
