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

import com.hqb.hrm.domain.Notice;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

import static com.hqb.hrm.util.common.HrmConstants.TABLE_NOTICE;

/**
 * Created by heqingbao on 2017/9/8.
 */
public class NoticeDynaSqlProvider {
    // 分页动态查询
    public String selectWhitParam(Map<String, Object> params) {
        String sql = new SQL() {
            {
                SELECT("*");
                FROM(TABLE_NOTICE);
                if (params.get("notice") != null) {
                    Notice notice = (Notice) params.get("notice");
                    if (notice.getTitle() != null && !notice.getTitle().equals("")) {
                        WHERE("  title LIKE CONCAT ('%',#{notice.title},'%') ");
                    }
                    if (notice.getContent() != null && !notice.getContent().equals("")) {
                        WHERE("  content LIKE CONCAT ('%',#{notice.content},'%') ");
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
                FROM(TABLE_NOTICE);
                if (params.get("notice") != null) {
                    Notice notice = (Notice) params.get("notice");
                    if (notice.getTitle() != null && !notice.getTitle().equals("")) {
                        WHERE("  title LIKE CONCAT ('%',#{notice.title},'%') ");
                    }
                    if (notice.getContent() != null && !notice.getContent().equals("")) {
                        WHERE("  content LIKE CONCAT ('%',#{notice.content},'%') ");
                    }
                }
            }
        }.toString();
    }

    // 动态插入
    public String insertNotice(Notice notice) {

        return new SQL() {
            {
                INSERT_INTO(TABLE_NOTICE);
                if (notice.getTitle() != null && !notice.getTitle().equals("")) {
                    VALUES("title", "#{title}");
                }
                if (notice.getContent() != null && !notice.getContent().equals("")) {
                    VALUES("content", "#{content}");
                }
                if (notice.getUser() != null && notice.getUser().getId() != null) {
                    VALUES("user_id", "#{user.id}");
                }
            }
        }.toString();
    }

    // 动态更新
    public String updateNotice(Notice notice) {

        return new SQL() {
            {
                UPDATE(TABLE_NOTICE);
                if (notice.getTitle() != null && !notice.getTitle().equals("")) {
                    SET(" title = #{title} ");
                }
                if (notice.getContent() != null && !notice.getContent().equals("")) {
                    SET(" content = #{content} ");
                }
                if (notice.getUser() != null && notice.getUser().getId() != null) {
                    SET(" user_id = #{user.id} ");
                }
                WHERE(" id = #{id} ");
            }
        }.toString();
    }

}
