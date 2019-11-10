package com.landleaf.ibsaas.web.web.controller.leo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @Title: RedirectController 
* @author wyl
*/
@Controller
@RequestMapping("/redirect")
public class RedirectController {

    @Autowired
    private Environment environment;
    @Value("${ibsaas.face.redirecturl}")
    private String redirecturl;

    @RequestMapping("")
    public void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(redirecturl);
    }
}
