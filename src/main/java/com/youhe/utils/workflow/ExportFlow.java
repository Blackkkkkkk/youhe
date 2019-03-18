package com.youhe.utils.workflow;

import org.activiti.engine.ProcessEngine;

import java.io.InputStream;
import java.util.List;

//import org.apache.commons.io.FileUtils;

/**
 * Created by xiaoqiang on 2018/12/10.
 */
public class ExportFlow {


    //导出流程的PNG和XML文件
    /*
    *@Parem processEngine 流程引擎
    *Parem  Id   流程部署Id
    */
    public void Export(ProcessEngine processEngine, String Id) {

        String deploymentId = Id;
        List<String> names = processEngine.getRepositoryService()
                .getDeploymentResourceNames(deploymentId);
        for (String name : names) {
            System.out.println(name);
            InputStream in = processEngine.getRepositoryService()
                    .getResourceAsStream(deploymentId, name);
            // 将文件保存到本地磁盘
            /*
             * OutputStream out = new FileOutputStream(new File("d:\\" + name));
			 * byte[] b = new byte[1024]; int len = 0; while((len =
			 * in.read(b))!=-1) { out.write(b, 0, len); } out.close();
			 */
            try {
//                FileUtils.copyInputStreamToFile(in, new File("d:\\" + name));
                in.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        }
    }

}
