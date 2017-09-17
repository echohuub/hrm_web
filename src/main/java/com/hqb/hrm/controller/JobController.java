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

import com.hqb.hrm.domain.Job;
import com.hqb.hrm.service.HrmService;
import com.hqb.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by heqingbao on 2017/9/17.
 */
@Controller
@RequestMapping("/job")
public class JobController {

    @Autowired
    private HrmService hrmService;

    @RequestMapping("/selectJob")
    public ModelAndView selectJob(Integer pageIndex, Job job) {
        ModelAndView mv = new ModelAndView();
        PageModel pageModel = new PageModel();
        if (pageIndex != null) {
            pageModel.setPageIndex(pageIndex);
        }
        List<Job> jobs = hrmService.findJob(job, pageModel);
        mv.addObject("jobs", jobs);
        mv.addObject("pageModel", pageModel);
        mv.setViewName("/job/job");
        return mv;
    }

    @RequestMapping("/addJob")
    public ModelAndView addJob(String flag, Job job) {
        ModelAndView mv = new ModelAndView();
        if ("1".equals(flag)) {
            mv.setViewName("/job/showAddJob");
        } else {
            hrmService.addJob(job);
            mv.setViewName("redirect:/job/selectJob");
        }

        return mv;
    }

    @RequestMapping("/removeJob")
    public String removeJob(String ids) {
        for (String id : ids.split(",")) {
            hrmService.removeJobById(Integer.parseInt(id));
        }
        return "redirect:/job/selectJob";
    }

    @RequestMapping("updateJob")
    public ModelAndView updateJob(String flag, Job job) {
        ModelAndView mv = new ModelAndView();
        if ("1".equals(flag)) {
            Job target = hrmService.findJobById(job.getId());
            mv.addObject("job", target);
            mv.setViewName("/job/showUpdateJob");
        } else {
            hrmService.modifyJob(job);
            mv.setViewName("redirect:/job/selectJob");
        }

        return mv;
    }
}
