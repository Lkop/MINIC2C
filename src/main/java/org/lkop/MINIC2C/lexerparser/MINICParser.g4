parser grammar MINICParser;

@header {
package org.lkop.MINIC2C.lexerparsergenerated;
}

options {
    tokenVocab = MINICLexer;
}

/*
 * Parser Rules
 */


compileUnit : (statement|functionDefinition)+
			;

functionDefinition : FUNCTION IDENTIFIER LP fargs? RP compoundStatement					
				   ;

statement : expression QM		#statement_ExpressionStatement
		  | ifstatement			#statement_IfStatement
		  | whilestatement		#statement_Whilestatement
		  | compoundStatement	#statement_CompoundStatement
		  | RETURN expression QM #statement_ReturnStatement
		  | BREAK QM			 #statement_BreakStatement
		  ;

ifstatement : IF LP condition RP statement (ELSE statement)?
			;

whilestatement : WHILE LP condition RP statement
			   ;

compoundStatement : LB RB
				  | LB statementList RB
				  ;

statementList : (statement)+ 
			  ;

condition : expression
          ;

expression : NUMBER											            #expr_NUMBER
		   | IDENTIFIER										            #expr_IDENTIFIER
		   | IDENTIFIER  LP args RP                                     #expr_FunctionCall
		   | expression op=(DIV|MULT) expression 			            #expr_DIVMULT
            | expression op=(PLUS|MINUS) expression                     #expr_PLUSMINUS
		   | PLUS expression								            #expr_UNARYPLUS
		   | MINUS expression								            #expr_UNARYMINUS
		   | LP expression RP								            #expr_PARENTHESIS
		   | IDENTIFIER ASSIGN expression					            #expr_Assignment
		   | NOT expression									            #expr_NOT
	       | expression AND expression						            #expr_AND
		   | expression OR expression						            #expr_OR
		   | expression op=(GT|GTE|LT|LTE|EQUAL|NEQUAL) expression		#expr_COMPARISON
		   ;

args : (expression (COMMA)?)+
	 ;

fargs : (IDENTIFIER (COMMA)?)+
	  ;
