package net.shop.util;


import net.shop.model.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class Hello extends TagSupport {
    private static final long serialVersionUID = 1L;

    public static String userLogin = "you can <a href=\"../../users/authorization\">SignIn</a> and get an account!";

//    public void setUserLogin(String userLogin){
//        this.userLogin = userLogin;
//    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print("Welcome, " + userLogin + "!");
        } catch(IOException ioException) {
            throw new JspException("Error: " + ioException.getMessage());
        }
        return SKIP_BODY;
    }
}
