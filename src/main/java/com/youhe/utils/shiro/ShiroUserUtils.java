package com.youhe.utils.shiro;


import com.youhe.common.Constant;
import com.youhe.entity.user.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class ShiroUserUtils {

    //取得当前用户
    public static ShiroUser getShiroUser() {


        //(AuthRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();


        Object key = SecurityUtils.getSubject().getPrincipal();
        ShiroUser shiroUser = new ShiroUser();
        try {
            BeanUtils.copyProperties(shiroUser, key);
        } catch (Exception e) {
        }

        return shiroUser;


    }


    //取得当前用户的id
    public static Long getUserId() {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return shiroUser.getUid();
    }

    //取得当前用户名
    public static String getName() {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return shiroUser.getUserName();
    }

    //取得当前用户的登录名
    public static String getLoginName() {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return shiroUser.getUserAccount();
    }

    public static void encryptPassword(User user) {
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String credentialsSalt = user.getUserAccount() + user.getSalt();
        String newPassword = new SimpleHash(Constant.HASH_ALGORITHM, user.getUserPassword(),
                ByteSource.Util.bytes(credentialsSalt), Constant.HASH_INTERATIONS).toHex();
        user.setUserPassword(newPassword);
    }

    public static void checkPassword(User user) {
        String credentialsSalt = user.getUserAccount() + user.getSalt();
        String newPassword = new SimpleHash(Constant.HASH_ALGORITHM, user.getUserPassword(),
                ByteSource.Util.bytes(credentialsSalt), Constant.HASH_INTERATIONS).toHex();
        user.setUserPassword(newPassword);
    }

}
