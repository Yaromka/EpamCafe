package com.epam.cafe.content;

import com.epam.cafe.constants.SessionAttr;

import java.util.HashMap;
import java.util.Map;

public class RequestContent {

    private static final String PARAM_COMMAND = "command";
    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;
    private String url;

    public RequestContent() {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        url = null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public void addRequestAttribute(String name, Object attribute)
    {
        requestAttributes.put(name, attribute);
    }

    public String getRequestParameterByName(String parameterName) {
        String[] parameters = requestParameters.get(parameterName);
        if(parameters != null) {
            return parameters[0];
        }
        return null;
    }

    public void setSessionAttributesForPagination(int numberOfPages, int pageNumber, String parameters) {
        this.setSessionAttributes(SessionAttr.NUMBER_OF_PAGES, numberOfPages);
        this.setSessionAttributes(SessionAttr.CURRENT_PAGE, pageNumber);
        this.setSessionAttributes(SessionAttr.PAGINATION_URL, buildUrlForPagination(parameters));
    }

    public void setRequestParameters(Map<String, String[]> parameters)
    {
        requestParameters = parameters;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public Object getSessionAttributeByName(String attributeName) {
        return sessionAttributes.get(attributeName);
    }

    public void setSessionAttributes(String name, Object attribute) {
        sessionAttributes.put(name, attribute);
    }

    public void sessionInvalidate() {
        requestAttributes = new HashMap<>();
        sessionAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
    }

    public String getCommandName() {
        return requestParameters.get(PARAM_COMMAND)[0].toUpperCase();
    }

    private String buildUrlForPagination(String commandParameters) {
        String commandName = getCommandName();
        return "/controller?command="+commandName+commandParameters;
    }

    public Map<String, String[]> getRequestParameters() {
        return requestParameters;
    }
}

