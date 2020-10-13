function inquiryREST(url) {
    var deferred = AJS.$.Deferred();
    AJS.$.ajax({
        url: (contextPath + url),
        type: "GET",
        contentType: "application/json",
        async: false,
        success: function (result) {
            deferred.resolve(result);
        },
        error: function (response) {
            if (response.status === 401) reLog();
            deferred.reject(response);
        }
    });
    return deferred.promise();
}


/**
 * Если получили ошибку 401, делаем Релог
 */
function reLog() {
    document.location.href = contextPath + "/login.jsp?os_destination=" + contextPath + "/secure/JiraReports.jspa";
}

/**
 * Загрузка файла с помощью XMLHttpRequest()
 */
AJS.$(document).on('click', '#uploadButton', function () {
    console.log("XMLHttpRequest()");
    if (AJS.$("#uploadExcel").val() === '') {
        sendMessage('#aui-message-bar', 'warning', 'Не выбран файл для загрузки', '');
        return false;
    } else {
        AJS.$('#aui-message-bar').empty();
        disabledUpload();
        var url = contextPath + "/rest/newRest/1.0/jirareports/upload";
        var xhr = new XMLHttpRequest();
        var formData = new FormData();
        xhr.open('POST', url, true);
        xhr.addEventListener('readystatechange', function (e) {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    // Готово. Информируем пользователя
                    console.log("success");
                    stopUploadFlag("success");
                    enabledUpload();
                    sendMessage('#aui-message-bar', 'success', "Отчет успешно загружен! (" + xhr.status + ")", '');
                } else {
                    // Ошибка. Информируем пользователя
                    stopUploadFlag("error");
                    enabledUpload();
                    console.log("xhr.status " + xhr.status);
                    if (xhr.status === 304)
                        sendMessage('#aui-message-bar', 'error', "Выбран файл некорректного формата!", '');
                    else
                        sendMessage('#aui-message-bar', 'error', "При загрузке файла произошла ошибка!" + " (" + xhr.status + ")", '');
                }
            }
        });
        formData.append('file', document.getElementById("uploadExcel").files[0]);
        console.log("formData.append");
        xhr.send(formData);
    }
});

function stopUploadFlag(massage) {
    if (massage === 'success') {
        AJS.flag({
            type: 'success',
            body: 'Отчет успешно загружен.',
            close: 'auto'
        });
    } else {
        AJS.flag({
            type: 'error',
            body: 'Не удалось загрузить отчет.',
            close: 'auto'
        });
    }
}

/**
 * Проверяем что установлена дата, или указан номер БИКа, если они вообще присутствуют на странице
 * @returns {boolean}
 */
function isNullDateSimple() {
    var sd = AJS.$('#startDate').val();
    var ed = AJS.$('#endDate').val();
    var id = AJS.$('#procedureId').val();
    if (id === '14' || (ed === undefined && sd === undefined)) return true;
    else if (ed === undefined) return sd !== "" && sd.trim() !== '';
    else return sd !== "" && sd.trim() !== '' && ed !== "" && ed.trim() !== '';
}

function isNullBiqSimple() {
    var biq = AJS.$('#nameBiq').val();
    if (biq === undefined) return true;
    return biq !== "" && biq.trim() !== '';
}

function isNullPsNumSimple() {
    var psNum = AJS.$('#psNum').val();
    if (psNum === undefined) return true;
    return psNum !== "" && psNum.trim() !== '';
}

function getAvailableDataSimple() {
    if (AJS.$("#btnSimpleReports").val() !== undefined) {
        if (!isNullDateSimple()) {
            sendMessage('#aui-message-bar', 'warning', 'Не указана дата', '');
            return false;
        } else if (!isNullBiqSimple()) {
            sendMessage('#aui-message-bar', 'warning', 'Не указан номер БИ;', '');
            return false;
        } else if (!isNullPsNumSimple()) {
            sendMessage('#aui-message-bar', 'warning', 'Не указан номер ПС', '');
            return false;
        } else {
            AJS.$('#aui-message-bar').empty();
            disabled();
        }
    }
}

