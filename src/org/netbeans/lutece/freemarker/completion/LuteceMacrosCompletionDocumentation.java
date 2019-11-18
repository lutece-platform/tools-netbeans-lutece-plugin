/*
 * Copyright (c) 2002-2019, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package org.netbeans.lutece.freemarker.completion;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import javax.swing.Action;
import org.netbeans.spi.editor.completion.CompletionDocumentation;

/**
 * LuteceMacrosCompletionDocumentation
 */
public class LuteceMacrosCompletionDocumentation implements CompletionDocumentation
{

    private static final String PATH_MACROS_PROPERTIES = "/org/netbeans/lutece/freemarker/completion/lutece_macros.properties";
    private String _strText;
    private Map<String, String> _mapDoc;

    public LuteceMacrosCompletionDocumentation(String strMacroName)
    {
        if (_mapDoc == null)
        {
            _mapDoc = new HashMap<>();
            try (InputStream input = LuteceMacrosCompletionDocumentation.class.getResourceAsStream(PATH_MACROS_PROPERTIES))
            {
                Properties prop = new Properties();
                prop.load(input);
                for( Entry entry : prop.entrySet())
                {
                    _mapDoc.put( (String) entry.getKey(), (String) entry.getValue() );
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        _strText = _mapDoc.get(strMacroName);
    }

    @Override
    public String getText()
    {
        return _strText;
    }

    @Override
    public URL getURL()
    {
        return null;
    }

    @Override
    public CompletionDocumentation resolveLink(String arg0)
    {
        return null;
    }

    @Override
    public Action getGotoSourceAction()
    {
        return null;
    }

}
