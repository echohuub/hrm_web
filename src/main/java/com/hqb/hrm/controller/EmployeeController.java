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
import com.hqb.hrm.domain.Employee;
import com.hqb.hrm.domain.Job;
import com.hqb.hrm.service.HrmService;
import com.hqb.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by heqingbao on 2017/9/17.
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private HrmService hrmService;

    @RequestMapping("/selectEmployee")
    public ModelAndView selectEmployee(Integer pageIndex, Employee employee,
                                       @RequestParam(name = "job_id", required = false) Integer jobId,
                                       @RequestParam(name = "dept_id", required = false) Integer deptId) {
        genericAssociation(employee, jobId, deptId);

        ModelAndView mv = new ModelAndView();
        PageModel pageModel = new PageModel();
        if (pageIndex != null) {
            pageModel.setPageIndex(pageIndex);
        }
        List<Employee> employees = hrmService.findEmployee(employee, pageModel);
        List<Job> jobs = hrmService.findAllJob();
        List<Dept> depts = hrmService.findAllDept();
        mv.addObject("employees", employees);
        mv.addObject("jobs", jobs);
        mv.addObject("depts", depts);
        mv.addObject("pageModel", pageModel);
        mv.setViewName("/employee/employee");
        return mv;
    }

    @RequestMapping("/addEmployee")
    public ModelAndView addEmployee(String flag, Employee employee,
                               @RequestParam(name = "job_id", required = false) Integer jobId,
                               @RequestParam(name = "dept_id", required = false) Integer deptId) {
        ModelAndView mv = new ModelAndView();
        if ("1".equals(flag)) {
            List<Job> jobs = hrmService.findAllJob();
            List<Dept> depts = hrmService.findAllDept();
            mv.addObject("jobs", jobs);
            mv.addObject("depts", depts);
            mv.setViewName("/employee/showAddEmployee");
        } else {
            genericAssociation(employee, jobId, deptId);
            hrmService.addEmployee(employee);
            mv.setViewName("redirect:/employee/selectEmployee");
        }

        return mv;
    }

    @RequestMapping("/removeEmployee")
    public String removeEmployee(String ids) {
        for (String id : ids.split(",")) {
            hrmService.removeEmployeeById(Integer.parseInt(id));
        }
        return "redirect:/employee/selectEmployee";
    }

    @RequestMapping("updateEmployee")
    public ModelAndView updateEmployee(String flag, Employee employee,
                                       @RequestParam(name = "job_id", required = false) Integer jobId,
                                       @RequestParam(name = "dept_id", required = false) Integer deptId) {
        ModelAndView mv = new ModelAndView();
        if ("1".equals(flag)) {
            Employee target = hrmService.findEmployeeById(employee.getId());
            List<Job> jobs = hrmService.findAllJob();
            List<Dept> depts = hrmService.findAllDept();
            mv.addObject("employee", target);
            mv.addObject("jobs", jobs);
            mv.addObject("depts", depts);
            mv.setViewName("/employee/showUpdateEmployee");
        } else {
            genericAssociation(employee, jobId, deptId);
            hrmService.modifyEmployee(employee);
            mv.setViewName("redirect:/employee/selectEmployee");
        }

        return mv;
    }

    private void genericAssociation(Employee employee, Integer jobId, Integer deptId) {
        if (jobId != null) {
            Job job = new Job();
            job.setId(jobId);
            employee.setJob(job);
        }
        if (deptId != null) {
            Dept dept = new Dept();
            dept.setId(deptId);
            employee.setDept(dept);
        }
    }
}
