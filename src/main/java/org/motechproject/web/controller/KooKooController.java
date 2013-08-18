package org.motechproject.web.controller;

import org.motechproject.web.viewmodel.KooKooRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/ivr/api/kookoo")
public class KooKooController {
    @ResponseBody
    @RequestMapping("/callback")
    private String callback(@ModelAttribute KooKooRequest request, BindingResult bindingResult) {
        System.out.println("KooKoo request:" + request);
        if (bindingResult.hasErrors()) return "<response>" +
                                                    "<playtext>Sorry, something went wrong. Please call later.</playtext>" +
                                                    "<hangup/>" +
                                                "</response>";
        return "<response>" +
                    "<playtext>Hello World!</playtext>" +
                    "<hangup/>" +
                "</response>";
    }

    @RequestMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    private void status(HttpServletRequest request) {

    }

}
