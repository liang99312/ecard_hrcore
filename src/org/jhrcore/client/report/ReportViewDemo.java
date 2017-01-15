package org.jhrcore.client.report;
 
import java.io.*;     
  
import com.fr.base.FRContext;   
import com.fr.base.dav.LocalEnv;
import com.fr.base.file.DatasourceManager;
import com.fr.report.*;   
import com.fr.report.io.*;   
import com.fr.view.PreviewFrame;
import com.fr.view.PreviewFrame;
import com.fr.view.PreviewPane;
import javax.swing.JFrame;
  
public class ReportViewDemo {         
    public static void main(String[] args) throws Exception {
         // 报表运行环境路径, WEB-INF目录所放的位置
         String envPath = "D:/FineSoft/FineReport/workspase/engine/WebReport/WEB-INF";
         // 设置当前报表运行环境, 报表预览时需要一个运行环境
         // 没有WEB-INF目录时, 路径设置为null. FRContext.setCurrentEnv(new LocalEnv(null));
         FRContext.setCurrentEnv(new LocalEnv(null));

         // 设置数据源
         // 因为数据源将会被导入，所以group.cpt只要保存，不需要导出为内置数据报表
         File cptFile = new File("C:\\FineReport6.2\\WebReport\\WEB-INF\\reportlets\\com\\demo\\basic\\finance3.cpt");
         try {
             TemplateImporter templateImporter = new TemplateImporter();
             WorkBook workBook = templateImporter.generate(new FileInputStream(cptFile));
             

             // datasource.xml 放在报表运行环境下的resource/datasource.xml
             // 如果没有设置datasourceManager, 报表执行时默认自动从这个地方读取, 具体如下:
             // 这里采取默认从报表运行环境读取，不另外设置datasource.xml
             
                DatasourceManager datasourceManager = new DatasourceManager();
                FRContext.setDatasourceManager(datasourceManager);
                try {
                    // 这边也可以用其他的位置代替比如"D:\\datasource.xml"
                    InputStream in = new FileInputStream("C:\\FineReport6.2\\WebReport\\WEB-INF\\resources\\datasource.xml");
                    datasourceManager.readInputStreamXML(in);
                } catch (Exception e) {
                    e.printStackTrace();
                }
              

             // 执行报表
             WorkBookTemplate excuteReport = workBook.execute(java.util.Collections.EMPTY_MAP);
/*
             PreviewFrame previewFrame = new PreviewFrame();
             previewFrame.print(excuteReport);
             previewFrame.setVisible(true);
*/

             PreviewPane pp = new PreviewPane();
        pp.print(excuteReport);

        JFrame fm = new JFrame();
        fm.setSize(600, 600);
        fm.getContentPane().add(pp);
        fm.setVisible(true);
         } catch(Exception e) {
             e.printStackTrace();
         }  
        /*
    	InputStream inputStream = new FileInputStream(new File("C:\\FineReport6.2\\WebReport\\WEB-INF\\reportlets\\com\\demo\\basic\\finance3.cpt"));
    	TemplateImporter templateImporter = new TemplateImporter(inputStream);   
        Report report = templateImporter.generateReport();   
           
        //设置数据源管理器，只需要读取datasource.xml文件即可   
        String datasourceFilePath = "C:\\FineReport6.2\\WebReport\\WEB-INF\\resources\\datasource.xml";
        FileInputStream datasourceFileInputStream = new FileInputStream(datasourceFilePath);   
        DatasourceManager datasourceManager = new DatasourceManager();  
        datasourceManager.readInputStreamXML(datasourceFileInputStream);   
        FRContext.setDatasourceManager(datasourceManager);
        //FRContext.getFRContext().setDatasourceManager();
           
        //PreviewFrame previewFrame = new PreviewFrame();   
        //previewFrame.print(report.execute());   
        //previewFrame.setVisible(true);
        PreviewPane pp = new PreviewPane();
        pp.print(report);
        
        JFrame fm = new JFrame();
        fm.setSize(600, 600);
        fm.getContentPane().add(pp);
        fm.setVisible(true);*/
    }   
} 
