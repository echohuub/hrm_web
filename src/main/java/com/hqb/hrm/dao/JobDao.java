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

import com.hqb.hrm.dao.provider.JobDynaSqlProvider;
import com.hqb.hrm.domain.Job;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

import static com.hqb.hrm.util.common.HrmConstants.JOBTABLE;

/**
 * Created by heqingbao on 2017/9/8.
 */
public interface JobDao {

    @Select("select * from " + JOBTABLE + " where ID = #{id}")
    Job selectById(int id);

    @Select("select * from " + JOBTABLE + " ")
    List<Job> selectAllJob();

    // 动态查询
    @SelectProvider(type = JobDynaSqlProvider.class, method = "selectWhitParam")
    List<Job> selectByPage(Map<String, Object> params);

    @SelectProvider(type = JobDynaSqlProvider.class, method = "count")
    Integer count(Map<String, Object> params);

    // 根据id删除部门
    @Delete(" delete from " + JOBTABLE + " where id = #{id} ")
    void deleteById(Integer id);

    // 动态插入部门
    @SelectProvider(type = JobDynaSqlProvider.class, method = "insertJob")
    void save(Job job);

    // 动态修改用户
    @SelectProvider(type = JobDynaSqlProvider.class, method = "updateJob")
    void update(Job job);
}
