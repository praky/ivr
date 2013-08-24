package org.motechproject.web.controller;

import org.motechproject.web.viewmodel.KooKooRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/ivr/api/kookoo")
public class KooKooController {
    private static Map<String, Integer> callflow = new HashMap<>();
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @ResponseBody
    @RequestMapping("/callback")
    private String callback(@ModelAttribute KooKooRequest request, BindingResult bindingResult) {
        logger.info(request.toString());

        if (bindingResult.hasErrors()) return playError();

        int previousCallFlowStep = -1;

        if (callflow.get(request.getSid()) != null) {
            previousCallFlowStep = callflow.get(request.getSid());
        }

        logger.info("Previous callflow step " + previousCallFlowStep);

        final int nextCallFlowStep = nextCallFlowStep(request, previousCallFlowStep);
        callflow.put(request.getSid(), nextCallFlowStep);

        logger.info("Next callflow step " + nextCallFlowStep);

        return callFlowFor(nextCallFlowStep, request);
    }

    private int nextCallFlowStep(KooKooRequest request, int previousCallFlowStep) {
        switch (previousCallFlowStep) {
            case 0: {
                return previousCallFlowStep + 1;
            }
            case 1: {
                if (isValidDebitCard(request)) {
                    return previousCallFlowStep + 1;
                }

                return previousCallFlowStep;
            }
            case 2: {
                if (isValidPIN(request)) {
                    return previousCallFlowStep + 1;
                }

                return previousCallFlowStep;
            }
            case 3: {
                return previousCallFlowStep + 1;
            }
            default: {
                return 0;
            }
        }
    }

    private String callFlowFor(int callFlowStep, KooKooRequest request) {
        switch (callFlowStep) {
            case 0: {
                return "<?xml version='1.0' encoding='UTF-8'?>" +
                        "<response>" +
                        "<playtext>Welcome to Thought Works phone banking services!</playtext>" +
                        "</response>";
            }
            case 1: {
                return "<?xml version='1.0' encoding='UTF-8'?>" +
                        String.format("<response sid='%s'>", request.getSid()) +
                        "<collectdtmf l='16' t='#' o='25000'>" +
                        "<playtext>Please enter your debit card number followed by the # key\n" +
                        "</playtext>" +
                        "</collectdtmf>" +
                        "</response>";
            }
            case 2: {
                return "<?xml version='1.0' encoding='UTF-8'?>" +
                        String.format("<response sid='%s'>", request.getSid()) +
                        "<collectdtmf l='4' t='#' o='20000'>" +
                        "<playtext>Please enter your debit card PIN number followed by the # key\n" +
                        "</playtext>" +
                        "</collectdtmf>" +
                        "</response>";
            }
            case 3: {
                return "<?xml version='1.0' encoding='UTF-8'?>" +
                        String.format("<response sid='%s'>", request.getSid()) +
                        "<collectdtmf l='10' t='#' o='25000'>" +
                        "<playtext>Please enter the Demand Draft amount followed by the # key\n" +
                        "</playtext>" +
                        "</collectdtmf>" +
                        "</response>";
            }
            case 4: {
                return "<?xml version='1.0' encoding='UTF-8'?>" +
                        String.format("<response sid='%s'>", request.getSid()) +
                        "<playtext>Your Demand Draft will be delivered in three working days. Thank you for choosing Thought Works phone banking services!</playtext>" +
                        "</response>";
            }
            default: {
                return "<?xml version='1.0' encoding='UTF-8'?>" +
                        String.format("<response sid='%s'>", request.getSid()) +
                        "<playtext>Welcome to Thought Works phone banking services!</playtext>" +
                        "</response>";
            }
        }
    }

    private String playError() {
        return "<response>" +
                "<playtext>Sorry, something went wrong. Please call later.</playtext>" +
                "<hangup/>" +
                "</response>";
    }

    private boolean isValidDebitCard(KooKooRequest request) {
        return "1234567890123456".equals(request.getData());
    }

    private boolean isValidPIN(KooKooRequest request) {
        return "9999".equals(request.getData());
    }
}
