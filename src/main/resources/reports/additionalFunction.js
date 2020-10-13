/**
 * Выпадающий список (Select)"
 * @returns {string}
 */
function initSelect(name, textForValue0, type, param, properties) {
    var html = '';
    var url = contextPath + "/rest/newRest/1.0/reportresource/getSelectResult?type=" + type + "&param=" + param;
    html += '<select  class="select"  name="' + name + '" ' + 'id="' + name + '" ' + properties + '>';
    html += ' <option value="0">' +  textForValue0 + '</option>';
    if (param !== "false") {
        inquiryREST(url)
            .then(
                function (array) {
                        for (var key in array) {
                            html += '<option value="' + key + '">' + array[key] + '</option>';
                        }
                });
    }
    html += '</select>';
    return html;
}

// выполнить при изменении управления
AJS.$(document).on('change', '#upravlenieId', function () {
    try {
        var name = "departmentId";
        var textForValue0 = "- Выберите значение -";
        var type = "DEPARMENT";
        var html = initSelect(name, textForValue0, type,  AJS.$('#upravlenieId option:selected').val(), "");
        document.getElementById('departmentId').remove();
        document.getElementById('depart').insertAdjacentHTML('beforeend', html);
    } catch (e) {
        console.log("не найдена функция getDepartmentsByUpravlenie скорее всего не подключен скрипт select.js");
    }
});

function sendMessage(element, type, title, body) {
    AJS.$(element).html('');
    switch (type) {
        case 'success':
            AJS.messages.success("#aui-message-bar", {
                title: title,
                //body: body
            });
            break;
        case 'info':
            AJS.messages.info("#aui-message-bar", {
                title: title,
                //body: body
            });
            break;
        case 'warning':
            AJS.messages.warning("#aui-message-bar", {
                title: title,
                //body: body
            });
            break;
        case 'error':
            AJS.messages.error("#aui-message-bar", {
                title: title,
                //body: body
            });
            break;
    }
}

/**
 * Получим идентифкатор создания отчета
 * @returns {never}
 */
function startReport() {
    AJS.$.ajax({
        type: 'GET',
        url: contextPath + "/rest/newRest/1.0/reportStatus/start",
        datatype: 'json',
        async: false,
        success: function (o) {
            var message = '<font color="' + colorConfig.error + '">Отчет формируется</font>';
            var messageEnd = '<font color="' + colorConfig.info + '"><span class="aui-icon aui-icon-small aui-iconfont-approve"></span>Отчет выгружен</font>';
            AJS.$("#reportStatusId").val(o.id);
            AJS.$('#button-spinner-message').html(message);
            AJS.$('#button-spinner').spin();
            var timer = setInterval(function () {
                disabled();
                statusReport(o.id).then(function (answer) {
                    if (answer.status === "PROCESSED") {
                        clearInterval(timer);
                        AJS.$('#button-spinner-message').html(messageEnd);
                        AJS.$('#button-spinner').spinStop();
                        enabled();
                    }
                }, function (answer) {
                    clearInterval(timer);
                    AJS.$('#button-spinner').spinStop();
                });
            }, 1000);
        },
        error: function (jqXHR, textStatus, errorThrown) {

        }
    });

    var deferred = AJS.$.Deferred();

    AJS.$.ajax({
        url: contextPath + "/rest/newRest/1.0/reportStatus/start",
        type: "GET",
        contentType: "application/json",
        async: true,
        success: function (array) {
            deferred.resolve(array);
        },
        error: function (response) {
            deferred.reject(response);
        }
    });

    return deferred.promise();
}



/**
 * Запросим статус формирования отчета
 *
 * @param id - номер отчета
 * @returns {never}
 */
function statusReport(id) {
    var deferred = AJS.$.Deferred();

    AJS.$.ajax({
        url: contextPath + "/rest/newRest/1.0/reportStatus/process/" + id,
        type: "GET",
        contentType: "application/json",
        async: true,
        success: function (array) {
            deferred.resolve(array);
        },
        error: function (response) {
            deferred.reject(response);
        }
    });

    return deferred.promise();
}

var colorConfig = {
    'field': "#FFFFFF",
    'fieldError': "#F9A5AA",

    'block': "#f0ad4e",
    'blockText': "#ffffff",
    'error': "#d9534f",
    'errorText': "#ffffff",
    'info': "#337ab7",
    'infoText': "#ffffff",
    'success': "#419641",
    'successText': "#ffffff",

    'add': "#8eb021",
    'edit': "#3572b0",
    'delete': "#d04437",

    'tableSuccess': "#98ff99",
    'tableError': "#cacaca",

    'holiday': "#cbeaff",
    'pre-holiday': "#e0ffcb",
    'work-holiday': "#ffffcb"
};

(function ($) {
    "use strict";

    var contextPath = null;

    $(function () {
        contextPath = $("meta[name='ajs-setup-context-path']").attr("content");
    });

    function getOurContextPath() {
        return contextPath ? contextPath : "";
    }

    // Make our setup code work with the AMD module
    define("wrm/context-path", [], function () {
        return getOurContextPath;
    });

    // Polyfill the global in window, just in case
    window.AJS || (window.AJS = {});
    window.AJS.contextPath = getOurContextPath;
})(window.jQuery);

