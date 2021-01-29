package org.lkop.MINIC2C;

import org.lkop.MINIC2C.lexerparsergenerated.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
        try (InputStream input_stream = new FileInputStream("input/test.txt")) {
            CharStream char_streams = CharStreams.fromStream(input_stream);
            MINICLexer lexer = new MINICLexer(char_streams);
            MINICParser parser = new MINICParser(new CommonTokenStream(lexer));
            ParseTree tree = parser.compileUnit();
            System.out.println(tree.toStringTree());

            STPrinterVisitor st_printer = new STPrinterVisitor("st");
            tree.accept(st_printer);

            ASTGeneratorVisitor ast_gen = new ASTGeneratorVisitor();
            tree.accept(ast_gen);

            CCompileUnit r = ast_gen.getRoot();
            System.out.println("Done");

            ASTPrinterVisitor ast_printer = new ASTPrinterVisitor("ast");
            ast_gen.getRoot().accept(ast_printer);

            MINIC2CTranslationVisitor c_gen = new MINIC2CTranslationVisitor();
            ast_gen.getRoot().accept(c_gen);

            CodeContainer r2 = c_gen.getRoot();
            System.out.println(r2.code.toString());


        }catch (IOException e) {
            System.out.println("File not found.");
        }
    }
}
