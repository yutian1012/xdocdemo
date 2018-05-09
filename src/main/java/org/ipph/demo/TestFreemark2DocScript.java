package org.ipph.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ipph.model.Developer;
import org.ipph.model.Role;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class TestFreemark2DocScript{
	
	private static String filePath="D:/xdoc/";
	
	public static void main(String[] args)throws Exception {
		InputStream in = TestFreemark2Doc.class.getResourceAsStream("/template/Test-script.docx");
		
		//第一步：创建report
		IXDocReport report = XDocReportRegistry.getRegistry()
				.loadReport(in, TemplateEngineKind.Freemarker);
		
		//第二步：从report中获取上下文环境，并注册内容对象
		IContext context = report.createContext();
		
		setWorkField(context);
		
		setList(context);
		
		setTableList(report, context);
		
		//第三步：输出文档镀锡
		OutputStream out = new FileOutputStream(new File(filePath+"Test_out.docx"));
		report.process(context, out);
	}
	
	public static void setTableList(IXDocReport report,IContext context){
		
		/*FieldsMetadata metadata = new FieldsMetadata();
		metadata.setAfterTableCellToken("token");
		metadata.addFieldAsList("authorList.nameTitle");
		metadata.addFieldAsList("authorList.userName");
		metadata.addFieldAsList("authorList.workTitle");
		metadata.addFieldAsList("authorList.workName");
		report.setFieldsMetadata(metadata);*/
		
		FieldsMetadata metadata = new FieldsMetadata();
        metadata.setBeforeRowToken("@before-row");
        metadata.setAfterRowToken("@after-row");
        
        report.setFieldsMetadata(metadata);
		
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		
		String nameTitle="作者姓名或名称";
		String workTitle="作品署名";
		
		for(int i=0;i<5;i++) {
			Map<String,Object> data=new HashMap<String, Object>();
			data.put("nameTitle",nameTitle);
			data.put("userName", "张三"+i);
			data.put("workTitle", workTitle);
			data.put("workName","作品"+i);
			
			result.add(data);
		}
		
		context.put("authorList", result);
	}
	
	public static void setWorkField(IContext context) {
		/*Map<String,Object> work=new HashMap<String, Object>();
		work.put("finishDate","2018-02-15");
		work.put("finishAddress", "北京海淀");
		work.put("pulishDate","2018-03-02");
		work.put("publishCountry","中国");
		work.put("publishCity","北京");*/
		
		//context.put("work", work);
		
		context.put("finishDate","2018-02-15");
		context.put("finishAddress", "北京海淀");
		context.put("pulishDate","2018-03-02");
		context.put("publishCountry","中国");
		context.put("publishCity","北京");
	}
	
	public static void setList(IContext context) {
		
		context.put("firstName", "ZERR");
		context.put("first_lastName", "Pascal");
		
		
		List<Developer> developers = new ArrayList<Developer>();
		
        //developers.add( new Developer( "ZERR", "Angelo", "angelo.zerr@gmail.com" ).addRole( new Role( "Architecte" ) ).addRole( new Role("Developer" ) ) );

        developers.add( new Developer( "Leclercq", "Pascal", "pascal.leclercq@gmail.com" ).addRole( new Role("Architecte" ) ).addRole( new Role(
                                                                                                                                                  "Developer" ) ) );

        developers.add( new Developer( "Bousta", "Amine", "" ).addRole( new Role( "Developer" ) ) );

        context.put( "developers", developers );
	}
}
