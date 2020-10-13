package ru.rshbintech.jira.report.webwork;

import com.atlassian.jira.web.action.JiraWebActionSupport;

public class Test extends JiraWebActionSupport {

    @Override
    protected String doExecute() throws Exception {
        return "TEST";
    }
}
