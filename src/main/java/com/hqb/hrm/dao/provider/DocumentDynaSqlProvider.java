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

import com.hqb.hrm.domain.Document;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

import static com.hqb.hrm.util.common.HrmConstants.TABLE_DOCUMENT;

/**
 * Created by heqingbao on 2017/9/8.
 */
public class DocumentDynaSqlProvider {
    // 分页动态查询
    public String selectWhitParam(Map<String, Object> params) {
        String sql = new SQL() {
            {
                SELECT("*");
                FROM(TABLE_DOCUMENT);
                if (params.get("document") != null) {
                    Document document = (Document) params.get("document");
                    if (document.getTitle() != null && !document.getTitle().equals("")) {
                        WHERE("  title LIKE CONCAT ('%',#{document.title},'%') ");
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
                FROM(TABLE_DOCUMENT);
                if (params.get("document") != null) {
                    Document document = (Document) params.get("document");
                    if (document.getTitle() != null && !document.getTitle().equals("")) {
                        WHERE("  title LIKE CONCAT ('%',#{document.title},'%') ");
                    }
                }
            }
        }.toString();
    }

    // 动态插入
    public String insertDocument(Document document) {

        return new SQL() {
            {
                INSERT_INTO(TABLE_DOCUMENT);
                if (document.getTitle() != null && !document.getTitle().equals("")) {
                    VALUES("title", "#{title}");
                }
                if (document.getFileName() != null && !document.getFileName().equals("")) {
                    VALUES("filename", "#{fileName}");
                }
                if (document.getRemark() != null && !document.getRemark().equals("")) {
                    VALUES("remark", "#{remark}");
                }
                if (document.getUser() != null && document.getUser().getId() != null) {
                    VALUES("user_id", "#{user.id}");
                }
            }
        }.toString();
    }

    // 动态更新
    public String updateDocument(Document document) {

        return new SQL() {
            {
                UPDATE(TABLE_DOCUMENT);
                if (document.getTitle() != null && !document.getTitle().equals("")) {
                    SET(" title = #{title} ");
                }
                if (document.getFileName() != null && !document.getFileName().equals("")) {
                    SET(" filename = #{fileName} ");
                }
                if (document.getRemark() != null && !document.getRemark().equals("")) {
                    SET("remark = #{remark}");
                }
                if (document.getUser() != null && document.getUser().getId() != null) {
                    SET("user_id = #{user.id}");
                }
                WHERE(" id = #{id} ");
            }
        }.toString();
    }


}
