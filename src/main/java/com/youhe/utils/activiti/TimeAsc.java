package com.youhe.utils.activiti;

import com.youhe.entity.activitiData.MyCommentEntity;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @ClassName TimeAsc
 * @Description 时间排序
 * @Author xdn
 * @Date 2019/4/1014:23
 * @Version 1.0
 */



public class TimeAsc {
       private final static TimeAsc TA=new TimeAsc();

       private TimeAsc(){

       }

       public static TimeAsc getInstance(){
           return  TA;

       }

    public  void ListSort(List<MyCommentEntity> list) {
        Collections.sort(list, new Comparator<MyCommentEntity>() {
            @Override
            public int compare(MyCommentEntity o1, MyCommentEntity o2) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = date.parse(o1.getTime());
                    Date dt2 = date.parse(o2.getTime());

                    if (dt1.getTime() > dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
}
