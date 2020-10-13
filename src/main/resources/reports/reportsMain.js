var auiMessageBar = '#aui-message-bar'; // панель куда выводятся сообщения

/**
 * действия при нажатии на меню
 */
AJS.$(document).on('click', '.reports-menu', function () {
  // удалим выделение с выделенного элемента
  AJS.$('.aui-nav-selected').removeClass('aui-nav-selected');
  // достанем данные с элемента на который нажали
  var reportsId = AJS.$(this).attr('id');
  // установим выделение и переключим вкладку
  AJS.$('#' + reportsId).parent().addClass('aui-nav-selected');
  setContentView(reportsChoiceRun(AJS.$(this).attr('data-id')));
  reportsCalendarInit(AJS.$(this).attr('data-id'));
});

/**
 * Функция запуска скрипта по типу (по выбранной вкладке)
 * @param type - id вкладки
 */
function reportsChoiceRun(type) {
  if (type.indexOf('tabs-menu-simple-report') > -1) {
    return simpleReports(type.substring(24, type.length));
  }
}

/**
 * Функция запуска скрипта для вызова календаря
 * @param type - id вкладки
 */
function reportsCalendarInit(type) {
  if (document.getElementById('startDate') && document.getElementById('endDate')) {
    Calendar.setup(initCalendar('startDate'));
    Calendar.setup(initCalendar('endDate'));
    AJS.$('#startDate').attr('placeholder', 'yyyy-mm-dd');
    AJS.$('#endDate').attr('placeholder', 'yyyy-mm-dd');
  }
  if (document.getElementById('startDate')) {
      Calendar.setup(initCalendar('startDate'));
      AJS.$('#startDate').attr('placeholder', 'yyyy-mm-dd');
  }
}

/**
 * вывести данные на страницу
 * @param data
 */
function setContentView(data) {
  AJS.$('#reports-content-body').html(data);
}


/**
 * Добавляем список отчетов simpleReports на страницу (будут под остальными отчетами)
 * @param user Имя пользователя
 */
function addSimpleReports(user) {
  if (document.getElementById('reports-menu-class'))
    document.getElementById('reports-menu-class').insertAdjacentHTML('beforeend', generateMenu(user));
}

/**
 * Получаем список групп, в которые добавлен пользователь
 * @param user Имя пользователя
 * @returns {Array} массив групп пользователя
 */
function getGroups(user) {
  var result = [];
  AJS.$.ajax({
    url: contextPath + "/rest/api/2/user?username=" + user + "&expand=groups",
    type: "GET",
    contentType: "application/json",
    async: false,
    success: function (data) {
      var groups = data.groups.items;
      for (var i = 0; i < groups.length; i++) {
        result.push(groups[i].name);
      }
    },
    error: function (response) {
      console.log(response);
    }
  });
  return result;
}

/**
 * Формирование меню отчетов. Форируется исходя из доступных пользователю групп,
 * и групп, необходимых для отображения отчета. Таблица simpleReports столбец GroupsForAccess.
 * @param user Имя пользователя
 * @returns {string} html со списком доступных отчетов
 */
function generateMenu(user) {
  var html = '';
  var url = "/rest/newRest/1.0/jirareports/report";
  var userAccess = getGroups(user).join().split(",");
  inquiryREST(url)
    .then(
      function (result) {
        url = "/rest/newRest/1.0/jirareports/access";
        inquiryREST(url)
          .then(
            function (accessReports) {
              for (var key in result) {
                var array = accessReports[key];
                if (check(userAccess, array)) {
                  html += '<li>';
                  html += '<a href="#"' +
                    ' class="reports-menu"' +
                    ' id="menu-report-simple-report' + key + '"' +
                    ' data-id="tabs-menu-simple-report-' + key + '">' + result[key] + '</a>';
                  html += '</li>';
                }
              }
            });
      });
  return html;
}

/**
 * Проверяем есть ли необходимые для отчета группы у пользователя
 * @param userAccess группы пользователя
 * @param accessReports группы для отчета
 * @returns {boolean}
 */
function check(userAccess, accessReports) {
  if (accessReports == null || accessReports === "") return true;
  var array = accessReports.split(',');
  return array.some(function (v) {
    return userAccess.indexOf(v.trim()) >= 0;
  });
}