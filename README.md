#Test automation JAVA project (Selenide + Selenoid)


1) Скачать и установить десктопную версию Docker, зарегистрировать аккаунт
 https://www.docker.com/products/docker-desktop
 Переменные окружения устанавливать для докера не нужно. 
 
2) Запускаем Docker desktop и проверяем, чтобы иконка кита в левом нижнем углу программы была зеленого цвета 

3) Зходим на https://github.com/aerokube/selenoid/releases и скачиваем файл selenoid_windows_amd64.exe

4) Переименовываем его в cm.exe (или просто в cm)

5) Создать в проекте папку с названием remote и переместить туда exe файл (расположение папки и её название
можете указать по желанию)

6) Запускаем прямо из папки remote PowerShell (лучше от администратора).
В Windows 10 "Файл" -> Запустить Windows PowerShell -> Запустить от админ.

7) Выполняем: 
**.\cm.exe selenoid start --vnc**
В результате выполнения этой команды произойдёт загрузка образов с 
-сервером, 
то есть образов, в которых доступна возможность видеть экран браузера в реальном времени. Также будет 
скачана свежая версия Selenoid вместе с контейнерами, исполняемые файлы веб-драйверов,
будут созданы файлы конфигурации и последним этапом будет сразу же запущен сам Selenoid. 

8) От Docker desktop должно прийти уведомление вида "можно ли присоединиться к selenoid?"
Подтвердить, нажав ок

9) Запускаем Selenoid UI:
 **.\cm.exe selenoid-ui start**
мы скачиваем и запускаем Selenoid UI – графическую оболочку, через которую мы можем посмотреть ход выполнения наших тестов в реальном времени, 
видеозаписи выполнения сценариев и примеры конфигурационных файлов, собрать какую-то статистику и т.д

10) Проверяем наличие активных контейнеров в том же открытом PowerShell:
 **docker ps**
 
11) Проверяем наличие активных загруженных образов в терминале:
**docker image ls**
 
12) Проверяем наличие активных контейнеров в докер-десктоп во вкладке Containers/Apps

13) Проверяем активные образы в докер-десктоп во вкладке Images:

14) Проверяем в браузере, что сервер селеноида доступен на локалхосте
http://localhost:4444/wd/hub
Если сервер поднят, высветится надпись вида «You are using Selenoid 1.10.3!»

15) Проверяем в браузере, что Selenoid UI доступен
http://localhost:8080/#/

16) Запускаем проект java, предварительно убедившись, что установлены корректные конфигурации.
Ниже представлен рабочий код для предустановок:

private static void setBrowserSettings() {
    
    Configuration.remote = "http://localhost:4444/wd/hub";
    Configuration.browser = "chrome";
    Configuration.browserSize = "1280x1024";
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("enableVNC", true);
    capabilities.setCapability("enableVideo",true);
    Configuration.browserCapabilities = capabilities;
}

17) После запуска тестового проекта сразу же открываем Selenoid UI и следим за статистикой выполнения

18) Проваливаемся в активный тест, справа – логи, слева – браузер в активном режиме

19) Останавливаем контейнеры после выполнения тестов:
**docker stop selenoid selenoid-ui**
 


#Дополнительны команды, которые могут понадобиться:

1) Узнать информацию о запущенных контейнерах:
**docker ps**
2) Удалить контейнеры:
**docker rm selenoid selenoid-ui**
3) Очистка всех созданных конфигураций:
**.\cm.exe selenoid cleanup**
4) Повторный запуск конфигурирования:
**docker run --rm -v /var/run/docker.sock:/var/run/docker.sock -v ${HOME}:/root -e OVERRIDE_HOME=${HOME} aerokube/cm:latest-release selenoid start**
