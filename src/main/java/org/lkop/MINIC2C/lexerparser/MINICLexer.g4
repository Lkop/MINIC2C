lexer grammar MINICLexer;

@header {
package org.lkop.MINIC2C.lexerparsergenerated;
}

/*
 * Lexer Rules
 */

FUNCTION :'function';
RETURN :'return'; 
IF:'if';
ELSE:'else';
WHILE:'while';
BREAK: 'break';

// Operators
PLUS:'+'; 
MINUS:'-';
DIV:'/'; 
MULT:'*';
OR:'||';
AND:'&&';
NOT:'!';
EQUAL:'==';
NEQUAL:'!='; 
GT:'>';
LT:'<';
GTE:'>=';
LTE:'<=';
QM:';';
LP:'(';
RP:')';
LB:'{';
RB:'}'; 
COMMA:',';
ASSIGN:'=';

// Identifiers - Numbers
IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;
NUMBER: '0'|[1-9][0-9]*;

// Whitespace
WS: [ \r\n\t]-> skip;