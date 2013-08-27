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
    public static final String EVENT_HANGUP = "Hangup";
    private static Map<String, Integer> callSidToCallFlowStepMap = new HashMap<>();
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @ResponseBody
    @RequestMapping("/callback")
    private String callback(@ModelAttribute KooKooRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return playError();

        logRequest(request);

        if (EVENT_HANGUP.equals(request.getEvent())) return "";

        int previousCallFlowStep = -1;

        if (callSidToCallFlowStepMap.get(request.getSid()) != null) {
            previousCallFlowStep = callSidToCallFlowStepMap.get(request.getSid());
        }

        logger.info("Previous callflow step " + previousCallFlowStep);

        final int nextCallFlowStep = nextCallFlowStep(request, previousCallFlowStep);
        callSidToCallFlowStepMap.put(request.getSid(), nextCallFlowStep);

        logger.info("Next callflow step " + nextCallFlowStep);

        final String callFlow = callFlowFor(nextCallFlowStep, request);
        logCallFlow(callFlow);
        return callFlow;
    }

    private void logCallFlow(String callFlow) {
        logger.info("\n\n");
        logger.info(String.format("\n%s\n%s\n%s\n%s\n",
                                    "===================================================================",
                                    "    Printing the KooKoo tunes / callflow xml being returned.",
                                    "===================================================================",
                                    callFlow));
    }

    private void logRequest(KooKooRequest request) {
        logger.info("\n\n");
        logger.info(String.format("\n%s\n%s\n%s\n%s\n",
                "==============================================",
                "   Printing the KooKoo http request.",
                "==============================================",
                request.toString()));
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
                if (isValidDDAmount(request)) {
                    return previousCallFlowStep + 1;
                }

                return previousCallFlowStep;
            }
            default: {
                return 0;
            }
        }
    }

    private String callFlowFor(int callFlowStep, KooKooRequest request) {
        switch (callFlowStep) {
            case 0: {
                logger.info("Playing welcome message.");
                return "<?xml version='1.0' encoding='UTF-8'?>" +
                        "\n<response>" +
                            "\n  <playtext>Welcome to Thought Works phone banking services!</playtext>" +
                        "\n</response>";
            }
            case 1: {
                logger.info("Ask for Debit card number.");
                return "<?xml version='1.0' encoding='UTF-8'?>" +
                        String.format("\n<response sid='%s'>", request.getSid()) +
                                       "\n  <collectdtmf l='16' t='#' o='15000'>" +
                                       "\n      <playtext>Please enter your debit card number followed by the # key</playtext>" +
                                       "\n  </collectdtmf>" +
                                    "\n</response>";
            }
            case 2: {
                logger.info("Ask for PIN number.");
                return "<?xml version='1.0' encoding='UTF-8'?>" +
                        String.format("\n<response sid='%s'>", request.getSid()) +
                                            "\n  <collectdtmf l='4' t='#' o='5000'>" +
                                            "\n     <playtext>Please enter your debit card PIN number followed by the # key</playtext>" +
                                            "\n  </collectdtmf>" +
                                        "\n</response>";
            }
            case 3: {
                logger.info("Ask for demand draft amount.");
                return "<?xml version='1.0' encoding='UTF-8'?>" +
                        String.format("\n<response sid='%s'>", request.getSid()) +
                                            "\n  <collectdtmf l='10' t='#' o='15000'>" +
                                            "\n     <playtext>Please enter the Demand Draft amount followed by the # key</playtext>" +
                                            "\n  </collectdtmf>" +
                                        "\n</response>";
            }
            case 4: {
                logger.info("Tell the demand draft amount and thank the customer.");
                return "<?xml version='1.0' encoding='UTF-8'?>" +
                        String.format("\n<response sid='%s'>", request.getSid()) +
                                            "\n  <playtext>You have entered </playtext>" +
                                            String.format("\n  <say-as format='401' lang='EN'>%s</say-as>", request.getData()) +
                                            "\n  <playtext>Your Demand Draft will be delivered in three working days. Thank you for choosing Thought Works phone banking services!</playtext>" +
                                            String.format("\n  <sendsms to='91%s'>", request.getCid()) +
                                                    "Your DD will be delivered in three working days. Transaction reference number: 123456. Thank you" +
                                            "\n  </sendsms>" +
                                            "\n  <hangup />" +
                                    "\n</response>";
            }
            default: {
                return "<?xml version='1.0' encoding='UTF-8'?>" +
                        String.format("\n<response sid='%s'>", request.getSid()) +
                                       "\n  <playtext>Welcome to Thought Works phone banking services!</playtext>" +
                                       "\n</response>";
            }
        }
    }

    private String playError() {
        return  "<?xml version='1.0' encoding='UTF-8'?>" +
                "\n<response>" +
                    "\n<playtext>Sorry, something went wrong. Please call later.</playtext>" +
                    "\n<hangup/>" +
                "\n</response>";
    }

    private boolean isValidDebitCard(KooKooRequest request) {
        return "1234567890123456".equals(request.getData());
    }

    private boolean isValidPIN(KooKooRequest request) {
        return "9999".equals(request.getData());
    }

    private boolean isValidDDAmount(KooKooRequest request) {
        try {
            final long ddAmount = Long.parseLong(request.getData());
            if (ddAmount > 0) return true;
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }
}
