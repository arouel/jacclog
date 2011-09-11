/*******************************************************************************
 * Copyright 2011 Andre Rouel
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

/*
 * ANTLRv3 grammar for files with comma-separated values (CSV).
 *
 * This version is influenced of the CSV grammar from Nathaniel Harward and can be
 * downloaded at http://www.harward.us/~nharward/antlr/csv.g.
 *
 */

grammar CommaSeparatedValues;

options {
  language = Java;
}

@header {
  package net.sf.jacclog.csv;
  import net.sf.jacclog.csv.QuotedTextFilter;
}

@lexer::header {
  package net.sf.jacclog.csv;
}

@lexer::members {

    private final List<RecognitionException> exceptions = new ArrayList<RecognitionException>();

    public List<RecognitionException> getExceptions() {
        return exceptions;
    }

    @Override
    public void reportError(RecognitionException e) {
        super.reportError(e);
        exceptions.add(e);
    }

}

line returns [List<String> result]
scope { List fields; }
@init { $line::fields = new ArrayList(); }
    : field ( COMMA field )*
    { $result = $line::fields; }
    ;

field
    : quoted_field { $line::fields.add( QuotedTextFilter.filter($quoted_field.text) ); }
    | unquoted_field { $line::fields.add($unquoted_field.text); }
    ;

quoted_field
    : DQUOTE
    ( CHAR
    | COMMA
    | DQUOTE DQUOTE
    | NEWLINE
    )* DQUOTE
    ;

unquoted_field
    : CHAR*
    ;

CHAR
    : '\u0000' .. '\u0009'
    | '\u000b' .. '\u000c'
    | '\u000e' .. '\u0021'
    | '\u0023' .. '\u002b'
    | '\u002d' .. '\uffff'
    ;

COMMA
    : '\u002c'
    ;

DQUOTE
    : '\u0022'
    ;

NEWLINE
    : '\u000d'? '\u000a'    // DOS/Windows(\r\n) + Unix(\n)
    | '\u000d'              // Mac OS 9 and earlier(\r)
    ;
