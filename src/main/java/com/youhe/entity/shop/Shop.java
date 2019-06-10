package com.youhe.entity.shop;

import com.youhe.entity.SysBaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by xiaoqiang on 2018/12/27.
 */
@Data
@Accessors(chain = true)
public class Shop extends SysBaseEntity {


    private static final long serialVersionUID = -7160520443360732807L;
    private Integer id;
    private String name;              //商品名称
    private Integer pirce;                //价格
    private Integer num;                 //商品数量
    private String detail_picture;   //详情图
    private String thumbnail;  //缩略图
    private Integer top;        //1 置顶 0非置顶
    private Integer status;    //1 上架 0下架
    private Date register; //注册时间
    private Integer hotSale;// 是否热卖   // 1热卖   0非热卖
    private String remark;//备注
    private String shopExplain;//商品文字介绍
    private List shopExplainList;//文字格式化预览
    private String specification;//规格与包装
    private List specificationList;//规格与包装预览
    private String sukParameter;//suk参数
    private List sukParameterList;//suk参数预览

    private List<Picture> pictureList;

    //排序
    private Integer orderNum;
    private Integer isNewProductOrderNum;// 是否新品排序号

    /*
     * 关联照片表的字段
     **/

    private String saveFileName; // 照片名字
    private String fileName; // 照片名字

    private String pageaddr; // 页面显示路径


    private String previewId;
    private Integer type;  //1.详情图，2缩略图  3商品介绍图片

    private String pictureSize; //照片大小


    private String dstFileName; // 对应前端上传控件id


    private int cartNum;  //查询购物车时的数量
    private int cartPrices;//

    private int carNumUD;// 购物车数量的增减

    // 动态排序字段  1 为排序   0 为不排序
    /*
      仅实现单一维度的排序情况，满足则排序，mybatis 会按提前写好的顺序进行排序
      后期如需自定义排序字段的顺序，可单独提取出来，在代码层做个字段先后的排序
      然后按占位符传入或者遍历字符串的形式传入
     */
    private int orderNum_Sort;
    private int name_Sort;
    private int pirce_Sort;
    private int num_Sort;
    private int top_Sort;
    private int hotSale_Sort;
    private int register_Sort;
    private int isNewProductOrderNum_Sort;


    /*
     * 关联商品分类表的字段
     **/
    private Integer cid;
    private String cname;
    private String searcnName;// 模糊搜索字段

    /*
     *判断是哪一个页面
     */

    //0 用户页面，1  管理(PC端)
    private int isIndex;

    private int commodityId;
    private String describe;
    private String available;
    private int tid;
    private String color;
    private String size;

    private int AllPrices;
    private String bigOrderCode;
    private Integer price;//商品价格
    private Integer amount;//商品总价
    private String shopStyleId;// 商品下二级类目id   如枕头下的 xxx枕头
    private int stockNum;
    private String sukChangeName; // 选中suk 名字

    private Integer shopId;//  // shop_picture 照片表的 id
    private Integer spId;

    private String shopIdList;// 订单列表多个商品id 的字段

    public List<String> getshList() {
        if (StringUtils.isEmpty(shopIdList))
            return null;
        return Arrays.asList(shopIdList.split(","));
    }
}
