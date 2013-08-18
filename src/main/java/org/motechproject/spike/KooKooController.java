package org.motechproject.spike;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/ivr/api/kookoo")
public class KooKooController {
    @ResponseBody
    @RequestMapping("/callback")
    private String callback() {
        return "Complimenti, si sta lavorando! " + new Date();
    }

    @RequestMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    private void status(HttpServletRequest request) {

    }

}
