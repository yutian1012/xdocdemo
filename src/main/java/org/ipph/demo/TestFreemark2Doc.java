package org.ipph.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public class TestFreemark2Doc {
	
	private static String filePath="D:/xdoc/";
	
	public static void main(String[] args)throws Exception {
		InputStream in = TestFreemark2Doc.class.getResourceAsStream("/template/Test.docx");
		
		//第一步：创建report
		IXDocReport report = XDocReportRegistry.getRegistry()
				.loadReport(in, TemplateEngineKind.Freemarker);
		
		//第二步：从report中获取上下文环境，并注册内容对象
		IContext context = report.createContext();
		context.put("name", "张三");
		context.put("age",33);
		
		//第三步：输出文档镀锡
		OutputStream out = new FileOutputStream(new File(filePath+"Test_out.docx"));
		report.process(context, out);
	}
}
