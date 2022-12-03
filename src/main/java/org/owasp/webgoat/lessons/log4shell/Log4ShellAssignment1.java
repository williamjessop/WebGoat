package org.owasp.webgoat.lessons.log4shell;

import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.owasp.webgoat.container.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.owasp.webgoat.container.assignments.AssignmentHints;
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
@AssignmentHints({"log4shell.assignment1.hints.1", "log4shell.assignment1.hints.2", "log4shell.assignment1.hints.3"}) 
public class Log4ShellAssignment1 extends AssignmentEndpoint { 
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private UserSessionData userSessionData; 

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

        String fileName = "/tmp/log4j-docker/filename.txt";

        File tempFile = new File(fileName);
        if (tempFile.exists()) {
            return success(this) 
                .output("The server has been exploited.")
                .feedback("log4shell.assignment1.success")
                .build();
        }

        return failed(this) 
                .feedback("log4shell.assignment1.fail")
                .output("That is not correct, we will be logging this attempt.")
                .build();
    }
}