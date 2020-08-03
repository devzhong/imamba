package com.mamba.imamba.service;

import com.mamba.imamba.RestResponse;
import com.mamba.imamba.dao.UserDao;
import com.mamba.imamba.error.ServiceError;
import com.mamba.imamba.po.UserPO;
import com.mamba.imamba.vo.OperateUserVO;
import com.mamba.imamba.vo.list.UserListVO;
import com.mamba.imamba.vo.search.SearchUserVO;
import com.ne.boot.common.entity.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    //账号新增
    public RestResponse create(OperateUserVO vo){
        try {
            UserPO po = buildUser(new UserPO(), vo);
            if (userDao.findByUsername(vo.getUsername()) != null){
                return RestResponse.error(ServiceError.USERNAME_EXIST);
            }
            if (vo.getPhone() != null){
                if (userDao.findByPhone(vo.getPhone()) != null){
                    return RestResponse.error(ServiceError.PHONE_EXIST);
                }
            }
            po.setUsername(vo.getUsername());
            po.setPhone(vo.getPhone());
            userDao.create(po);
            return RestResponse.success();
        }catch (Exception e){
            LOGGER.error("UserService create error {}", e);
            return RestResponse.error(ServiceError.SERVICE_ERROR);
        }
    }

    //更新用户
    public RestResponse update(OperateUserVO vo, Integer id){
        try {
            UserPO po = userDao.findById(id);
            if (po == null){
                return RestResponse.error(ServiceError.USER_NOT_EXIST);
            }
            //判断手机号是否已被使用
            if (vo.getPhone() != null){
                UserPO poPhone = userDao.findByPhone(vo.getPhone());
                if (poPhone != null && (!poPhone.getId().equals(id))){
                    return RestResponse.error(ServiceError.PHONE_EXIST);
                }
            }

            //判断用户名是否已被使用
            if (vo.getUsername() != null){
                UserPO poUsername = userDao.findByUsername(vo.getUsername());
                if (poUsername != null && (!poUsername.getId().equals(id))){
                    return RestResponse.error(ServiceError.USERNAME_EXIST);
                }
            }
            po.setPhone(vo.getPhone());
            po.setUsername(vo.getUsername());
            po.setPassword(vo.getPassword());
            po.setId(id);
            userDao.update(po);
            return RestResponse.success();
        }catch (Exception e){
            LOGGER.error("UserService update error {}", e);
            return RestResponse.error(ServiceError.SERVICE_ERROR);
        }
    }

    //删除用户
    public RestResponse delete(Integer id){
        try {
            UserPO po = userDao.findById(id);
            if (po == null){
                return RestResponse.error(ServiceError.USER_NOT_EXIST);
            }
            userDao.delete(id);
            return RestResponse.success();
        }catch (Exception e){
            LOGGER.error("UserService delete error {}", e);
            return RestResponse.error(ServiceError.SERVICE_ERROR);
        }
    }

    //查询用户
    public RestResponse<UserListVO> all(SearchUserVO vo, Page page){
        try {
            return RestResponse.success(buildUserList(userDao.all(vo, page)),page);
        }catch (Exception e){
            LOGGER.error("UserService all error {}", e);
            return RestResponse.error(ServiceError.SERVICE_ERROR);
        }

    }


    private UserPO buildUser(UserPO po, OperateUserVO vo){
        po.setPassword(vo.getPassword());
        return po;
    }

    private List<UserListVO> buildUserList(List<UserPO> pos){
        List<UserListVO> vos = new ArrayList<>();
        for (UserPO po : pos){
            UserListVO vo = new UserListVO();
            vo.setId(po.getId());
            vo.setUsername(po.getUsername());
            vo.setPassword(po.getPassword());
            vo.setPhone(po.getPhone());
            vo.setCreateTime(po.getCreateTime());
            vo.setUpdateTime(po.getUpdateTime());
            vos.add(vo);
        }
        return vos;
    }


}
