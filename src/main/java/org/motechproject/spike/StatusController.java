package org.motechproject.spike;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/")
public class StatusController {

    @ResponseBody
    @RequestMapping("/status")
    private String status() {
        return "I am alive and running! " + new Date();
    }
}
