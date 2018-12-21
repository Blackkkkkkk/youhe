package com.youhe.utils.shiro;

import com.youhe.common.Constant;
import com.youhe.entity.SysBaseEntity;
import com.youhe.entity.permission.Permission;
import com.youhe.entity.role.Role;
import com.youhe.entity.user.User;
import com.youhe.service.user.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //认证.登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;//获取用户输入的token
        String username = utoken.getUsername();


        User search = new User();
        search.setUserAccount(username);

        User user = userService.findOnlyUserList(search).get(0);

        // User user =userService.findByUserName(username);
        return new SimpleAuthenticationInfo(user, user.getUserPassword(), this.getClass().getName());//放入shiro.调用CredentialsMatcher检验密码
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        User user = (User) principal.fromRealm(this.getClass().getName()).iterator().next();//获


        if (isNotEmpty(userService.findByUserName(user.getUserAccount()))) {
            user = userService.findByUserName(user.getUserAccount());
        }
        // 取session中的用户
        List<String> permissions = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if (roles.size() > 0) {
            for (Role role : roles) {
                Set<Permission> modules = role.getPermissions();
                if (modules.size() > 0) {
                    for (Permission module : modules) {
                        if (module.getPercode() != null && module.getPercode() != "") {
                            permissions.add(module.getPercode());
                        }
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);//将权限放入shiro中.
        return info;
    }


    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    /*
    @PostConstruct
    public void initCredentialsMatcher(){

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Constant.HASH_ALGORITHM);
        matcher.setHashIterations(Constant.HASH_INTERATIONS);
		/*HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Constant.HASH_ALGORITHM);
		matcher.setHashIterations(Constant.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);*/
    //setCredentialsMatcher(matcher);
        /*System.out.println(new CredentialsMatcher());
        System.out.println(matcher);*/
    // 重写校验
    /*
        setCredentialsMatcher(new CredentialsMatcher());
    }
*/

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;


        private Long uid;
        private String userAccount;//账号
        private String userName;//姓名
        private String phone;  //手机号码
        private String email;//邮箱
        private Date registerDate; //注册时间
        private Long locked;//状态
        private String userPassword;// 密码
        private String salt;// 密码干扰


        public ShiroUser(Long uid, String userAccount, String userName, String phone, String email, Date registerDate
                , Long locked, String userPassword, String salt) {
            this.uid = uid;
            this.userAccount = userAccount;
            this.userName = userName;
            this.phone = phone;
            this.email = email;
            this.registerDate = registerDate;
            this.locked = locked;
            this.userPassword = userPassword;
            this.salt = salt;
        }


        public Long getUid() {
            return uid;
        }

        public void setUid(Long uid) {
            this.uid = uid;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Date getRegisterDate() {
            return registerDate;
        }

        public void setRegisterDate(Date registerDate) {
            this.registerDate = registerDate;
        }

        public Long getLocked() {
            return locked;
        }

        public void setLocked(Long locked) {
            this.locked = locked;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return userAccount.toString();
        }

        /**
         * 重载hashCode,只计算loginName;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(userAccount);
        }

        /**
         * 重载equals,只计算loginName;
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (userAccount == null) {
                if (other.userAccount != null) {
                    return false;
                }
            } else if (!userAccount.equals(other.userAccount)) {
                return false;
            }
            return true;
        }
    }

    /**
     * 判断对象是否为空或null
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) return true;
        else if (obj instanceof CharSequence) return ((CharSequence) obj).length() == 0;
        else if (obj instanceof Collection) return ((Collection) obj).isEmpty();
        else if (obj instanceof Map) return ((Map) obj).isEmpty();
        else if (obj.getClass().isArray()) return Array.getLength(obj) == 0;

        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

}
