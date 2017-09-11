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

package com.hqb.hrm.util.common;

/**
 * Created by heqingbao on 2017/9/8.
 */
public class HrmConstants {

    // 数据库表常量
    public static final String TABLE_USER = "user";
    public static final String TABLE_DEPT = "dept";
    public static final String TABLE_JOB = "job";
    public static final String TABLE_EMPLOYEE = "employee";
    public static final String TABLE_NOTICE = "notice";
    public static final String TABLE_DOCUMENT = "document";

    // 登录
    public static final String LOGIN = "loginForm";
    // 用户的session对象
    public static final String USER_SESSION = "user_session";

    // 默认每页4条数据
    public static int PAGE_DEFAULT_SIZE = 4;
}
