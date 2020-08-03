package com.mamba.imamba.dao;

import com.mamba.imamba.po.UserPO;
import com.mamba.imamba.vo.search.SearchUserVO;
import com.ne.boot.common.entity.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    UserPO findById(Integer id);
    UserPO findByUsername(String username);
    void create(UserPO po);
    UserPO findByPhone(String phone);
    List<UserPO> all(SearchUserVO po, Page page);
    void update(UserPO po);
    void delete(Integer id);

}
