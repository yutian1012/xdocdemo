package org.ipph.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

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
		
		setTableList(report, context);
		
		System.out.println("输出文档");
		
		//第三步：输出文档镀锡
		OutputStream out = new FileOutputStream(new File(filePath+"Test_out.docx"));
		report.process(context, out);
		
		System.out.println("文档输出结束！");
	}
	
	public static void setTableList(IXDocReport report,IContext context){
		
		FieldsMetadata metadata = new FieldsMetadata();
		metadata.addFieldAsList("userList.xh");
		metadata.addFieldAsList("userList.username");
		metadata.addFieldAsList("userList.graduate");
		metadata.addFieldAsList("userList.email");
		report.setFieldsMetadata(metadata);
		
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		
		for(int i=0;i<5;i++) {
			Map<String,Object> data=new HashMap<String, Object>();
			data.put("xh",i+1);
			data.put("username", "张三"+i);
			data.put("graduate", "硕士"+i);
			data.put("email","test"+i+"@qq.com");
			
			result.add(data);
		}
		
		context.put("userList", result);
	}
}
