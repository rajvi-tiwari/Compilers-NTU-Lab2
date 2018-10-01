package test;

//import static com.sun.tools.internal.ws.wsdl.parser.Util.fail;
import static org.junit.Assert.fail;

import java.io.StringReader;

import lexer.Lexer;

import org.junit.Test;

import parser.Parser;

public class ParserTests {
	private void runtest(String src) {
		runtest(src, true);
	}

	private void runtest(String src, boolean succeed) {
		Parser parser = new Parser();
		try {
			parser.parse(new Lexer(new StringReader(src)));
			if(!succeed) {
				fail("Test was supposed to fail, but succeeded");
			}
		} catch (beaver.Parser.Exception e) {
			if(succeed) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		} catch (Throwable e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testEmptyModule() {
		runtest("module Test { }");
	}

	@Test
	public void rt1()		// Module+import test with false success
	{
		runtest("module Test { " +
				"import scanner " +
				"}", false);
	}

	@Test
	public void rt2()		// Module+import test with true success
	{
		runtest("module Test { " +
				"import scanner ;" +
				"}");
	}

	@Test
	public void rt3()		//Import+Func Declaration+ParameterList+StatementList w/ prim type test
	{
		runtest("module Test { " +
				"import scanner ;" +
				"public int dummy(int first, int last) {\n" +
				" scanner s; \n" +
				"input i;\n" +
				"}\n" +
				"}");
	}

	@Test
	public void rt4()		//Import+Field Declaration
	{
		runtest("module Test { " +
				"import scanner ;" +
				"public int dummy;" +
				"}");
	}

	@Test
	public void rt5()		//Import+Type Declaration: success = false test
	{
		runtest("module Test { " +
				"import scanner ;" +
				"public type dummy == \"Rajvi is awesome\"; \n" +
				"}", false);
	}

	@Test
	public void rt6()		//Import+Type Declaration: success = true test
	{
		runtest("module Test { " +
				"import scanner ;" +
				"public type dummy = \"Rajvi is awesome\"; \n" +
				"}");
	}

	@Test
	public void rt7()		//Import+Func Declaration + Array Type (boolean 2D array)
	{
		runtest("module Test { " +
				"import scanner ;" +
				"public boolean[][] dummy(int first, int last) {\n" +
				" scanner s; \n" +
				"input i;\n" +
				"}\n" +
				"}");
	}

	@Test
	public void rt8()		// Empty Parameter List + Statement Block
	{
		runtest("module Test { " +
				"import scanner ;" +
				"public int dummy() {\n" +
				"{ scanner s; }" +
				"}\n" +
				"}");
	}

	@Test
	public void rt9()		// Empty Parameter List + if-else statement
	{
		runtest("module Test { " +
				"import scanner ;" +
				"public int dummy() { if(rajvi[0][3] == \"dumb\")\n" +
				"\treturn rajvi[0][3] =\"smart lol\";" +
				"\telse return;"+
				"}\n" +
				"}");
	}

}
