package org.motechproject.web.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/ivr/api/verboice")
public class VerboiceController {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @RequestMapping(value = "/authenticate-card", produces = {"application/json"})
    @ResponseBody
    public String authenticateCard(@RequestParam("card_number") long cardNumber) {
        if (cardNumber == 1234567890123456L) return "{result: true}";

        return "{result: false}";
    }

    @RequestMapping(value = "/authenticate-pin", produces = {"application/json"})
    @ResponseBody
    public String authenticatePIN(@RequestParam("pin") int pin) {
        logger.info("PIN : " + pin);

        if (pin == 9999) return "{result: true}";

        return "{result: false}";
    }

    @RequestMapping(value = "/dd-amount", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public void dd(@RequestParam("dd_amount") String ddAmount) {
        logger.info("Demand draft amount : " + ddAmount);
    }

    @RequestMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public void status(HttpServletRequest request) {
        Map parameterMap = request.getParameterMap();
        logger.info("Status request parameters : " + parameterMap);
    }

    @RequestMapping(value = "/manifest", produces = {"application/xml"})
    @ResponseBody
    private String manifest() {
        URL manifestUrl = getClass().getClassLoader().getResource("/manifest.xml");
        try {
            String manifest = FileUtils.readFileToString(new File(manifestUrl.getFile()), "UTF-8");
            logger.info("Manifest: " + manifest);
            return manifest;
        } catch (IOException e) {
            logger.severe("Manifest could not be retrieved.");
            return "Manifest could not retrieved. Please try again later.";
        }
    }
}
