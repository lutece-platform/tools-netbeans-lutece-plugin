/* Copyright (C) 2015  Rafał Ostanek
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.netbeans.lutece.freemarker.lexer;

import java.io.IOException;
import java.io.InputStream;

import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerInput;
import org.netbeans.spi.lexer.LexerRestartInfo;

import freemarker.core.FMParserConstants;
import freemarker.core.FMParserTokenManager;
import freemarker.core.SimpleCharStream;
import freemarker.core.Token;
import freemarker.core.TokenMgrError;
import org.netbeans.lutece.freemarker.panel.FTLPanel;
import org.openide.util.NbPreferences;

/**
 *
 * @author Rafał Ostanek
 */
class FTLLexer implements Lexer<FTLTokenId> {

    private LexerRestartInfo<FTLTokenId> info;
    private FMParserTokenManager fmParserTokenManager;

    public FTLLexer(LexerRestartInfo<FTLTokenId> info) {
        this.info = info;
        final LexerInput input = info.input();
        InputStream istream = new InputStream() {

            @Override
            public int read() throws IOException {
                int result = input.read();
                //debug("read " + result);
                //if (result == LexerInput.EOF) {
                //    throw new IOException("LexerInput EOF");
                //}
                return result;
            }
        };
        SimpleCharStream stream = new SimpleCharStream(istream);
        fmParserTokenManager = new FMParserTokenManager(stream);
    }

    @Override
    public org.netbeans.api.lexer.Token<FTLTokenId> nextToken() {
        Token token;
        try {
            token = fmParserTokenManager.getNextToken();

        } catch (TokenMgrError err) {
            String msg = err.getMessage();
            debug(msg);
            int len = 1;
            if (msg.startsWith("You can't use")) {
                len = 2;
            } else if (msg.startsWith("Unknown directive")) {
                String dn = msg.substring(20, msg.indexOf("."));
                debug(dn);
                len = dn.length() + 2;
            } else if (msg.contains("is an existing directive")) {
                //String dn = msg.substring(0, msg.indexOf("is an"));
                //debug(dn);
                //len = dn.length();
                len = err.getEndColumnNumber() - err.getColumnNumber() + 2; // +1 to count length, +1 for <
            } else if (msg.startsWith("Naming convention mismatch")) {
                //String dn = msg.substring(len)"problematic token, ";
                len = err.getEndColumnNumber() - err.getColumnNumber() + 1;
            }
            debug("fictional STATIC_TEXT_NON_WS token with length = " + len + " to recover");
            return info.tokenFactory().createToken(FTLLanguageHierarchy.getToken(FMParserConstants.STATIC_TEXT_NON_WS), len);
        }
        FTLTokenId tokenId = FTLLanguageHierarchy.getToken(token.kind);
        debug(token.beginLine + ":" + token.beginColumn + " " + token.endLine + ":" + token.endColumn);
        debug(tokenId + " " + token.image);
        //if (info.input().readLength() < 1) {
        //    return null;
        //}
        if (token.kind == FMParserConstants.EOF) {
            debug(info.input().readLength());
            //while (info.input().readLength() > 0) {
            //    info.input().read();
            //}
            debug("EOF returning null");
            return null;
        }
        if ((token.kind == FMParserConstants.TERSE_COMMENT_END || token.kind == FMParserConstants.MAYBE_END) && token.image.endsWith(";")) {
            // this is because of weird hacks in FTL.jj
            debug("trimming ; at the end of token image");
            token.image = token.image.substring(0, token.image.length() - 1);
        }
        int length = token.image.length();
        debug("length " + length + " readLength " + info.input().readLength());
        if(tokenId == null) {
            int z = 0;
        }
        return info.tokenFactory().createToken(tokenId, length);
    }

    @Override
    public Object state() {
        return null;
    }

    @Override
    public void release() {
    }

    private void debug(Object s) {
        //-20181005 valc@ese.by make IDE slow when netbaeans.conf has console log enabled
        if(NbPreferences.forModule(FTLPanel.class).getBoolean("debug", false)) {
            System.out.println(s);
        }
    }
}
