package com.youhe.serviceImpl.Controller.shopController;

import com.youhe.biz.department.DepartmentBiz;
import com.youhe.biz.role.RoleBiz;
import com.youhe.biz.shop.PictureBiz;
import com.youhe.biz.shop.ShopBiz;
import com.youhe.biz.user.UserBiz;
import com.youhe.entity.department.User_Department;
import com.youhe.entity.role.User_Role;
import com.youhe.entity.shop.Picture;
import com.youhe.entity.shop.Shop;
import com.youhe.entity.user.User;
import com.youhe.serviceImpl.Controller.roleController.RoleControllerImpl;
import com.youhe.utils.R;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class ShopControllerImpl {

    private Logger log = LoggerFactory.getLogger(ShopControllerImpl.class);

    @Autowired
    private PictureBiz pictureBiz;

    @Autowired
    private ShopBiz shopBiz;


    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> uploadReport(HttpServletRequest request, HttpServletResponse response, Shop shop) {

        Map<String, Object> result = new HashMap<String, Object>();

        //调用通用接口上传文件
        return ((ShopControllerImpl) AopContext.currentProxy()).uploadFile(request, "reportFile", shop);

    }


    /**
     * 上传文件通用接口
     *
     * @param request     请求体
     * @param dstFileName html上传组件中(input中name属性)，上传文件体名称，通过此名称获取所有上传的文件map
     * @param shop        （特殊）上传报告所述报告组参数
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> uploadFile(HttpServletRequest request, String dstFileName, Shop shop) {
        Map<String, Object> ret = new HashMap<String, Object>();


// 项目在容器中实际发布运行的根路径
        //   String realPath = request.getSession().getServletContext().getRealPath("/") + "upload";

        String realPath = null;

        String pageaddr = ""; // 页面显示的路径

        if (shop.getType() == 2) {   // 用户首页轮播图保存的路径
            pageaddr = "/templates/upload/userShopIndex/carousel/";
            realPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + pageaddr;


        } else {//上传商品，保存相册的路径 按每天日期分类
            pageaddr = "/templates/upload/ShopManage/" +
                    new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
            realPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + pageaddr;

        }


        //判断保存文件的路径是否存在
        File fileUploadPath = new File(realPath);
        if (!fileUploadPath.exists()) {
            fileUploadPath.mkdir();
        }


        if (ServletFileUpload.isMultipartContent(request)) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> fileList = multipartRequest.getFiles(dstFileName);

            // String picSaveList = new String();  //保存到数据库返回的ID列

            Picture picture;

            for (MultipartFile item : fileList) {
                String fileName = "";        //当前上传文件全名称
                String fileType = "";        //当前上传文件类型
                String saveFileName = "";    //保存到服务器目录的文件名称
                String reportAddr = "";      //保存到服务器目录的文件全路径


                picture = new Picture();
                try {


                    fileName = item.getOriginalFilename();
                    fileType = item.getContentType();

                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
                    System.out.println(df.format(new Date()));// new Date()为获取当前系统时间

                    saveFileName = df.format(new Date()) + "_" + fileName;
                    reportAddr = fileUploadPath + "/" + saveFileName;
                    reportAddr = reportAddr.replace("/", File.separator).replace("\\", File.separator);

                    File savedFile = new File(fileUploadPath, saveFileName);
                    item.transferTo(savedFile);

                    picture.setFileName(fileName);
                    picture.setFileType(fileType);
                    picture.setReportaddr(reportAddr);
                    picture.setType(shop.getType());
                    picture.setSaveFileName(saveFileName);
                    picture.setPreviewId(shop.getPreviewId());
                    picture.setShopId(shop.getId());
                    picture.setPictureSize(shop.getPictureSize());
                    picture.setPageaddr(pageaddr);

                    pictureBiz.save(picture);

                    System.out.println(picture.getId());
                    ret.put("previewId", shop.getPreviewId());

                } catch (Exception e) {
                    log.error(e.getMessage());
                    ret.put("success", false);
                    ret.put("message", e.getMessage());


                    File file = new File(reportAddr);
                    // 判断目录或文件是否存在
                    if (file.exists()) {  // 不存在返回 false
                        file.delete();
                    }

                    throw new RuntimeException();
                }

            }

            ret.put("success", true);


        }
        return ret;
    }

    @Transactional(rollbackFor = Exception.class)
    public void del(Shop shop) {

        shopBiz.del(shop);
        Picture picture = new Picture();
        picture.setShopId(shop.getId());


        List<Picture> list = pictureBiz.findPictureList(picture);


        File file;

        pictureBiz.del(picture);

        for (Picture item : list) {
            file = new File(item.getReportaddr());
            // 判断目录或文件是否存在
            if (file.exists()) {  // 不存在返回 false
                file.delete();
            }
        }


    }

}
