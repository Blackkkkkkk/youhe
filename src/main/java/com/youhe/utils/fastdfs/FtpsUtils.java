package com.youhe.utils.fastdfs;/**
 * @ClassName Ftps
 * @Description TODO
 * @Author xdn
 * @Date 2019/5/239:21
 * @Version 1.0
 */

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @ClassName Ftps
 * @Description TODO
 * @Author xdn
 * @Date 2019/5/239:21
 * @Version 1.0
 */
public class FtpsUtils {

    private FtpsUtils() {
    }

    private static class FtpsUtilsInstance {
        private static final FtpsUtils ftpsUtils = new FtpsUtils();
    }

    public static FtpsUtils getInstance() {
        return FtpsUtilsInstance.ftpsUtils;
    }


    public static final String SFTP_REQ_HOST = "host";
    public static final String SFTP_REQ_PORT = "port";
    public static final String SFTP_REQ_USERNAME = "username";
    public static final String SFTP_REQ_PASSWORD = "password";
    public static final int SFTP_DEFAULT_PORT = 22;
    public static final String DESTINATION_FOLDER = "/123456/";
    ;//目标文件夹
    Session session = null;
    Channel channel = null;
    static  OutputStream out = null;
    static  InputStream is = null;
    /**
     * 创建跟和服务器的连接
     * @param sftpDetails
     * @param timeout
     * @return
     * @throws Exception
     */
    public ChannelSftp getChannel(Map<String, String> sftpDetails, int timeout) throws Exception {
        String ftpHost = sftpDetails.get(SFTP_REQ_HOST);
        String port = sftpDetails.get(SFTP_REQ_PORT);
        String ftpUserName = sftpDetails.get(SFTP_REQ_USERNAME);
        String ftpPassword = sftpDetails.get(SFTP_REQ_PASSWORD);

        int ftpPort = SFTP_DEFAULT_PORT;
        if (port != null && !port.equals("")) {
            ftpPort = Integer.valueOf(port);
        }
        JSch jsch = new JSch(); // 创建JSch对象
        session = jsch.getSession(ftpUserName, ftpHost, ftpPort); // 根据用户名，主机ip，端口获取一个Session对象
        if (ftpPassword != null) {
            session.setPassword(ftpPassword); // 设置密码
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立链接


       Channel  channel = session.openChannel("sftp"); // 打开SFTP通道
        channel.connect(); // 建立SFTP通道的连接
        return  (ChannelSftp)channel;
    }

    /**
     * 关闭连接
     * @throws Exception
     */
    public void closeChannel() throws Exception {
        if (session != null) {
            session.disconnect();
        }
        if (channel != null) {
            channel.disconnect();
        }


    }

    /**
     * 文件传输
     * @param chSftp
     * @param savePath
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public boolean outAndCloseStream(ChannelSftp chSftp, String savePath, MultipartFile multipartFile) throws Exception {
        boolean flag = false;
        byte[] buff = new byte[1024 * 1024]; // 设定每次传输的数据块大小为256KB
        int read;
                System.out.println("Start to read input stream");
                is = multipartFile.getInputStream();

               /* do {
                    // 使用OVERWRITE模式
                    read = is.read(buff, 0, buff.length);
                    if (read > 0) {
                        out.write(buff, 0, read);
                    }
                } while (read >= 0);*/
             out = chSftp.put(savePath, ChannelSftp.OVERWRITE);
            while ((read= is.read(buff, 0, buff.length)) != -1) {
                out.write(buff, 0,read);
                flag = true;
            }
                System.out.println("input stream read done.");
        closeStream();
        return flag;
    }

    /**
     * 关闭流
     * @throws Exception
     */
    public static void closeStream() {
        if (is!= null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            is = null;
        }
        if(out!= null){
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out = null;
        }
    }
}
