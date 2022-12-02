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
import java.net.*;
import java.io.*;

@RestController 
// @AssignmentHints({"lesson-template.hints.1", "lesson-template.hints.2", "lesson-template.hints.3"}) 
public class Custom123 extends AssignmentEndpoint { 

    private final String secretValue = "secr37Value";

    @Autowired
    private UserSessionData userSessionData; 

    // Attempt for http lesson validation
    // @GetMapping("/custom/log4shell1")
    // public void setComplete(){
    //     solved = true;
    // }

    @PostMapping("/custom/assignment1") 
    @ResponseBody
    public AttackResult completed(@RequestParam("param1") String param1, @RequestParam("param2") String param2) { 
        if (userSessionData.getValue("some-value") != null) {
            // do any session updating you want here ... or not, just comment/example here
            //return failed(this).feedback("lesson-template.sample-attack.failure-2").build();
        }

        //overly simple example for success. See other existing lessons for ways to detect 'success' or 'failure'
        File tempFile = new File("/tmp/log4j-docker/filename.txt");
        if (tempFile.exists()) {
            return success(this) 
                    .output("I did it")
                    .feedback("custom.assignment1.success")
                    .build();
            //lesson-template.sample-attack.success is defined in src/main/resources/i18n/WebGoatLabels.properties
        }

        try{
            URL url = new URL("http://example.com");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        }catch(Exception e){
            // system.out.print("we died");
        }

        // else
        return failed(this) 
                .feedback("custom.assignment1.fail")
                .output("I failed but did it")
                .build();
    }
}