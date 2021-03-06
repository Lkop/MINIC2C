package org.lkop.MINIC2C;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.lkop.MINIC2C.lexerparsergenerated.MINICLexer;
import org.lkop.MINIC2C.lexerparsergenerated.MINICParser;
import org.lkop.MINIC2C.lexerparsergenerated.MINICParserBaseVisitor;

import java.util.HashMap;
import java.util.Stack;


public class ASTGeneratorVisitor extends MINICParserBaseVisitor<Integer> {

    private CCompileUnit root;
    private Stack<ASTElement> parents = new Stack<>();
    private Stack<Integer> parents_ctx = new Stack<>();
    private HashMap<String, ASTElement> var_st = new HashMap<>();
    private HashMap<String, ASTElement> fun_st = new HashMap<>();
    private boolean from_fun = false;

    public CCompileUnit getRoot() {
        return root;
    }

    @Override
    public Integer visitCompileUnit(MINICParser.CompileUnitContext ctx) {
        CCompileUnit new_node = new CCompileUnit(-1);
        root = new_node;

        parents.push(new_node);
        parents_ctx.push(CCompileUnit.CT_COMPILEUNIT_STATEMENTS);
        for (MINICParser.StatementContext statement_context : ctx.statement()) {
            super.visit(statement_context);
        }
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CCompileUnit.CT_COMPILEUNIT_FUNCTIONDEFINITIONS);
        for (MINICParser.FunctionDefinitionContext function_definition_context : ctx.functionDefinition()) {
            super.visit(function_definition_context);
        }
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitFunctionDefinition(MINICParser.FunctionDefinitionContext ctx) {
        ASTElement parent = parents.peek();

        CFunctionDefinition new_node = new CFunctionDefinition(parents_ctx.peek());
        parent.addChild(new_node);

        from_fun = true;

        parents.push(new_node);
        parents_ctx.push(CFunctionDefinition.CT_NAME);
        super.visit(ctx.IDENTIFIER());
        parents_ctx.pop();
        parents.pop();

        if (ctx.fargs() != null) {
            parents.push(new_node);
            parents_ctx.push(CFunctionDefinition.CT_ARGS);
            super.visit(ctx.fargs());
            parents_ctx.pop();
            parents.pop();
        }

        parents.push(new_node);
        parents_ctx.push(CFunctionDefinition.CT_BODY);
        super.visit(ctx.compoundStatement());
        parents_ctx.pop();
        parents.pop();

        from_fun = false;

        return 0;
    }

