package tag;

import entity.User;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class CustomTag extends SimpleTagSupport {
    private User user;

    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        if (user != null) {
            out.println(user.getFirstName() + ' ' + user.getLastName());
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
}
