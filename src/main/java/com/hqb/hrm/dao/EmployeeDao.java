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

import com.hqb.hrm.dao.provider.EmployeeDynaSqlProvider;
import com.hqb.hrm.domain.Employee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

import static com.hqb.hrm.util.common.HrmConstants.EMPLOYEETABLE;

/**
 * Created by heqingbao on 2017/9/8.
 */
public interface EmployeeDao {

    // 根据参数查询员工总数
    @SelectProvider(type = EmployeeDynaSqlProvider.class, method = "count")
    Integer count(Map<String, Object> params);

    // 根据参数动态查询员工
    @SelectProvider(type = EmployeeDynaSqlProvider.class, method = "selectWhitParam")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "CARD_ID", property = "cardId"),
            @Result(column = "POST_CODE", property = "postCode"),
            @Result(column = "QQ_NUM", property = "qqNum"),
            @Result(column = "BIRTHDAY", property = "birthday", javaType = java.util.Date.class),
            @Result(column = "CREATE_DATE", property = "createDate", javaType = java.util.Date.class),
            @Result(column = "DEPT_ID", property = "dept",
                    one = @One(select = "com.hqb.hrm.dao.DeptDao.selectById",
                            fetchType = FetchType.EAGER)),
            @Result(column = "JOB_ID", property = "job",
                    one = @One(select = "com.hqb.hrm.dao.JobDao.selectById",
                            fetchType = FetchType.EAGER))
    })
    List<Employee> selectByPage(Map<String, Object> params);

    // 动态插入员工
    @SelectProvider(type = EmployeeDynaSqlProvider.class, method = "insertEmployee")
    void save(Employee employee);

    // 根据id删除员工
    @Delete(" delete from " + EMPLOYEETABLE + " where id = #{id} ")
    void deleteById(Integer id);

    // 根据id查询员工
    @Select("select * from " + EMPLOYEETABLE + " where ID = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "CARD_ID", property = "cardId"),
            @Result(column = "POST_CODE", property = "postCode"),
            @Result(column = "QQ_NUM", property = "qqNum"),
            @Result(column = "BIRTHDAY", property = "birthday", javaType = java.util.Date.class),
            @Result(column = "CREATE_DATE", property = "createDate", javaType = java.util.Date.class),
            @Result(column = "DEPT_ID", property = "dept",
                    one = @One(select = "com.hqb.hrm.dao.DeptDao.selectById",
                            fetchType = FetchType.EAGER)),
            @Result(column = "JOB_ID", property = "job",
                    one = @One(select = "com.hqb.hrm.dao.JobDao.selectById",
                            fetchType = FetchType.EAGER))
    })
    Employee selectById(Integer id);

    // 动态修改员工
    @SelectProvider(type = EmployeeDynaSqlProvider.class, method = "updateEmployee")
    void update(Employee employee);

}
