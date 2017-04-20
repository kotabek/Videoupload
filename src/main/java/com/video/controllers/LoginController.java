package com.video.controllers;

import com.video.domains.MemberDocumentDomain;
import com.video.services.DocumentService;
import com.video.services.S3Service;
import com.video.to.DocumentTO;
import com.video.utils.SecurityContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kotabek on 4/20/17.
 */
@Controller
public class LoginController {
    @Autowired
    private DocumentService documentService;
    @Autowired
    private S3Service s3Service;


    @RequestMapping(value = {"/", "/main"}, method = RequestMethod.GET)
    public ModelAndView indexPage() {
        RedirectView rv = new RedirectView("/login");
        rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return new ModelAndView(rv);
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage() {
        if (SecurityContextUtils.getMemberId() != null) {
            RedirectView rv = new RedirectView("/dashboard");
            rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
            return new ModelAndView(rv);
        }
        return new ModelAndView("index");
    }


    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboardPage() {
        List<DocumentTO> documents = new ArrayList<>();
        for (MemberDocumentDomain document : documentService.getMemberDocuments(SecurityContextUtils.getMemberId())) {
            DocumentTO to = new DocumentTO();
            document.fillTO(to);
            to.setUrl(s3Service.getResourceUrlForDay(to.getKey()));
            documents.add(to);
        }
        return new ModelAndView("dashboard")
                .addObject("documents", documents);
    }
}
