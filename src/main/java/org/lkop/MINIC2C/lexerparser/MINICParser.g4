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

statement : expression SEMICOLON		    #statement_ExpressionStatement
		  | ifstatement			            #statement_IfStatement
		  | whilestatement		            #statement_Whilestatement
		  | dowhilestatement		        #statement_DoWhileStatement
		  | forloopstatement		        #statement_ForLoopStatement
		  | compoundStatement	            #statement_CompoundStatement
		  | RETURN expression SEMICOLON     #statement_ReturnStatement
		  | BREAK SEMICOLON			        #statement_BreakStatement
		  ;

ifstatement : IF LP condition RP statement (ELSE statement)?
			;

whilestatement : WHILE LP condition RP statement
			   ;

dowhilestatement : DO statement WHILE LP condition RP SEMICOLON
                 ;

forloopstatement : FOR LP expression SEMICOLON condition SEMICOLON expression RP statement
                 ;

condition : expression
          ;

compoundStatement : LB RB
				  | LB statementList RB
				  ;

statementList : (statement)+ 
			  ;

expression : NUMBER											            #expr_NUMBER
		   | IDENTIFIER										            #expr_IDENTIFIER
		   | IDENTIFIER LP args RP                                      #expr_FunctionCall
		   | expression op=(DIV|MULT) expression 			            #expr_DIVMULT
           | expression op=(PLUS|MINUS) expression                      #expr_PLUSMINUS
		   | PLUS expression								            #expr_UNARYPLUS
		   | MINUS expression								            #expr_UNARYMINUS
		   | LP expression RP								            #expr_PARENTHESIS
		   | var_declaration                                            #expr_VarDeclaration
		   | IDENTIFIER ASSIGN expression					            #expr_Assignment
		   | IDENTIFIER LSB expression RSB ASSIGN expression            #expr_ArrayElementAssignment
		   | NOT expression									            #expr_NOT
	       | expression AND expression						            #expr_AND
		   | expression OR expression						            #expr_OR
		   | expression op=(GT|GTE|LT|LTE|EQUAL|NEQUAL) expression		#expr_COMPARISON
		   ;

var_declaration : type IDENTIFIER                                       #declaration_TypeVariable
                | IDENTIFIER LSB (NUMBER)? RSB (ASSIGN LB args RB)?        #declaration_Array
                | type IDENTIFIER LSB NUMBER RSB                        #declaration_TypeArray
                ;

type : INT
     | LONG
     | FLOAT
     | DOUBLE
     ;

args : (expression (COMMA)?)+
	 ;

fargs : (IDENTIFIER (COMMA)?)+
	  ;
