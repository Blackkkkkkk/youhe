package com.youhe.utils.shiro;

import com.youhe.common.Constant;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class CredentialsMatcher extends SimpleCredentialsMatcher {

    private static final Logger log = LoggerFactory.getLogger(SimpleCredentialsMatcher.class);


    public CredentialsMatcher() {
    }

    protected Object getCredentials(AuthenticationToken token) {
        return token.getCredentials();
    }

    protected Object getCredentials(AuthenticationInfo info) {
        return info.getCredentials();
    }

    @Override
    protected boolean equals(Object tokenCredentials, Object accountCredentials) {
        if (log.isDebugEnabled()) {
            log.debug("Performing credentials equality check for tokenCredentials of type [" + tokenCredentials.getClass().getName() + " and accountCredentials of type [" + accountCredentials.getClass().getName() + "]");
        }

        if (this.isByteSource(tokenCredentials) && this.isByteSource(accountCredentials)) {
            if (log.isDebugEnabled()) {
                log.debug("Both credentials arguments can be easily converted to byte arrays.  Performing array equals comparison");
            }

            byte[] tokenBytes = this.toBytes(tokenCredentials);
            byte[] accountBytes = this.toBytes(accountCredentials);


            System.out.println(MessageDigest.isEqual(tokenBytes, accountBytes));

            return MessageDigest.isEqual(tokenBytes, accountBytes);

        } else {
            System.out.println(accountCredentials.equals(tokenCredentials));
            return accountCredentials.equals(tokenCredentials);
        }
    }


    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {


        String newPassword = null;
        InitUsernamePasswordToken authcToken = (InitUsernamePasswordToken) token;

        //   Object tokenCredentials = this.getCredentials(token);
        Object accountCredentials = this.getCredentials(info);

        //如果为PC端登录，正常密码验证
        if (authcToken.getActionType() == 1) {

            String credentialsSalt = authcToken.getUsername() + authcToken.getSalt();
            newPassword = new SimpleHash(Constant.HASH_ALGORITHM, authcToken.getPassword(),
                    ByteSource.Util.bytes(credentialsSalt), Constant.HASH_INTERATIONS).toHex();

        } else if (authcToken.getActionType() == 2) {
            //如果微信端登录，密码不需要has加密
            newPassword = authcToken.getPass();
        } else if (authcToken.getActionType() == 3) {
            //如果手机验证码登录，密码不需要has加密
            newPassword = authcToken.getPass();
        }
        Boolean a = this.equals(newPassword, accountCredentials);
        System.out.println(this.equals(newPassword, accountCredentials));
        return this.equals(newPassword, accountCredentials);

    }




}
