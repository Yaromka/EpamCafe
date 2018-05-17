package com.epam.cafe.content;

public class RequestResult {
    private String page;
    private NavigationType navigationType;

    public RequestResult(String page, NavigationType navigationType) {
        this.page = page;
        this.navigationType = navigationType;
    }

    public String getPage() {
        return page;
    }

    public NavigationType getNavigationType() {
        return navigationType;
    }
}
