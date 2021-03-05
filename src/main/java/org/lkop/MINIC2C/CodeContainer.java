package org.lkop.MINIC2C;

import org.lkop.MINIC2C.treecomponents.BaseTreeElement;
import org.lkop.MINIC2C.treecomponents.BaseVisitor;
import org.lkop.MINIC2C.treecomponents.VisitableBaseTreeElement;

import java.util.HashSet;
import java.util.List;

public class CodeContainer extends ContextedElement<CodeContainer> {

    private CodeNodeType node_type;
    private String name;
    //public StringBuilder code;
    public String code="";

    public CodeContainer(CodeNodeType node_type, String name, int context) {
        super(context);
        this.node_type = node_type;
        this.name = name;

        //code = new StringBuilder();
    }

    public void addCode(String code){
        //this.code.append(code);
        this.code += code;
    }

    public void addCode(CodeContainer cc){
        //this.code.append(code);
        this.code += cc.code;
    }

    @Override
    public CodeContainer getChild(int context) {
        return (CodeContainer)super.getChild(context);
    }

    @Override
    public CodeContainer getParent(int pos) {
        return (CodeContainer)super.getParent(pos);
    }

    @Override
    public List<CodeContainer> getChildrenInContext(int context) {
        return super.getChildrenInContext(context);
    }

    @Override
    public List<CodeContainer> getChildrenInContext(int context, int pos) {
        return super.getChildrenInContext(context, pos);
    }

    public String getName() {
        return name;
    }

    public String getGraphvizName(){
        return name+"_"+getSerialId();
    }
}

class CodeFile extends CodeContainer {
    public static final int CC_FILE_PREPROCESSOR = 0, CC_FILE_GLOBALS = 1, CC_FILE_FUNCTIONDEFINITION = 2;
    public static final String[] context_names = {
        "PREPROCESSOR_CONTEXT" ,"GLOBALS_CONTEXT", "FUNDEFS_CONTEXT"
    };

    HashSet<String> st_gvar = new HashSet<>();

    public CodeFile(int context) {
        super(CodeNodeType.CB_FILE, "CodeFile", context);
    }

    public void addPreprocessorCode(String prep_code) {
        CodeRepository repo = new CodeRepository(CodeFile.CC_FILE_PREPROCESSOR);
        repo.addCode(prep_code+"\n");
        this.addChild(repo);
    }

    public void declareGlobalVariable(String variable) {
        if(!st_gvar.contains(variable)) {
            st_gvar.add(variable);
            CodeRepository repo = new CodeRepository(CodeFile.CC_FILE_GLOBALS);
            repo.addCode("float "+variable+";\n");
            this.addChild(repo);
        }
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        CodeVisitor v = (CodeVisitor)visitor;
        if (v != null) {
            return (T) v.visitCodeFile(this);
        }
        return null;
    }
}

class CodeFunctionDefinition extends CodeContainer {
    public static final int CC_FUNCTIONDEFINITION_HEADER = 0, CC_FUNCTIONDEFINITION_BODY = 1;
    public static final String[] context_names = {
        "FUNCTIONDEFINITION_HEADER", "FUNCTIONDEFINITION_BODY"
    };

    public CodeFunctionDefinition(int context) {
        super(CodeNodeType.CB_FUNCTIONDEFINITION, "CodeFunctionDefinition", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        CodeVisitor v = (CodeVisitor)visitor;
        if (v != null) {
            return (T) v.visitCodeFunctionDefinition(this);
        }
        return null;
    }
}

class CodeMainFunctionDefinition extends CodeFunctionDefinition {

    CodeCompoundStatement main_body;

    public CodeMainFunctionDefinition(int context) {
        super(context);
        CodeRepository main_header = new CodeRepository(CodeFunctionDefinition.CC_FUNCTIONDEFINITION_HEADER);
        main_header.addCode("void main(int argc, char* argv[])");
        addChild(main_header);

        main_body = new CodeCompoundStatement(CodeFunctionDefinition.CC_FUNCTIONDEFINITION_BODY);
        addChild(main_body);
    }

    CodeCompoundStatement getMainBody(){
        return main_body;
    }
}

class CodeExpressionStatement extends CodeContainer {
    public static final int CB_EXPRESSION_BODY = 0;
    public static final String[] context_names = {
        "EXPRESSION_BODY"
    };

    public CodeExpressionStatement(int context) {
        super(CodeNodeType.CB_EXPRESSIONSTATEMENT, "CodeExpressionStatement", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        CodeVisitor v = (CodeVisitor)visitor;
        if (v != null) {
            return (T) v.visitCodeExpressionStatement(this);
        }
        return null;
    }
}

class CodeReturnStatement extends CodeContainer {
    public static final int CB_EXPRESSION_BODY = 0;
    public static final String[] context_names = {
        "RETURN_EXPRESSION_BODY"
    };

    public CodeReturnStatement(int context) {
        super(CodeNodeType.CB_RETURNSTATEMENT, "CodeReturnStatement", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        CodeVisitor v = (CodeVisitor)visitor;
        if (v != null) {
            return (T) v.visitCodeReturnStatement(this);
        }
        return null;
    }
}

class CodeIfStatement extends CodeContainer {
    public static final int CB_IF_CONDITION = 0, CB_IF_BODY = 1, CB_ELSE_BODY = 2;
    public static final String[] context_names = {
        "IF_CONDITION" ,"IF_BODY", "ELSE_BODY"
    };

    public CodeIfStatement(int context) {
        super(CodeNodeType.CB_IFSTATEMENT, "CodeIfStatement", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        CodeVisitor v = (CodeVisitor)visitor;
        if (v != null) {
            return (T) v.visitCodeIfStatement(this);
        }
        return null;
    }
}

class CodeWhileStatement extends CodeContainer {
    public static final int CB_WHILE_CONDITION = 0, CB_WHILE_BODY = 1;
    public static final String[] context_names = {
        "WHILE_CONDITION" ,"WHILE_BODY"
    };

    public CodeWhileStatement(int context) {
        super(CodeNodeType.CB_WHILESTATEMENT, "CodeWhileStatement", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        CodeVisitor v = (CodeVisitor)visitor;
        if (v != null) {
            return (T) v.visitCodeWhileStatement(this);
        }
        return null;
    }
}

class CodeCompoundStatement extends CodeContainer {
    public static final int CB_COMPOUND_BODY = 0;
    public static final String[] context_names = {
            "CB_COMPOUND_BODY"
    };

    public CodeCompoundStatement(int context) {
        super(CodeNodeType.CB_COMPOUNDSTATEMENT, "CodeCompoundStatement", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        CodeVisitor v = (CodeVisitor)visitor;
        if (v != null) {
            return (T) v.visitCodeCompoundStatement(this);
        }
        return null;
    }
}

class CodeCondition extends CodeContainer {
    public static final int CB_CONDITION = 0;
    public static final String[] context_names = {
            "CB_CONDITION"
    };

    public CodeCondition(int context) {
        super(CodeNodeType.CB_CONDITION, "CodeCondition", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        CodeVisitor v = (CodeVisitor)visitor;
        if (v != null) {
            return (T) v.visitCodeCondition(this);
        }
        return null;
    }
}

class CodeRepository extends CodeContainer {

    public CodeRepository(int context) {
        super(CodeNodeType.CB_CODEREPOSITORY, "CB_CODEREPOSITORY", context);
    }

    @Override
    public <T> T accept(BaseVisitor<? extends T> visitor) {
        CodeVisitor v = (CodeVisitor)visitor;
        if (v != null) {
            return (T) v.visitCodeRepository(this);
        }
        return null;
    }
}