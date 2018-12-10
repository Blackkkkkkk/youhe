package com.youhe.mapper.test;





public interface TestMapper {

    //根据用户账号查询用户信息
    // @Delete("delete from sys_user where USER_ACCOUNT ='12'")
    void delete(String userName);
}
