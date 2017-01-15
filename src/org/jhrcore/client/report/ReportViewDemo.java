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
         // �������л���·��, WEB-INFĿ¼���ŵ�λ��
         String envPath = "D:/FineSoft/FineReport/workspase/engine/WebReport/WEB-INF";
         // ���õ�ǰ�������л���, ����Ԥ��ʱ��Ҫһ�����л���
         // û��WEB-INFĿ¼ʱ, ·������Ϊnull. FRContext.setCurrentEnv(new LocalEnv(null));
         FRContext.setCurrentEnv(new LocalEnv(null));

         // ��������Դ
         // ��Ϊ����Դ���ᱻ���룬����group.cptֻҪ���棬����Ҫ����Ϊ�������ݱ���
         File cptFile = new File("C:\\FineReport6.2\\WebReport\\WEB-INF\\reportlets\\com\\demo\\basic\\finance3.cpt");
         try {
             TemplateImporter templateImporter = new TemplateImporter();
             WorkBook workBook = templateImporter.generate(new FileInputStream(cptFile));
             

             // datasource.xml ���ڱ������л����µ�resource/datasource.xml
             // ���û������datasourceManager, ����ִ��ʱĬ���Զ�������ط���ȡ, ��������:
             // �����ȡĬ�ϴӱ������л�����ȡ������������datasource.xml
             
                DatasourceManager datasourceManager = new DatasourceManager();
                FRContext.setDatasourceManager(datasourceManager);
                try {
                    // ���Ҳ������������λ�ô������"D:\\datasource.xml"
                    InputStream in = new FileInputStream("C:\\FineReport6.2\\WebReport\\WEB-INF\\resources\\datasource.xml");
                    datasourceManager.readInputStreamXML(in);
                } catch (Exception e) {
                    e.printStackTrace();
                }
              

             // ִ�б���
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
           
        //��������Դ��������ֻ��Ҫ��ȡdatasource.xml�ļ�����   
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
