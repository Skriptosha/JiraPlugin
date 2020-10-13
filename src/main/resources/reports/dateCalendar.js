/**
 * Параметры для инициализации календаря
 *
 * @param elementId
 * @returns {{align: string, button: *, cache: boolean, context: *, daFormat: string, date: string, dateStatusFunc: null, dateText: null, disableFunc: null, displayArea: null, electric: boolean, eventName: string, firstDay: number, flat: null, flatCallback: null, ifFormat: string, inputField: *, multiple: null, onClose: null, onSelect: null, onUpdate: null, position: null, showOthers: boolean, showsTime: boolean, singleClick: boolean, step: number, timeFormat: string, useISO8601WeekNumbers: boolean, weekNumbers: boolean}}
 */
function initCalendar(elementId) {
  return {
    align: "Br",
    button: AJS.$('a#' + elementId + '-trigger'),
    cache: false,
    context: AJS.$('div.jira-dialog-content'),
    daFormat: "%Y-%m-%d",
    date: new Date(),
    dateStatusFunc: null,
    dateText: null,
    disableFunc: null,
    displayArea: null,
    electric: true,
    eventName: "click",
    firstDay: 1,
    flat: null,
    flatCallback: null,
    ifFormat: "%Y-%m-%d",
    inputField: AJS.$('input#' + elementId + '.text.medium-field.datepicker-input'),
    multiple: null,
    onClose: null,
    onSelect: null,
    onUpdate: null,
    position: null,
    showOthers: false,
    showsTime: false,
    singleClick: true,
    step: 1,
    timeFormat: "24",
    useISO8601WeekNumbers: false,
    weekNumbers: true
  };
}