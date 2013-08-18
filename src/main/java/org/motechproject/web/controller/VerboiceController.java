package org.motechproject.web.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

@Controller
@RequestMapping("/ivr/api/verboice")
public class VerboiceController {

    @ResponseBody
    @RequestMapping("/callback")
    public String callback() {
        return "Complimenti, si sta lavorando! " + new Date();
    }

    @RequestMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public void status(HttpServletRequest request) {

    }

    @RequestMapping("/manifest")
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
