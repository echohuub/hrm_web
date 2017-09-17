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

import com.hqb.hrm.domain.Dept;
import com.hqb.hrm.service.HrmService;
import com.hqb.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by heqingbao on 2017/9/17.
 */
@Controller
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private HrmService hrmService;

    @RequestMapping("/selectDept")
    public String selectDept(Model model, Integer pageIndex, Dept dept) {
        PageModel pageModel = new PageModel();
        if (pageIndex != null) {
            pageModel.setPageIndex(pageIndex);
        }
        List<Dept> depts = hrmService.findDept(dept, pageModel);
        model.addAttribute("depts", depts);
        model.addAttribute("pageModel", pageModel);
        return "/dept/dept";
    }

    @RequestMapping("addDept")
    public String addDept(String flag, Dept dept) {
        if ("1".equals(flag)) {
            return "/dept/showAddDept";
        } else {
            hrmService.addDept(dept);
            return "redirect:/dept/selectDept";
        }
    }

    @RequestMapping("updateDept")
    public ModelAndView updateDept(String flag, Dept dept) {
        ModelAndView mv = new ModelAndView();
        if ("1".equals(flag)) {
            Dept target = hrmService.findDeptById(dept.getId());
            mv.addObject("dept", target);
            mv.setViewName("/dept/showUpdateDept");
        } else {
            hrmService.modifyDept(dept);
            mv.setViewName("redirect:/dept/selectDept");
        }
        return mv;
    }

    @RequestMapping("removeDept")
    public String removeDept(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            hrmService.removeDeptById(Integer.parseInt(id));
        }
        return "redirect:/dept/selectDept";
    }
}
