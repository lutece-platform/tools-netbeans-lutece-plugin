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

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

import freemarker.core.ParseException;

import org.netbeans.lutece.freemarker.parser.FTLParser.FTLParserResult;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;

public class FTLSyntaxErrorHighlightingTask extends ParserResultTask {

    @Override
    public void run (Result result, SchedulerEvent event) {
        try {
            FTLParserResult sjResult = (FTLParserResult) result;
            List<ParseException> syntaxErrors = sjResult.getErrors();
            Document document = result.getSnapshot ().getSource ().getDocument (false);
            List<ErrorDescription> errors = new ArrayList<> ();
            for (ParseException syntaxError : syntaxErrors) {
                int start = NbDocument.findLineOffset ((StyledDocument) document, syntaxError.lineNumber - 1) + syntaxError.columnNumber - 1;
                int end = NbDocument.findLineOffset ((StyledDocument) document, syntaxError.endLineNumber - 1) + syntaxError.endColumnNumber;

				ErrorDescription errorDescription = ErrorDescriptionFactory.createErrorDescription(
					Severity.ERROR,
					syntaxError.getMessage (),
					document,
					document.createPosition(start),
					document.createPosition(end)
				);
                errors.add (errorDescription);
            }
            HintsController.setErrors (document, "freemarker", errors);
        } catch (BadLocationException ex1) {
            Exceptions.printStackTrace (ex1);
        //} catch (org.netbeans.modules.parsing.spi.ParseException ex1) {
          //  Exceptions.printStackTrace (ex1);
        }
    }

    @Override
    public int getPriority () {
        return 100;
    }

    @Override
    public Class<? extends Scheduler> getSchedulerClass () {
        return Scheduler.EDITOR_SENSITIVE_TASK_SCHEDULER;
    }

    @Override
    public void cancel () {
    }

}