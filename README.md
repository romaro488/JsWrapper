#JsWrapper application
# REST API wrapper around ScriptEngine
Написать REST API оболочку вокруг javax.script.ScriptEngine, которая будет давать возможность через апи

1) запускать код javascript, переданный в теле запроса, и возвращать обратно в теле ответа вывод скрипта на консоль либо сообщение об ошибке,
2) просматривать статус скрипта (завершен успешно, с ошибкой, выполняется, в очереди) и его консольный вывод
3) прибивать зависшие скрипты принудительно.

Запросы могут приходить параллельно, причем скрипт может выполняться долго, а может и вообще зависнуть, например в бесконечном цикле.