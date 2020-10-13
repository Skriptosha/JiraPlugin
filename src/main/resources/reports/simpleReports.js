/**
 * Основная функция JS для отчетов по шаблону simpleReports. Если отчет не шаблонный, в базу данных,
 * в таблице simpleReports необходимо прописать какую функцию необходимо будет вызвать для данного
 * отчета в столбце JSFunction.
 *
 * @param id procedureId отчета
 * @returns {string} сформированный по шаблону html
 */

function simpleReports(id) {
    AJS.$('#aui-message-bar').empty();
    var html = '';
        html += '<form id="reportForm" class="aui" action="' + contextPath + '/secure/SimpleReportsActionN.jspa" method="post"' +
        ' onsubmit="return getAvailableDataSimple();">';
        html += ' <input class="hidden" id="procedureId" name="procedureId" value="' + id + '">';
        html += ' <input class="hidden" id="reportStatusId" name="reportStatusId" value="0" />';
    var url = "/rest/newRest/1.0/jirareports/js?param=" + id;
    inquiryREST(url)
        .then(
            function (result) {
                var func = window[result];
                html += result == null ? generatePage(id) : func();
            });
    html += '</form>';
    return html;
}

/**
 * Шаблонное формирование страницы. Используется, если в столбце JSFunction указано null
 * @param id procedureId отчета
 * @returns {string} сформированный по шаблону html
 */
