lexer grammar MINICLexer;

@header {
package org.lkop.MINIC2C.lexerparsergenerated;
}

/*
 * Lexer Rules
 */

INT:'int';
LONG:'long';
FLOAT:'float';
DOUBLE:'double';
BOOLEAN:'boolean';

FUNCTION :'function';
RETURN :'return'; 
IF:'if';
ELSE:'else';
WHILE:'while';
DO:'do';
FOR:'for';
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
COLON:':';
SEMICOLON:';';
LP:'(';
RP:')';
LB:'{';
RB:'}';
LSB:'[';
RSB:']';
COMMA:',';
ASSIGN:'=';

// Identifiers - Numbers
IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;
NUMBER: '0'|[1-9][0-9]*;

// Whitespace
WS: [ \r\n\t]-> skip;