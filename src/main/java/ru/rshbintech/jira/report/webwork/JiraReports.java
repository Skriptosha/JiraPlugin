package ru.rshbintech.jira.report.webwork;

import com.atlassian.jira.web.action.JiraWebActionSupport;

public class JiraReports extends JiraWebActionSupport {
    @Override
    public String execute() throws Exception {
        return "reports-action-success";
    }
}
