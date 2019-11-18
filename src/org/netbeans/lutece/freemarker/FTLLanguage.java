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

package org.netbeans.lutece.freemarker;

import org.netbeans.lutece.freemarker.comment.FTLCommentHandler;
import org.netbeans.api.lexer.Language;
import org.netbeans.lutece.freemarker.lexer.FTLTokenId;
import org.netbeans.lutece.freemarker.parser.FTLParser;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.csl.spi.CommentHandler;

/**
 *
 * @author Rafał Ostanek
 */
@LanguageRegistration(mimeType = "text/x-ftl")
public class FTLLanguage extends DefaultLanguageConfig {

    @Override
    public Language<FTLTokenId> getLexerLanguage() {
        return FTLTokenId.getLanguage();
    }

    @Override
    public String getDisplayName() {
        return "FTL";
    }

    @Override
    public Parser getParser() {
        return new FTLParser();
    }

    @Override
    public CommentHandler getCommentHandler() {
        return new FTLCommentHandler();
    }
}
