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

package com.hqb.hrm.controller;

import com.hqb.hrm.domain.Notice;
import com.hqb.hrm.domain.User;
import com.hqb.hrm.service.HrmService;
import com.hqb.hrm.util.common.HrmConstants;
import com.hqb.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by heqingbao on 2017/9/17.
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private HrmService hrmService;

    @RequestMapping("/selectNotice")
    public ModelAndView selectNotice(Integer pageIndex, Notice notice) {
        ModelAndView mv = new ModelAndView();
        PageModel pageModel = new PageModel();
        if (pageIndex != null) {
            pageModel.setPageIndex(pageIndex);
        }
        List<Notice> notices = hrmService.findNotice(notice, pageModel);
        mv.addObject("notices", notices);
        mv.addObject("pageModel", pageModel);
        mv.setViewName("/notice/notice");
        return mv;
    }

    @RequestMapping("/addNotice")
    public String addNotice(String flag, Notice notice, HttpSession session) {
        if ("1".equals(flag)) {
            return "/notice/showAddNotice";
        } else {
            User currentUser = (User) session.getAttribute(HrmConstants.USER_SESSION);
            notice.setUser(currentUser);
            hrmService.addNotice(notice);
            return "redirect:/notice/selectNotice";
        }
    }

    @RequestMapping("/removeNotice")
    public String removeNotice(String ids) {
        for (String id : ids.split(",")) {
            hrmService.removeNoticeById(Integer.parseInt(id));
        }
        return "redirect:/notice/selectNotice";
    }

    @RequestMapping("/updateNotice")
    public ModelAndView updateNotice(String flag, Notice notice) {
        ModelAndView mv = new ModelAndView();
        if ("1".equals(flag)) {
            Notice target = hrmService.findNoticeById(notice.getId());
            mv.addObject("notice", target);
            mv.setViewName("/notice/showUpdateNotice");
        } else {
            hrmService.modifyNotice(notice);
            mv.setViewName("redirect:/notice/selectNotice");
        }
        return mv;
    }

    @RequestMapping("/previewNotice")
    public ModelAndView previewNotice(String id) {
        Notice notice = hrmService.findNoticeById(Integer.parseInt(id));
        ModelAndView mv = new ModelAndView();
        mv.addObject("notice", notice);
        mv.setViewName("/notice/previewNotice");
        return mv;
    }
}

