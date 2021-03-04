package org.lkop.MINIC2C;

public enum ASTNodeType {
        NT_NA,
        NT_COMPILEUNIT,

        NT_FUNCTIONDEFINITION,

        NT_EXPRESSIONSTATEMENT,
        NT_RETURNSTATEMENT,
        NT_BREAKSTATEMENT,

        NT_IFSTATEMENT,
        NT_WHILESTATEMENT,
        NT_COMPOUNDSTATEMENT,
        NT_CONDITION,

        NT_FUNCTIONCALL,
        NT_DIVISION,
        NT_MULTIPLICATION,
        NT_ADDITION,
        NT_SUBTRACTION,
        NT_UNARYPLUS,
        NT_UNARYMINUS,
        NT_ASSIGNMENT,
        NT_NOT,
        NT_AND,
        NT_OR,
        NT_GT,
        NT_GTE,
        NT_LT,
        NT_LTE,
        NT_EQUAL,
        NT_NEQUAL,
        NT_IDENTIFIER,
        NT_NUMBER
}
