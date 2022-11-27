package org.owasp.webgoat.lessons.custom;

import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.owasp.webgoat.container.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
// import org.apache.logging.log4j.LoggerContext;
// import org.apache.logging.log4j.BasicConfigurator;

@RestController 
// @AssignmentHints({"lesson-template.hints.1", "lesson-template.hints.2", "lesson-template.hints.3"}) 
public class Custom123 extends AssignmentEndpoint { 

    
    // System.setProperty("log4j.configurationFile","./logging.xml");
    private static final Logger logger = LoggerFactory.getLogger(Custom123.class);

    private final String secretValue = "secr37Value";

    @Autowired
    private UserSessionData userSessionData; 

    @PostMapping("/custom/assignment1") 
    @ResponseBody
    public AttackResult completed(@RequestParam("param1") String param1, @RequestParam("param2") String param2) { 
        // LoggerContext context = LogManager.getContext(false);
        // File file = new File("/home/kali/Desktop/WebGoat/src/main/resources/log4j2.xml");
        // context.setConfigLocation(file.toURI());


        // BasicConfigurator.configure();
        logger.info("contents of param1: {}", param1);
        logger.info("${jndi:ldap://127.0.0.1/a}");
        logger.info("${java:version}");
        // logger.info(param1);
        // logger.debug("We've just greeted the user!");
        // logger.info("We've just greeted the user!");
        // logger.warn("We've just greeted the user!");
        // logger.error("We've just greeted the user!");
        // logger.fatal("We've just greeted the user!");
        
        
        
        if (userSessionData.getValue("some-value") != null) {
            // do any session updating you want here ... or not, just comment/example here
            //return failed(this).feedback("lesson-template.sample-attack.failure-2").build();
        }

        //overly simple example for success. See other existing lessons for ways to detect 'success' or 'failure'
        if (secretValue.equals(param1)) {
            return success(this) 
                    .output("I did it")
                    .feedback("custom.assignment1.success")
                    .build();
            //lesson-template.sample-attack.success is defined in src/main/resources/i18n/WebGoatLabels.properties
        }

        // else
        return failed(this) 
                .feedback("custom.assignment1.fail")
                .output("I failed but did it")
                .build();
    }
}