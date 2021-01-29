package org.lkop.MINIC2C;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.lkop.MINIC2C.lexerparsergenerated.MINICLexer;
import org.lkop.MINIC2C.lexerparsergenerated.MINICParser;
import org.lkop.MINIC2C.lexerparsergenerated.MINICParserBaseVisitor;

import java.util.Stack;

public class ASTGeneratorVisitor extends MINICParserBaseVisitor {

    private CCompileUnit root;
    private Stack<ASTElement> parents = new Stack<>();
    private Stack<Integer> parents_ctx = new Stack<>();

    public CCompileUnit getRoot() {
        return root;
    }

    @Override
    public Integer visitCompileUnit(MINICParser.CompileUnitContext ctx) {
        CCompileUnit new_node = new CCompileUnit(-1);
        root = new_node;

        parents.push(new_node);
        parents_ctx.push(CCompileUnit.CT_COMPILEUNIT_STATEMENTS);
        for (MINICParser.StatementContext statementContext : ctx.statement()) {
            super.visit(statementContext);
        }
        parents_ctx.pop();
        parents.pop();


        return 0;
    }

    @Override
    public Integer visitIfstatement(MINICParser.IfstatementContext ctx) {
        ASTElement parent = parents.peek();

        CIf new_node = new CIf(parents_ctx.peek());
        parent.addChild(new_node);

        parents.push(new_node);
        parents_ctx.push(CIf.CT_IF_EXPRESSION);
        super.visit(ctx.expression());
        parents_ctx.pop();
        parents.pop();

        parents.push(new_node);
        parents_ctx.push(CIf.CT_IF_STATEMENT);
        super.visit(ctx.statement(0));
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
    public Integer visitTerminal(TerminalNode node) {
        ASTElement parent, new_node;

        switch (node.getSymbol().getType()) {
            case MINICLexer.NUMBER:
                parent = parents.peek();
                new_node = new CNUMBER(parents_ctx.peek(), node.getText());
                parent.addChild(new_node);
                break;
            case MINICLexer.IDENTIFIER:
                parent = parents.peek();
                new_node = new CIDENTIFIER(parents_ctx.peek(), node.getText());
                parent.addChild(new_node);
                break;
        }
        return 0;
    }
}
