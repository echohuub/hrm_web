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

import com.hqb.hrm.dao.provider.DocumentDynaSqlProvider;
import com.hqb.hrm.domain.Document;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

import static com.hqb.hrm.util.common.HrmConstants.TABLE_DOCUMENT;

/**
 * Created by heqingbao on 2017/9/8.
 */
public interface DocumentDao {

    // 动态查询
    @SelectProvider(type = DocumentDynaSqlProvider.class, method = "selectWhitParam")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "CREATE_DATE", property = "createDate", javaType = java.util.Date.class),
            @Result(column = "USER_ID", property = "user",
                    one = @One(select = "com.hqb.hrm.dao.UserDao.selectById",
                            fetchType = FetchType.EAGER))
    })
    List<Document> selectByPage(Map<String, Object> params);

    @SelectProvider(type = DocumentDynaSqlProvider.class, method = "count")
    Integer count(Map<String, Object> params);

    // 动态插入文档
    @SelectProvider(type = DocumentDynaSqlProvider.class, method = "insertDocument")
    void save(Document document);

    @Select("select * from " + TABLE_DOCUMENT + " where ID = #{id}")
    Document selectById(int id);

    // 根据id删除文档
    @Delete(" delete from " + TABLE_DOCUMENT + " where id = #{id} ")
    void deleteById(Integer id);

    // 动态修改文档
    @SelectProvider(type = DocumentDynaSqlProvider.class, method = "updateDocument")
    void update(Document document);

}
