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

package com.hqb.hrm.util.tag;


import com.hqb.hrm.util.common.HrmConstants;

/**
 * Created by heqingbao on 2017/9/8.
 * <p>
 * 分页实体
 */
public class PageModel {
    /**
     * 分页总数据条数
     */
    private int recordCount;
    /**
     * 当前页面
     */
    private int pageIndex;
    /**
     * 每页分多少条数据
     */
    private int pageSize = HrmConstants.PAGE_DEFAULT_SIZE = 4;

    /**
     * 总页数
     */
    private int totalSize;

    public int getRecordCount() {
        this.recordCount = this.recordCount <= 0 ? 0 : this.recordCount;
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageIndex() {
        this.pageIndex = this.pageIndex <= 0 ? 1 : this.pageIndex;
        /** 判断当前页面是否超过了总页数:如果超过了默认给最后一页作为当前页  */
        this.pageIndex = this.pageIndex >= this.getTotalSize() ? this.getTotalSize() : this.pageIndex;

        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        this.pageSize = this.pageSize <= HrmConstants.PAGE_DEFAULT_SIZE ? HrmConstants.PAGE_DEFAULT_SIZE : this.pageSize;
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        if (this.getRecordCount() <= 0) {
            totalSize = 0;
        } else {
            totalSize = (this.getRecordCount() - 1) / this.getPageSize() + 1;
        }
        return totalSize;
    }


    public int getFirstLimitParam() {
        return (this.getPageIndex() - 1) * this.getPageSize();
    }


}
