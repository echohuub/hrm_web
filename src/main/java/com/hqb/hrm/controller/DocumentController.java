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

import com.hqb.hrm.domain.Document;
import com.hqb.hrm.domain.User;
import com.hqb.hrm.service.HrmService;
import com.hqb.hrm.util.common.HrmConstants;
import com.hqb.hrm.util.tag.PageModel;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

/**
 * Created by heqingbao on 2017/9/17.
 */
@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private HrmService hrmService;

    @RequestMapping("/selectDocument")
    public ModelAndView selectDocument(Integer pageIndex, Document document) {
        ModelAndView mv = new ModelAndView();
        PageModel pageModel = new PageModel();
        if (pageIndex != null) {
            pageModel.setPageIndex(pageIndex);
        }
        List<Document> documents = hrmService.findDocument(document, pageModel);
        mv.addObject("documents", documents);
        mv.addObject("pageModel", pageModel);
        mv.setViewName("/document/document");
        return mv;
    }

    @RequestMapping("/addDocument")
    public String addDocument(String flag, Document document, HttpSession session) throws Exception {
        if ("1".equals(flag)) {
            return "/document/showAddDocument";
        } else {
            String path = session.getServletContext().getRealPath("/upload/");

            File file = new File(path);
            if (!file.isDirectory()) {
                file.delete();
            }
            if (!file.exists()) {
                new File(path).mkdirs();
            }

            String fileName = document.getFile().getOriginalFilename();

            document.getFile().transferTo(new File(path, fileName));

            document.setFileName(fileName);

            User user = (User) session.getAttribute(HrmConstants.USER_SESSION);
            document.setUser(user);

            hrmService.addDocument(document);

            return "redirect:/document/selectDocument";
        }
    }

    @RequestMapping("/updateDocument")
    public ModelAndView updateDocument(String flag, Document document) {
        ModelAndView mv = new ModelAndView();
        if ("1".equals(flag)) {
            Document target = hrmService.findDocumentById(document.getId());
            mv.addObject("document", target);
            mv.setViewName("/document/showUpdateDocument");
        } else {
            hrmService.modifyDocument(document);
            mv.setViewName("redirect:/document/selectDocument");
        }

        return mv;
    }

    @RequestMapping("/removeDocument")
    public String removeDocument(@RequestParam String ids) {
        for (String id : ids.split(",")) {
            hrmService.removeDocumentById(Integer.parseInt(id));
        }
        return "redirect:/document/selectDocument";
    }

    @RequestMapping("/downLoad")
    public HttpEntity<byte[]> downLoad(Integer id, HttpSession session) throws Exception {
        Document target = hrmService.findDocumentById(id);
        String fileName = target.getFileName();

        String dir = session.getServletContext().getRealPath("/upload/");
        File file = new File(dir, fileName);

        HttpHeaders headers = new HttpHeaders();

        String downloadFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        headers.setContentDispositionFormData("attachment", downloadFileName);

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
}

