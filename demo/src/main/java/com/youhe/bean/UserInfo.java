package com.youhe.bean;

import com.youhe.utils.BaseBean;

/**
 * 
 * <p>Title:UserInfo </p>
 * <p>Description:用户信息的实体类</p>
 * <p>Company:xxxx</p>
 * @author 老牛
 * @version 1.0
 * 
 */
public class UserInfo extends BaseBean{

	//用户编号
	private Integer userId;
	
	//角色编号
	private Integer roleId;
	
	//用户姓名
	private String userName;
	
	//用户性别
	private String userSex;
	
	//用户年龄
	private int userAge;
	
	//账号
	private String userAccount;
	
	//密码
	private String userPassword;
	
	//薪资
	private Double userSalary; 
	
	//标示
	private String userMark;
	
	//角色名称
	private String roleName;

	
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Double getUserSalary() {
		return userSalary;
	}

	public void setUserSalary(Double userSalary) {
		this.userSalary = userSalary;
	}

	public String getUserMark() {
		return userMark;
	}

	public void setUserMark(String userMark) {
		this.userMark = userMark;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", roleId=" + roleId + ", userName=" + userName + ", userSex=" + userSex
				+ ", userAge=" + userAge + ", userAccount=" + userAccount + ", userPassword=" + userPassword
				+ ", userSalary=" + userSalary + ", userMark=" + userMark + ", roleName=" + roleName + "]";
	}
	
	
}
