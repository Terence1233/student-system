package com.braisedpanda.shirotest.shiro;



import com.braisedpanda.shirotest.model.po.User;
import com.braisedpanda.shirotest.model.po.UserRole;
import com.braisedpanda.shirotest.service.PermissionService;
import com.braisedpanda.shirotest.service.RolePermissionService;
import com.braisedpanda.shirotest.service.UserRoleService;
import com.braisedpanda.shirotest.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RolePermissionService rolePermissionService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("========开始权限验证========");
//        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = (User)principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //查询登录用户所拥有的角色，并添加角色
        int uid = user.getUserid();
        List<UserRole> roleList = userRoleService.getUserRoleByUid(uid);

        for (UserRole role:
             roleList) {
           info.addRole(role.getRole());


           String roleId= role.getRoleId();
            List<String> permissionList= rolePermissionService.getPermission(roleId);
            //查询登录用户所拥有的权限，并添加权限
            for (String  permission:
                    permissionList) {
                info.addStringPermission(permission);
            }

        }

        return info;
    }

    //重写验证身份的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("========开始身份验证========");
        String username = (String) authenticationToken.getPrincipal();

        String userpassword = new String((char[]) authenticationToken.getCredentials());


        User user = userService.selectUserByUsernameAndPasword(username,userpassword);

        if (user == null) {
            throw new AccountException("用户名或密码错误");
        }

        return new SimpleAuthenticationInfo(user, userpassword, getName());

    }



}