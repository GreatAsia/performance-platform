package com.okay.testcenter;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.List;

public class LoginInterceptor extends AuthorizationFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = this.getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;
        boolean isAccessAllowed = false;
        boolean[] bs = null;
        if (rolesArray != null && rolesArray.length != 0) {
            List<String> roles = CollectionUtils.asList(rolesArray);
            bs = subject.hasRoles(roles);
        }
        if (bs == null) {
            return false;
        }
        for (boolean b : bs) {
            if (b) {
                isAccessAllowed = true;
                break;
            }
        }

        return isAccessAllowed;
    }
}

