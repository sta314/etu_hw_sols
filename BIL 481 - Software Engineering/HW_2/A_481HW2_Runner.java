import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.util.ArrayList;

public class Runner{
    public static void main(String[] args) throws Exception{
        CharStream input = CharStreams.fromStream(System.in);
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();
        
        MyJavaBaseListener myListener = new MyJavaBaseListener(tokens);
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(myListener, tree);
    
        System.out.println("Never called");
        
        for (MyJavaBaseListener.callInstance declared : myListener.declaredMethods) {
            if (!myListener.calledMethods.contains(declared))
                System.out.println(declared);
        }
        System.out.println("\nCalled but not declared");
        for (MyJavaBaseListener.callInstance called : myListener.calledMethods) {
            if (!myListener.declaredMethods.contains(called))
                System.out.println(called);
        }
    }
}



class MyJavaBaseListener extends JavaBaseListener{

    class callInstance{
        String packageName;
        String className;
        String methodName;
        
        public callInstance(String pName, String cName, String mName){
            packageName = pName;
            className = cName;
            methodName = mName;
        }

        public String toString(){
            return this.packageName + "::" + className + "::" + methodName;
        }

        public boolean equals(Object o){
            callInstance other = (callInstance) o;
            if (this.packageName.equals(other.packageName) && this.className.equals(other.className) && this.methodName.equals(other.methodName))
                return true;
            return false;
        }
    }

    String currentPackage;
    String currentClass;

    ArrayList<callInstance> declaredMethods;
    ArrayList<callInstance> calledMethods;

    public MyJavaBaseListener(TokenStream tokens){
        declaredMethods = new ArrayList<callInstance>();
        calledMethods = new ArrayList<callInstance>();
    }

    
    //We will get class's names and store them on a global variable
    @Override public void enterNormalClassDeclaration(JavaParser.NormalClassDeclarationContext ctx) { 
        currentClass = ctx.Identifier().getText();
    }
    //

    //We will get package's names and store them on a global variable
    @Override public void enterPackageDeclaration(JavaParser.PackageDeclarationContext ctx) { 
        String conceString = "";

        for (int i = 0; i < ctx.qualifiedName().Identifier().size(); i++) {
            conceString = conceString + "." + ctx.qualifiedName().Identifier().get(i).getText();
        }

        currentPackage = conceString.substring(1);
    }
    //

    //These will trigger method declerations
    @Override public void enterNonVoidMethod(JavaParser.NonVoidMethodContext ctx) {
        declaredMethods.add(new callInstance(currentPackage, currentClass, ctx.Identifier().getText()));
    }

	@Override public void enterVoidMethod(JavaParser.VoidMethodContext ctx) {
        declaredMethods.add(new callInstance(currentPackage, currentClass, ctx.Identifier().getText()));
    }
    
	@Override public void enterConstructorMethod(JavaParser.ConstructorMethodContext ctx) {
        declaredMethods.add(new callInstance(currentPackage, currentClass, ctx.Identifier().getText()));
    }

    //

    //This will trigger method call
    @Override public void enterPrimary(JavaParser.PrimaryContext ctx) {
        if (ctx.Identifier().size() > 0){
            if(ctx.Identifier().size() < 2){
                callInstance instance = new callInstance(currentPackage, currentClass, ctx.Identifier().get(ctx.Identifier().size()-1).toString());
                if (!calledMethods.contains(instance)){
                    calledMethods.add(instance);
                }
            }
                
            else{
                callInstance instance = new callInstance(currentPackage, ctx.Identifier().get(ctx.Identifier().size()-2).toString(), ctx.Identifier().get(ctx.Identifier().size()-1).toString());
                if (!calledMethods.contains(instance)){
                    calledMethods.add(instance);
                }
            }  
        }
    }



}