    @Override
    public Integer visitStatement_ExpressionStatement(MINICParser.Statement_ExpressionStatementContext ctx) {
        ASTElement parent = parents.peek();

        CExpressionStatement new_node = new CExpressionStatement(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CExpressionStatement.CT_EXRESSION);
        super.visit(ctx.expression());
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitStatement_ReturnStatement(MINICParser.Statement_ReturnStatementContext ctx) {
        ASTElement parent = parents.peek();

        CReturnStatement new_node = new CReturnStatement(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CReturnStatement.CT_EXRESSION);
        super.visit(ctx.expression());
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitStatement_BreakStatement(MINICParser.Statement_BreakStatementContext ctx) {
        ASTElement parent = parents.peek();

        CBreakStatement new_node = new CBreakStatement(parents_ctx.peek());
        parent.addChild(new_node);

        return 0;
    }

    @Override
    public Integer visitIfstatement(MINICParser.IfstatementContext ctx) {
        ASTElement parent = parents.peek();

        CIf new_node = new CIf(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CIf.CT_IF_CONDITION);
        super.visit(ctx.condition());
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CIf.CT_IF_STATEMENT);
        super.visit(ctx.statement(0));
        parents_ctx.pop();
        parents.pop();

        if(ctx.statement(1) != null) {
            parents.push(new_node);
            parents_ctx.push(CIf.CT_ELSE_STATEMENT);
            super.visit(ctx.statement(1));
            parents_ctx.pop();
            parents.pop();
        }

        return 0;
    }

    @Override
    public Integer visitWhilestatement(MINICParser.WhilestatementContext ctx) {
        ASTElement parent = parents.peek();

        CWhile new_node = new CWhile(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CWhile.CT_WHILE_CONDITION);
        super.visit(ctx.condition());
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CWhile.CT_WHILE_STATEMENT);
        super.visit(ctx.statement());
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitDowhilestatement(MINICParser.DowhilestatementContext ctx) {
        ASTElement parent = parents.peek();

        CDoWhile new_node = new CDoWhile(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CDoWhile.CT_DOWHILE_STATEMENT);
        super.visit(ctx.statement());
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CDoWhile.CT_DOWHILE_CONDITION);
        super.visit(ctx.condition());
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitForloopstatement(MINICParser.ForloopstatementContext ctx) {
        ASTElement parent = parents.peek();

        CForLoop new_node = new CForLoop(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CForLoop.CT_FORLOOP_INITIALIZATION);
        super.visit(ctx.expression(0));
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CForLoop.CT_FORLOOP_CONDITION);
        super.visit(ctx.condition());
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CForLoop.CT_FORLOOP_INCREMENT);
        super.visit(ctx.expression(1));
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CForLoop.CT_FORLOOP_STATEMENT);
        super.visit(ctx.statement());
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitCompoundStatement(MINICParser.CompoundStatementContext ctx) {
        ASTElement parent = parents.peek();

        CCompound new_node = new CCompound(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CCompound.CT_COMPOUND_STATEMENTSLIST);
        super.visit(ctx.statementList());
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitCondition(MINICParser.ConditionContext ctx) {
        ASTElement parent = parents.peek();

        CCondition new_node = new CCondition(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CCondition.CT_CONDITION_EXPRESSION);
        super.visit(ctx.expression());
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitExpr_FunctionCall(MINICParser.Expr_FunctionCallContext ctx) {
        ASTElement parent = parents.peek();

        CFunctionCall new_node = new CFunctionCall(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CFunctionCall.CT_NAME);
        super.visit(ctx.IDENTIFIER());
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CFunctionCall.CT_ARGS);
        super.visit(ctx.args());
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitExpr_DIVMULT(MINICParser.Expr_DIVMULTContext ctx) {
        ASTElement parent = parents.peek();
        ASTElement new_node;

        switch (ctx.op.getType()) {
            case MINICLexer.DIV:
                new_node = new CDivision(parents_ctx.peek());
                parent.addChild(new_node);

                parents.push(new_node);
                parents_ctx.push(CDivision.CT_LEFT);
                super.visit(ctx.expression(0));
                parents_ctx.pop();
                parents.pop();

                parents.push(new_node);
                parents_ctx.push(CDivision.CT_RIGHT);
                super.visit(ctx.expression(1));
                parents_ctx.pop();
                parents.pop();
                break;
            case MINICLexer.MULT:
                new_node = new CMultiplication(parents_ctx.peek());
                parent.addChild(new_node);

                parents.push(new_node);
                parents_ctx.push(CMultiplication.CT_LEFT);
                super.visit(ctx.expression(0));
                parents_ctx.pop();
                parents.pop();

                parents.push(new_node);
                parents_ctx.push(CMultiplication.CT_RIGHT);
                super.visit(ctx.expression(1));
                parents_ctx.pop();
                parents.pop();
                break;
        }
        return 0;
    }

    @Override
    public Integer visitExpr_PLUSMINUS(MINICParser.Expr_PLUSMINUSContext ctx) {
        ASTElement parent = parents.peek();
        ASTElement new_node;

        switch (ctx.op.getType()) {
            case MINICLexer.PLUS:
                new_node = new CAddition(parents_ctx.peek());
                parent.addChild(new_node);

                parents.push(new_node);
                parents_ctx.push(CAddition.CT_LEFT);
                super.visit(ctx.expression(0));
                parents_ctx.pop();
                parents.pop();

                parents.push(new_node);
                parents_ctx.push(CAddition.CT_RIGHT);
                super.visit(ctx.expression(1));
                parents_ctx.pop();
                parents.pop();
                break;
            case MINICLexer.MINUS:
                new_node = new CSubtraction(parents_ctx.peek());
                parent.addChild(new_node);

                parents.push(new_node);
                parents_ctx.push(CSubtraction.CT_LEFT);
                super.visit(ctx.expression(0));
                parents_ctx.pop();
                parents.pop();

                parents.push(new_node);
                parents_ctx.push(CSubtraction.CT_RIGHT);
                super.visit(ctx.expression(1));
                parents_ctx.pop();
                parents.pop();
                break;
        }
        return 0;
    }

    @Override
    public Integer visitExpr_UNARYPLUS(MINICParser.Expr_UNARYPLUSContext ctx) {
        ASTElement parent = parents.peek();

        CUnaryPlus new_node = new CUnaryPlus(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CUnaryPlus.CT_RIGHT);
        super.visit(ctx.expression());
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitExpr_UNARYMINUS(MINICParser.Expr_UNARYMINUSContext ctx) {
        ASTElement parent = parents.peek();

        CUnaryMinus new_node = new CUnaryMinus(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CUnaryMinus.CT_RIGHT);
        super.visit(ctx.expression());
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitExpr_Assignment(MINICParser.Expr_AssignmentContext ctx) {
        ASTElement parent = parents.peek();

        CAssignment new_node = new CAssignment(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CAssignment.CT_LEFT);
        super.visit(ctx.IDENTIFIER());
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CAssignment.CT_RIGHT);
        super.visit(ctx.expression());
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitExpr_ArrayElementAssignment(MINICParser.Expr_ArrayElementAssignmentContext ctx) {
        ASTElement parent = parents.peek();

        CArrayElementAssignment new_node = new CArrayElementAssignment(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CArrayElementAssignment.CT_ARRAY);
        super.visit(ctx.IDENTIFIER());
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CArrayElementAssignment.CT_POSITION);
        super.visit(ctx.expression(0));
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CArrayElementAssignment.CT_RIGHT);
        super.visit(ctx.expression(1));
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitExpr_NOT(MINICParser.Expr_NOTContext ctx) {
        ASTElement parent = parents.peek();

        CNot new_node = new CNot(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CNot.CT_RIGHT);
        super.visit(ctx.expression());
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitExpr_AND(MINICParser.Expr_ANDContext ctx) {
        ASTElement parent = parents.peek();

        CAnd new_node = new CAnd(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CAnd.CT_LEFT);
        super.visit(ctx.expression(0));
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CAnd.CT_RIGHT);
        super.visit(ctx.expression(1));
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitExpr_OR(MINICParser.Expr_ORContext ctx) {
        ASTElement parent = parents.peek();

        COr new_node = new COr(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(COr.CT_LEFT);
        super.visit(ctx.expression(1));
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(COr.CT_RIGHT);
        super.visit(ctx.expression(1));
        parents_ctx.pop();
        parents.pop();

        return 0;
    }

    @Override
    public Integer visitExpr_COMPARISON(MINICParser.Expr_COMPARISONContext ctx) {
        ASTElement parent = parents.peek();
        ASTElement new_node;

        switch (ctx.op.getType()) {
            case MINICLexer.GT:
                new_node = new CGt(parents_ctx.peek());
                parent.addChild(new_node);

                parents.push(new_node);
                parents_ctx.push(CGt.CT_LEFT);
                super.visit(ctx.expression(0));
                parents_ctx.pop();
                parents.pop();

                parents.push(new_node);
                parents_ctx.push(CGt.CT_RIGHT);
                super.visit(ctx.expression(1));
                parents_ctx.pop();
                parents.pop();
                break;
            case MINICLexer.GTE:
                new_node = new CGte(parents_ctx.peek());
                parent.addChild(new_node);

                parents.push(new_node);
                parents_ctx.push(CGte.CT_LEFT);
                super.visit(ctx.expression(0));
                parents_ctx.pop();
                parents.pop();

                parents.push(new_node);
                parents_ctx.push(CGte.CT_RIGHT);
                super.visit(ctx.expression(1));
                parents_ctx.pop();
                parents.pop();
                break;
            case MINICLexer.LT:
                new_node = new CLt(parents_ctx.peek());
                parent.addChild(new_node);

                parents.push(new_node);
                parents_ctx.push(CLt.CT_LEFT);
                super.visit(ctx.expression(0));
                parents_ctx.pop();
                parents.pop();

                parents.push(new_node);
                parents_ctx.push(CLt.CT_RIGHT);
                super.visit(ctx.expression(1));
                parents_ctx.pop();
                parents.pop();
                break;
            case MINICLexer.LTE:
                new_node = new CLte(parents_ctx.peek());
                parent.addChild(new_node);

                parents.push(new_node);
                parents_ctx.push(CLte.CT_LEFT);
                super.visit(ctx.expression(0));
                parents_ctx.pop();
                parents.pop();

                parents.push(new_node);
                parents_ctx.push(CLte.CT_RIGHT);
                super.visit(ctx.expression(1));
                parents_ctx.pop();
                parents.pop();
                break;
            case MINICLexer.EQUAL:
                new_node = new CEqual(parents_ctx.peek());
                parent.addChild(new_node);

                parents.push(new_node);
                parents_ctx.push(CEqual.CT_LEFT);
                super.visit(ctx.expression(0));
                parents_ctx.pop();
                parents.pop();

                parents.push(new_node);
                parents_ctx.push(CEqual.CT_RIGHT);
                super.visit(ctx.expression(1));
                parents_ctx.pop();
                parents.pop();
                break;
            case MINICLexer.NEQUAL:
                new_node = new CNequal(parents_ctx.peek());
                parent.addChild(new_node);

                parents.push(new_node);
                parents_ctx.push(CNequal.CT_LEFT);
                super.visit(ctx.expression(0));
                parents_ctx.pop();
                parents.pop();

                parents.push(new_node);
                parents_ctx.push(CNequal.CT_RIGHT);
                super.visit(ctx.expression(1));
                parents_ctx.pop();
                parents.pop();
                break;
        }
        return 0;
    }

    @Override
    public Integer visitDeclaration_Array(MINICParser.Declaration_ArrayContext ctx) {
        ASTElement parent = parents.peek();

        CDeclarationArray new_node = new CDeclarationArray(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CDeclarationArray.CT_NAME);
        super.visit(ctx.IDENTIFIER());
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CDeclarationArray.CT_NUMELEMENTS);
        super.visit(ctx.NUMBER());
        parents_ctx.pop();
        parents.pop();

        if (ctx.args() != null) {
            parents.push(new_node);
            parents_ctx.push(CDeclarationArray.CT_ELEMENTS);
            super.visit(ctx.args());
            parents_ctx.pop();
            parents.pop();
        }

        return 0;
    }

    @Override
    public Integer visitDeclaration_TypeArray(MINICParser.Declaration_TypeArrayContext ctx) {
        return super.visitDeclaration_TypeArray(ctx);
    }

    @Override
    public Integer visitTerminal(TerminalNode node) {
        ASTElement parent = parents.peek();
        ASTElement new_node;

        switch (node.getSymbol().getType()) {
            case MINICLexer.NUMBER:
                new_node = new CNUMBER(parents_ctx.peek(), node.getText());
                parent.addChild(new_node);
                break;
            case MINICLexer.IDENTIFIER:
                if (from_fun) {
                    if(fun_st.containsKey(node.getText())){
                        new_node = fun_st.get(node.getText());
                    }else{
                        new_node = new CIDENTIFIER(parents_ctx.peek(), node.getText());
                        fun_st.put(node.getText(), new_node);
                    }
                }else{
                    if(var_st.containsKey(node.getText())){
                        new_node = var_st.get(node.getText());
                    }else{
                        new_node = new CIDENTIFIER(parents_ctx.peek(), node.getText());
                        var_st.put(node.getText(), new_node);
                    }
                }
                parent.addChild(new_node, parents_ctx.peek());
                break;
        }
        return 0;
    }
}
