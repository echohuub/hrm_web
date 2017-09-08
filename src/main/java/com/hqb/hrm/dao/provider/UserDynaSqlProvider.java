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

package com.hqb.hrm.dao.provider;

import com.hqb.hrm.domain.User;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

import static com.hqb.hrm.util.common.HrmConstants.USERTABLE;

/**
 * Created by heqingbao on 2017/9/8.
 */
public class UserDynaSqlProvider {
    // 分页动态查询
    public String selectWhitParam(Map<String, Object> params) {
        String sql = new SQL() {
            {
                SELECT("*");
                FROM(USERTABLE);
                if (params.get("user") != null) {
                    User user = (User) params.get("user");
                    if (user.getUsername() != null && !user.getUsername().equals("")) {
                        WHERE("  username LIKE CONCAT ('%',#{user.username},'%') ");
                    }
                    if (user.getStatus() != null && !user.getStatus().equals("")) {
                        WHERE(" status LIKE CONCAT ('%',#{user.status},'%') ");
                    }
                }
            }
        }.toString();

        if (params.get("pageModel") != null) {
            sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
        }

        return sql;
    }

    // 动态查询总数量
    public String count(Map<String, Object> params) {
        return new SQL() {
            {
                SELECT("count(*)");
                FROM(USERTABLE);
                if (params.get("user") != null) {
                    User user = (User) params.get("user");
                    if (user.getUsername() != null && !user.getUsername().equals("")) {
                        WHERE(" username LIKE CONCAT ('%',#{user.username},'%') ");
                    }
                    if (user.getStatus() != null && !user.getStatus().equals("")) {
                        WHERE(" status LIKE CONCAT ('%',#{user.status},'%') ");
                    }
                }
            }
        }.toString();
    }

    // 动态插入
    public String insertUser(User user) {

        return new SQL() {
            {
                INSERT_INTO(USERTABLE);
                if (user.getUsername() != null && !user.getUsername().equals("")) {
                    VALUES("username", "#{username}");
                }
                if (user.getStatus() != null && !user.getStatus().equals("")) {
                    VALUES("status", "#{status}");
                }
                if (user.getLoginname() != null && !user.getLoginname().equals("")) {
                    VALUES("loginname", "#{loginname}");
                }
                if (user.getPassword() != null && !user.getPassword().equals("")) {
                    VALUES("password", "#{password}");
                }
            }
        }.toString();
    }

    // 动态更新
    public String updateUser(User user) {

        return new SQL() {
            {
                UPDATE(USERTABLE);
                if (user.getUsername() != null) {
                    SET(" username = #{username} ");
                }
                if (user.getLoginname() != null) {
                    SET(" loginname = #{loginname} ");
                }
                if (user.getPassword() != null) {
                    SET(" password = #{password} ");
                }
                if (user.getStatus() != null) {
                    SET(" status = #{status} ");
                }
                if (user.getCreateDate() != null) {
                    SET(" create_date = #{createDate} ");
                }
                WHERE(" id = #{id} ");
            }
        }.toString();
    }
}
