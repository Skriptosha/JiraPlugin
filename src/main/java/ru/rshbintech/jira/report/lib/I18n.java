package ru.rshbintech.jira.report.lib;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.I18nHelper;

public class I18n {
    private static ApplicationUser currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
    private static I18nHelper i18n = ComponentAccessor.getI18nHelperFactory().getInstance(currentUser);

    /**
     * Берем значение по ключу используя I18nHelper. Если такое значение не определено,
     * то возвращает текст после первой точки, или просто сам ключ, если точки нет в тексте
     *
     * @param key Ключ
     * @return Значение из файла проперти, либо текст после первой точки, или просто сам ключ, если точки нет в тексте
     */
    public static String getText(String key) {
        return i18n.isKeyDefined(key) ? i18n.getText(key) : (key.contains(".") ? key.split("[.]")[1] : key);
    }

    public static ApplicationUser getCurrentUser() {
        return currentUser;
    }
}
