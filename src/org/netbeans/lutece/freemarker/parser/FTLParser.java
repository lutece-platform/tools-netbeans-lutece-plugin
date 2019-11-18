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

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeListener;

import freemarker.core.FMParser;
import freemarker.core.ParseException;
import freemarker.template.Template;
import java.io.IOException;

import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;

public class FTLParser extends Parser {

    private Snapshot snapshot;
    private FMParser freemarkerParser;
    private final List<ParseException> errors = new ArrayList<>();

    @Override
    public void parse(Snapshot snapshot, Task task, SourceModificationEvent event) {
        this.snapshot = snapshot;
        errors.clear();
        Reader reader = new StringReader(snapshot.getText().toString());
        try {
            Template tpl = new Template(snapshot.getSource().getFileObject().getNameExt(), reader);
            freemarkerParser = new FMParser(tpl, reader, false, false);
            freemarkerParser.Root();
        } catch (ParseException ex) {
            errors.add(ex);
            //ex.printStackTrace();
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
    }

    @Override
    public Result getResult(Task task) {
        return new FTLParserResult(snapshot, errors);
    }

    @Override
    public void addChangeListener(ChangeListener changeListener) {
    }

    @Override
    public void removeChangeListener(ChangeListener changeListener) {
    }

    public static class FTLParserResult extends ParserResult {

        private final List<ParseException> errors;
        private boolean valid = true;

        FTLParserResult(Snapshot snapshot, List<ParseException> errors) {
            super(snapshot);
            this.errors = errors;
            valid = errors.isEmpty();
        }

        public List<ParseException> getErrors() {
            return errors;
        }

        @Override
        protected void invalidate() {
            valid = false;
        }

        @Override
        public List<? extends Error> getDiagnostics() {
            return new ArrayList<>();
        }

    }

}
