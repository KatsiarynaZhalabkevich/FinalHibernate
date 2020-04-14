package by.epam.web.tag;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeJSPTag extends TagSupport {
    public static Locale locale=Locale.ENGLISH;
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, YYYY", locale);
            out.print(dateFormat.format(Calendar.getInstance().getTime()));
        } catch (Exception e) {
            System.out.println(e);
        }
        return SKIP_BODY;
    }




}
