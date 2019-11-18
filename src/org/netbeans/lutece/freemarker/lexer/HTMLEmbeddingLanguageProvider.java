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

import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.netbeans.api.html.lexer.HTMLTokenId;
import org.netbeans.api.lexer.InputAttributes;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.LanguagePath;
import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.LanguageEmbedding;
import org.netbeans.spi.lexer.LanguageProvider;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = LanguageProvider.class)
public class HTMLEmbeddingLanguageProvider extends LanguageProvider {

    private Language embeddedLanguage;

    @Override
    public Language<?> findLanguage(String mimeType) {
        return HTMLTokenId.language();
    }

    @Override
    public LanguageEmbedding<?> findLanguageEmbedding(Token<?> token, LanguagePath languagePath, InputAttributes inputAttributes) {
        initLanguage();

        if (languagePath.mimePath().equals("text/html")) {
            if (token.id().name().equals("TEXT")) {
                return LanguageEmbedding.create(embeddedLanguage, 0, 0, false);
            }
        }

        return null;
    }

    private void initLanguage() {
        embeddedLanguage = MimeLookup.getLookup("text/x-ftl").lookup(Language.class);

        if (embeddedLanguage == null) {
            throw new NullPointerException("Can't find language for embedding");
        }

    }

}
