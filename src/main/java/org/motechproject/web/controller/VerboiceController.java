package org.motechproject.web.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

@Controller
@RequestMapping("/ivr/api/verboice")
public class VerboiceController {

    @RequestMapping(value = "/authenticate-pin", produces = {"application/json"})
    @ResponseBody
    public String authenticatePIN() {
        return "{result: ok}";
    }

    @RequestMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public void status(HttpServletRequest request) {
        Map parameterMap = request.getParameterMap();
    }

    @RequestMapping(value = "/manifest", produces = {"application/xml"})
    @ResponseBody
    private String manifest() {
        URL manifest = getClass().getClassLoader().getResource("/manifest.xml");
        try {
            return FileUtils.readFileToString(new File(manifest.getFile()));
        } catch (IOException e) {
            return "Manifest could not be generated.";
        }
    }
}
