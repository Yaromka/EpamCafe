package com.epam.cafe.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;

public class HttpRequestHelper {
    private RequestContent content;

    public HttpRequestHelper(RequestContent content) {
        this.content = content;
    }

    /**
     * Add request attributes, parameters and session attributes in RequestContent object.
     */
    public void getDataFromHttpRequest(HttpServletRequest request) {
        setRequestAttributes(request);
        setRequestParameters(request);
        setSessionAttributes(request);
    }

    /**
     * Add attributes and session attributes to request from content.
     */
    public void addDataToHttpRequest(HttpServletRequest request) {
        Map<String, Object> requestAttributes = content.getRequestAttributes();
        Map<String, Object> sessionAttributes = content.getSessionAttributes();
        requestAttributes.forEach(request::setAttribute);
        sessionAttributes.forEach((key, value)-> request.getSession().setAttribute(key, value));
    }

    private void setRequestAttributes(HttpServletRequest request) {
        Enumeration<String> requestAttributeNames = request.getAttributeNames();

        while (requestAttributeNames.hasMoreElements()) {
            String name = requestAttributeNames.nextElement();
            Object attribute = request.getAttribute(name);
            content.addRequestAttribute(name, attribute);
        }
    }

    private void setRequestParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        content.setRequestParameters(parameterMap);
    }

    private void setSessionAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Enumeration<String> sessionAttributeNames = session.getAttributeNames();

        while (sessionAttributeNames.hasMoreElements()) {
            String name = sessionAttributeNames.nextElement();
            Object sessionAttribute = session.getAttribute(name);
            content.setSessionAttributes(name, sessionAttribute);
        }
    }
}