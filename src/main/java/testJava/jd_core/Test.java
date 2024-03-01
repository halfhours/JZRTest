package testJava.jd_core;

import org.jd.core.v1.ClassFileToJavaSourceDecompiler;

public class Test {
    /**
     * 运行时反编译库jd_core
     */
    public static void main(String[] args) throws Exception {
        Loader loader = new Loader();
        Printer printer = new Printer();
        ClassFileToJavaSourceDecompiler decompiler = new ClassFileToJavaSourceDecompiler();
        decompiler.decompile(loader,printer,"testJava/testBot");
        String source = printer.toString();
        System.out.println(source);
    }
}
