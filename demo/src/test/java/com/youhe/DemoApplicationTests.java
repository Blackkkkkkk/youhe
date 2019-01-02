package com.youhe;

import org.activiti.engine.ProcessEngine;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.InputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	ProcessEngine processEngine;

	@Test
	public void contextLoads() {
	}


	@Test
	public void Export() {

		String deploymentId = "4";

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
				FileUtils.copyInputStreamToFile(in, new File("d:\\" + name));
				in.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}
	}
}