function generatePage(id) {
    var html = '';
    var url = "/rest/newRest/1.0/jirareports/param?param=" + id;
    inquiryREST(url)
        .then(
            function (result) {
                var multiple;
                var getKeyOrValue;
                var minimumInputLength;
                var idElement;
                var url;
                var name;
                var textForValue0;
                var type;
                var param;
                var properties;

                html += '<div class="aui-flatpack-example layout-example">';
                if (result.hasOwnProperty("upload_file")) {
                    html += ' <h3> Загрузка Excel файла для отчета </h3>';
                    html += '<div class="aui-group">';
                     //   html += '   <label for="uploadExcel">Выберите файл Excel: </label>';
                        html += '<div class="aui-item">';
                            html += '<p>';
                            html += ' <input class="upfile" type="file" id="uploadExcel" name="Загрузка файла Excel">';
                            html += ' <button class="aui-button" type="button" id="uploadButton">Загрузить Отчет</button>';
                            html += '</p>';
                        html += ' </div>';
                    html += ' </div>';

                }

                if (result.hasOwnProperty("startDate")) {
                        html += '   <h3> Выберите период: </h3>';
                        html += '<div class="aui-group">';
                            html += '<div class="aui-item">';
                                html += '<p>';
                                result.hasOwnProperty("endDate") ? html += '   <label for="startDate">c </label>'
                                : html += '   <label for="startDate">Дата </label>';
                             //   html += '<input class="aui-date-picker" autocomplete="off" form="reportForm" name="startDate" id="startDate" type="date"/>'
                                html += '   <input class="text medium-field datepicker-input" id="startDate" name="startDate" readonly />';
                                html += '     <span class="js-start-date-trigger aui-icon aui-icon-small aui-iconfont-calendar">Выберите дату</span>';
                    //    initDatePicker("startDate");
                                html += '</p>';
                            html += ' </div>';
                        html += ' </div>';
                 //   if (!result.hasOwnProperty("endDate")) html += ' </div>';

                }
                if (result.hasOwnProperty("endDate")) {
                    html += '<div class="aui-group">';
                        html += '<div class="aui-item">';
                            html += '<p>';
                            html += '   <label for="endDate">по </label>';
                          //  html += '<input class="aui-date-picker" autocomplete="off" form="reportForm" name="endDate" id="endDate" type="date"/>'
                            html += '   <input class="text medium-field datepicker-input" id="endDate" name="endDate" readonly />';
                            html += '     <span class="js-start-date-trigger aui-icon aui-icon-small aui-iconfont-calendar">Выберите дату</span>';
                          //  initDatePicker("endDate");
                            html += '</p>';
                        html += ' </div>';
                    html += ' </div>';
                }

                if (result.hasOwnProperty("userCode")) {
                    html += ' <input class="hidden" id="userCode" name="userCode" value="' + getUserCode() + '" readonly />';
                }

                if (result.hasOwnProperty("upravlenieId") && !result.hasOwnProperty("departmentId")) {

                    name = "upravlenieId";
                    textForValue0 = "- Выберите значение -";
                    type = "UPRAVLENIE";
                    param = "";
                    properties = "style=\"display: block\"";

                    html += '   <h3> Выберите подразделение: </h3>';
                    html += '<div class="aui-group">';
                        html += '<div class="aui-item">';
                            html += '<p>';
                            html += '   <label for="upravlenieId">Управление</label>';
                            html += '   <div id="upravlenieId">' + initSelect(name, textForValue0, type, param, properties) + '</div>';
                            html += '</p>';
                        html += '</div>';
                    html += '</div>';
                }

                if (result.hasOwnProperty("departmentId") && result.hasOwnProperty("upravlenieId")) {

                    name = "upravlenieId";
                    textForValue0 = "- Выберите значение -";
                    type = "UPRAVLENIE";
                    param = "";
                    properties = "style=\"display: block\"";

                    html += '   <h3> Выберите подразделение: </h3>';
                    html += '<div class="aui-group">';
                        html += '<div class="aui-item">';
                            html += '<p>';
                            html += '   <label for="upravlenieId" style="float: left;">Управление</label>';
                            html += initSelect(name, textForValue0, type, param, properties);
                            html += '</p>';
                        html += '</div>';

                    name = "departmentId";
                    textForValue0 = "- Выберите значение -";
                    type = "DEPARMENT";
                    param = "false";
                        html += '<div class="aui-item">';
                            html += '<p id="depart">';
                            html += '   <label for="departmentId" style="float: left;">Департамент</label>';
                            html +=    initSelect(name, textForValue0, type, param, properties);
                            html += '</p>';
                        html += '</div>';
                    html += ' </div>';
                }

                if (result.hasOwnProperty('fioKPI')) {
                    html += ' <h3> Выберите ФИО сотрудника </h3>';
                    html += '<div class="aui-group">';
                        html += '<div class="aui-item">';
                            html += '<p>';
                            html += ' <label for="fioKPI">ФИО сотрудника</label>';
                            html += ' <input id="fioKPI" name="fioKPI" style="width: 295px" placeholder="Начните вводить имя пользователя..."/>';
                            html += '</p>';
                        html += '</div>';
                    html += ' </div>';

                    url = "FIO";
                    idElement = "#fioKPI";
                    minimumInputLength = 3;
                    multiple = false;
                    getKeyOrValue = "value";
                    initAutocomplete(url, idElement, minimumInputLength, multiple, getKeyOrValue);
                }

                if (result.hasOwnProperty('biqMultiple')) {
                    html += ' <h3> Выберите Номер БИК </h3>';
                    html += '<div class="aui-group">';
                        html += '<div class="aui-item">';
                            html += '<p>';
                            html += ' <label for="biqMultiple">BIQ-</label>';
                            html += ' <input id="biqMultiple" name="biqMultiple" style="width: 295px" placeholder="Начните вводить номер БИ..."/>';
                            html += '</p>';
                        html += '</div>';
                    html += ' </div>';

                    url = "BIQ";
                    idElement = "#biqMultiple";
                    minimumInputLength = 2;
                    multiple = true;
                    getKeyOrValue = "key";
                    initAutocomplete(url, idElement, minimumInputLength, multiple, getKeyOrValue);
                }

                if (result.hasOwnProperty('psNum')) {
                    html += ' <h3> Выберите Номер План сметы </h3>';
                    html += '<div class="aui-group">';
                        html += '<div class="aui-item">';
                            html += '<p>';
                            html += ' <label for="psNum">PS </label>';
                            html += ' <input class="text" id="psNum" name="psNum" type="number"/>';
                            html += '</p>';
                        html += ' </div>';
                    html += ' </div>';

                    url = "PS";
                    idElement = "#psNum";
                    minimumInputLength = 2;
                    multiple = false;
                    getKeyOrValue = "key";
                    initAutocomplete(url, idElement, minimumInputLength, multiple, getKeyOrValue);
                }

                if (result.hasOwnProperty('planRelease') && result.hasOwnProperty("radioStatus")) {

                    name = "planRelease";
                    textForValue0 = "Все включенные в план";
                    type = "CUSTOM_FIELD";
                    param = 11600;

                    html += ' <h3> Выберите Группу приоритизации </h3>';
                    html += '<div class="aui-group">';
                        html += '<div class="aui-item">';
                            html += '<p>';
                            html += '   <input class="radio" type="radio" checked="checked" name="radioStatus" id="radioStatus1">';
                            html += '   <label for="radioStatus1">Включено в план:</label>';
                            html +=     initSelect(name, textForValue0, type, param, "");
                            html += '</p>';
                        html += ' </div>';
                        html += '<div class="aui-item">';
                            html += '<p>';
                            html += '   <input class="radio" type="radio" name="radioStatus" id="radioStatus2">';
                            html += '   <label for="radioStatus1">Закрытые</label>';
                            html += '</p>';
                        html += ' </div>';
                    html += ' </div>';
                }
                html += '</div>';
                html += '<div class="aui-group">';
                    html += '<button type="submit" class="aui-button aui-button-primary" id="btnSimpleReports">Выгрузить Отчет в Excel</button>';

                html += '</div>';

            }, function (result) {
                console.log('reject');
                console.log(result.responseText)
            });
    return html;
}