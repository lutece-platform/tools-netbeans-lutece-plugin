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

package org.netbeans.lutece.freemarker.parser;

import java.util.Collection;
import java.util.Collections;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.TaskFactory;

@MimeRegistration(mimeType = "text/x-ftl", service = TaskFactory.class)
public class FTLSyntaxErrorHighlightingTaskFactory extends TaskFactory {

    @Override
    public Collection create(Snapshot snapshot) {
        return Collections.singleton(new FTLSyntaxErrorHighlightingTask());
    }

}
