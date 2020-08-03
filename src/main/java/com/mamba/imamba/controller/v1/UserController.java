package com.mamba.imamba.controller.v1;

import com.mamba.imamba.RestResponse;
import com.mamba.imamba.error.ServiceError;
import com.mamba.imamba.service.UserService;
import com.mamba.imamba.vo.OperateUserVO;
import com.mamba.imamba.vo.list.UserListVO;
import com.mamba.imamba.vo.search.SearchUserVO;
import com.ne.boot.common.entity.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "UserController")
@RestController
@RequestMapping("/api/v1/*")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户新增")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public RestResponse create(@RequestBody OperateUserVO vo){
        if (StringUtils.isEmpty(vo.getUsername())){
            return RestResponse.error(ServiceError.USERNAME_NULL);
        }
        if (StringUtils.isEmpty(vo.getPassword())){
            return RestResponse.error(ServiceError.PASSWORD_NULL);
        }
        return userService.create(vo);
    }

    @ApiOperation("更新用户")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public RestResponse update(@PathVariable("id") Integer id, @RequestBody OperateUserVO vo){
        return userService.update(vo, id);
    }

    @ApiOperation("删除用户")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public RestResponse delete(@PathVariable("id") Integer id){
        return userService.delete(id);
    }

    @ApiOperation("查询用户")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public RestResponse<UserListVO> all(@ApiIgnore SearchUserVO vo, Page page){
        return userService.all(vo, page);
    }

}