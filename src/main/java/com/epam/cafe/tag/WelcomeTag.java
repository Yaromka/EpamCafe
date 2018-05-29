package com.epam.cafe.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Calendar;

public class WelcomeTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger(WelcomeTag.class);

    /**
     * Creates a tag on the header of page.
     * Which checks the day of the week and write some sentence.
     */
    @Override
    public int doStartTag() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        String currentDay = "";
        switch (day) {
            case Calendar.SUNDAY:
                currentDay = "Sunday";
                break;
            case Calendar.MONDAY:
                currentDay = "Monday";
                break;
            case Calendar.TUESDAY:
                currentDay = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                currentDay = "Wednesday";
                break;
            case Calendar.THURSDAY:
                currentDay = "Thursday";
                break;
            case Calendar.FRIDAY:
                currentDay = "Friday";
                break;
            case Calendar.SATURDAY:
                currentDay = "Saturday";
                break;
        }

        String endOfPhrase = " = Programmer - an organism that turns food into software.";
        JspWriter out = pageContext.getOut();
        try {
            out.write(currentDay + endOfPhrase);
        } catch (IOException e) {
            LOGGER.warn("An exception occurred during attempt to set welcome tag: ");
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}