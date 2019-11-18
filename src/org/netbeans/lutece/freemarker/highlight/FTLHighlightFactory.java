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

package org.netbeans.lutece.freemarker.highlight;

import javax.swing.text.Document;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.highlighting.HighlightsLayer;
import org.netbeans.spi.editor.highlighting.HighlightsLayerFactory;
import org.netbeans.spi.editor.highlighting.ZOrder;

/**
 *
 * @author rostanek
 */
@MimeRegistration(mimeType = "text/x-ftl", service = HighlightsLayerFactory.class)
public class FTLHighlightFactory implements HighlightsLayerFactory {

    public static FTLHighlighter getHighlighter(Document doc) {
        FTLHighlighter highlighter = (FTLHighlighter) doc.getProperty(FTLHighlighter.class);
        if (highlighter == null) {
            doc.putProperty(FTLHighlighter.class, highlighter = new FTLHighlighter(doc));
        }
        return highlighter;
    }

    @Override
    public HighlightsLayer[] createLayers(Context context) {
        return new HighlightsLayer[]{
            HighlightsLayer.create(
            FTLHighlighter.class.getName(),
            ZOrder.CARET_RACK.forPosition(2000),
            true,
            getHighlighter(context.getDocument()).getHighlightsBag())
        };
    }

}