AJS.$(document).on('click', 'btnSimpleReports', function () {
  var that = this;
  if (!that.isBusy()) {
    that.busy();
    disabled();
    setTimeout(function () {
      enabled();
      AJS.flag({
        type: 'success',
        body: 'Отчет успешно сформирован.',
        close: 'auto'
      });
      that.idle();

    }, 5000);
  }
});


/**
 * JIRA auiSelect2. Работает с remote Data для поиска записи.
 *
 * @param urlRefreshParam
 * @param idElement ИД элемента на который необходимо навесить auiSelect2
 * @param minimumInputLength Минимальная длина для начала поиска в remote Data
 * @param multiple
 * @param getKeyOrValue
 */
function initAutocomplete(urlRefreshParam, idElement, minimumInputLength, multiple, getKeyOrValue) {
    if (urlRefreshParam !== null) inquiryREST("/rest/newRest/1.0/reportresource/refresh?query=" + urlRefreshParam);
    setTimeout(function () {
        AJS.$(idElement).auiSelect2({
            ajax: {
                url: contextPath + "/rest/newRest/1.0/reportresource/getAUISelect2result",
                delay: 300,
                dataType: 'json',
                data: function (params) {
                    return {
                        query: params
                    };
                },
                results: function (data) {
                    var results = [];
                    AJS.$.each(data, function (index, account) {
                        results.push({
                            id: index,
                            text: account
                        });
                    });
                    return {
                        results: results
                    };
                }
            },
            allowClear: true,
            multiple: multiple,
            dropdownAutoWidth: true,
            minimumInputLength: minimumInputLength,
            formatNoMatches: function (term) {
                return 'Совпадения не найдены'
            },
            formatSearching: function () {
                return 'Поиск...'
            },
            formatInputTooShort: function (term, minLength) {
                return 'Введите минимум ' + minLength + ' символ(а) для поиска'
            }
        }).on("change", function (e) {
            if (AJS.$(idElement).auiSelect2('data').length !== 0) {
                var string = "";
                var result = AJS.$(idElement).auiSelect2('data');
                var k;
                if (multiple) {
                    for (k in result) {
                        getKeyOrValue.toLowerCase() === "key" ? string = string + result[k]['id'] + ", "
                            : string = string + result[k]['text'] + ", ";
                    }
                } else {
                    for (k in result) {
                        if (getKeyOrValue.toLowerCase() === "key" && k === "id") string = result[k] + ", ";
                        else if (getKeyOrValue.toLowerCase() !== "key" && k === "text") string = result[k] + ", ";
                    }
                }
                document.getElementById(idElement.substring(1, idElement.length))
                    .value = string.substring(0, string.length - 2);
            }
        });
    }, 100);
}

/**
function initDatePicker(idElement) {
    setTimeout(function () {
        AJS.$(document).ready(function () {
            AJS.$('#' + idElement).datePicker({'overrideBrowserDefault': true, 'languageCode': 'ru'});
        });
    }, 100);
}
 */

function disabledUpload() {
    AJS.$("#uploadExcel").prop('disabled', true);
    document.getElementById('uploadButton')
        .insertAdjacentHTML('beforeend', '<aui-spinner id="spin" size="small"></aui-spinner>');
}

function enabledUpload() {
    AJS.$("#uploadExcel").prop('disabled', false);
    AJS.$('#spin').remove();
}

function disabled() {
    AJS.$(".aui *").prop('disabled', true);
    if (AJS.$("#fioKPI").val() !== undefined) AJS.$('#fioKPI').auiSelect2('disabled', true);
    document.getElementById('btnSimpleReports')
        .insertAdjacentHTML('beforeend', '<aui-spinner id="spin" size="small"></aui-spinner>');
}

function enabled() {
    AJS.$(".aui *").prop('disabled', false);
    if (AJS.$("#fioKPI").val() !== undefined) AJS.$('#fioKPI').auiSelect2('enable', false);
    AJS.$('#spin').remove();
}



