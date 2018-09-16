package com.sp.questionnaire.service.impl;

import com.sp.questionnaire.dao.UserDao;
import com.sp.questionnaire.entity.User;
import com.sp.questionnaire.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/13-10:59
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> queryUser() {
        //System.out.println("userDao:"+userDao.toString());
        return userDao.queryUser();
    }

    @Override
    public User queryUserByID(String id) {
        return userDao.queryUserByID(id);
    }

    @Override
    public User queryUserByEmail(String email) {
        if (email != null && !"".equals(email)) {
            return userDao.queryUserByEmail(email);
        }else{
            return null;
        }
    }

    @Override
    public User queryUserByRandomCode(String code) {
        return userDao.queryUserByRandomCode(code);
    }


    @Transactional
    @Override
    public boolean insertUser(User user) {
        if (user != null && !"".equals(user.getId())) {
            try {
                int i = userDao.insertUser(user);
                if (i == 1) {
                    return true;
                } else {
                    throw new RuntimeException("a:插入用户失败！" + user);
                }
            } catch (Exception e) {
                throw new RuntimeException("b:插入用户失败：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("c:插入用户失败，用户id不能为空！");
        }
    }

    @Transactional
    @Override
    public boolean updateUser(User user) {
        if (user != null && !"".equals(user.getId())) {
            try {
                int i = userDao.updateUser(user);
                if (i == 1) {
                    return true;
                } else {
                    throw new RuntimeException("a:用户更新失败！" + user);
                }
            } catch (Exception e) {
                throw new RuntimeException("b:用户更新失败：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("c:用户更新失败，用户id不能为空！");
        }
    }

    @Transactional
    @Override
    public boolean deleteUser(String id) {
        if (id != null && !"".equals(id)) {
            try {
                int i = userDao.deleteUser(id);
                if (i == 1) {
                    return true;
                } else {
                    throw new RuntimeException("a:删除用户失败！" + id);
                }
            } catch (Exception e) {
                throw new RuntimeException("b:删除用户失败：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("c:删除用户失败，用户id不能为空！");
        }
    }
}
