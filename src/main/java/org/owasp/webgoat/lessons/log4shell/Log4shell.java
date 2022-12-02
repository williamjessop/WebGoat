package org.owasp.webgoat.lessons.log4shell;

import org.owasp.webgoat.container.lessons.Category;
import org.owasp.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class Log4shell extends Lesson {
 @Override
    public Category getDefaultCategory() {
        return Category.LOG4SHELL;
    }

    @Override
    public String getTitle() {
        return "log4shell.title";
    }
}