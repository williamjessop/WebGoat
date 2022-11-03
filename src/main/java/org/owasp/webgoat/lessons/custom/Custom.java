package org.owasp.webgoat.lessons.custom;

import org.owasp.webgoat.container.lessons.Category;
import org.owasp.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class Custom extends Lesson {
 @Override
    public Category getDefaultCategory() {
        return Category.CUSTOM;
    }

    @Override
    public String getTitle() {
        return "custom.title";
    }
}