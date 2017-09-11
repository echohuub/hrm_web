/*
 * Copyright (c) 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hqb.hrm.dao;


import com.hqb.hrm.dao.provider.UserDynaSqlProvider;
import com.hqb.hrm.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

import static com.hqb.hrm.util.common.HrmConstants.TABLE_USER;

/**
 * Created by heqingbao on 2017/9/8.
 */
public interface UserDao {

    // 根据登录名和密码查询员工
    @Select("select * from " + TABLE_USER + " where loginname = #{loginname} and password = #{password}")
    User selectByLoginnameAndPassword(
            @Param("loginname") String loginname,
            @Param("password") String password);

    // 根据id查询用户
    @Select("select * from " + TABLE_USER + " where ID = #{id}")
    User selectById(Integer id);

    // 根据id删除用户
    @Delete(" delete from " + TABLE_USER + " where id = #{id} ")
    void deleteById(Integer id);

    // 动态修改用户
    @SelectProvider(type = UserDynaSqlProvider.class, method = "updateUser")
    void update(User user);

    // 动态查询
    @SelectProvider(type = UserDynaSqlProvider.class, method = "selectWhitParam")
    List<User> selectByPage(Map<String, Object> params);

    // 根据参数查询用户总数
    @SelectProvider(type = UserDynaSqlProvider.class, method = "count")
    Integer count(Map<String, Object> params);

    // 动态插入用户
    @SelectProvider(type = UserDynaSqlProvider.class, method = "insertUser")
    void save(User user);

}
