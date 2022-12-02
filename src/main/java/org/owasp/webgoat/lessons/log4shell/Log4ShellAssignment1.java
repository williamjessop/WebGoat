package org.owasp.webgoat.lessons.log4shell;

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
import java.util.StringJoiner;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;

@RestController 
// @AssignmentHints({"lesson-template.hints.1", "lesson-template.hints.2", "lesson-template.hints.3"}) 
public class Log4ShellAssignment1 extends AssignmentEndpoint { 
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final String secretValue = "secr37Value";

    @Autowired
    private UserSessionData userSessionData; 

    // Attempt for http lesson validation
    // @GetMapping("/log4shell/log4shell1")
    // public void setComplete(){
    //     solved = true;
    // }

    @PostMapping("/log4shell/assignment1") 
    @ResponseBody
    public AttackResult completed(@RequestParam("param1") String param1, @RequestParam("param2") String param2) { 
        try{
            URL url = new URL("http://localhost:8080/login");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            // Setup POST params
            Map<String,String> arguments = new HashMap<>();
            arguments.put("uname", param1);
            arguments.put("password", param2);
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" 
                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            // Send URL encoded form
            con.setFixedLengthStreamingMode(length);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            con.connect();
            try(OutputStream os = con.getOutputStream()) {
                os.write(out);
            }
            log.info("SENT FORM TO SERVER WITH " + param1);

            TimeUnit.SECONDS.sleep(2);

        }catch(Exception e){
            log.info("TRIED TO SEND AND FAILED");
        }

        

        //overly simple example for success. See other existing lessons for ways to detect 'success' or 'failure'
        File tempFile = new File("/tmp/log4j-docker/filename.txt");
        if (tempFile.exists()) {
            return success(this) 
                    .output("I did it")
                    .feedback("log4shell.assignment1.success")
                    .build();
            //lesson-template.sample-attack.success is defined in src/main/resources/i18n/WebGoatLabels.properties
        }



        // else
        return failed(this) 
                .feedback("log4shell.assignment1.fail")
                .output("I failed but did it")
                .build();
    }
}