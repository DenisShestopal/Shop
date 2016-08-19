# Task:
<br>Система Интернет-магазин. 
<br>+ 1. Администратор осуществляет ведение каталога Товаров.
<br>+ 2. Клиент делает и оплачивает Заказ на Товары.
<br>+ 3. Администратор может занести неплательщиков в “черный список”.

1. The Administrator can maintain the Products catalog.
2. The client makes and pays goods orders.
3. The administrator can add users with unpaid orders to the blacklist.

#Done
<br>1. Domain model.
<br>2. CRUD Service and Dao and Controllers interfaces and Classes.
<br>3. JSPs for each Entity for CRUD operations.
<br>4. Authorization.
<br>5. Logging.
<br>6. Comments

#Technologies used
Database: MySQL
UI: JSP, JSTL, HTML, CSS
Spring (Core, MVC)
ORM: Hibernate
Servlet
Generic DAO Patterns
Token Authentication
Server: Tomcat

#Todo
<br>Add Tests

#Extra Todo
<br>Add exceptions
<br>Blacklist create management .jsp


#Requirements
<br>+ 1. На основе сущностей предметной области создать классы их описывающие.
<br>+ 2. Классы и методы должны иметь отражающую их функциональность названия
<br> и должны быть грамотно структурированы по пакетам.
<br>+ 3. Оформление кода должно соответствовать Java Code Convention.
<br>+ 4. Информацию о предметной области хранить в БД, для доступа использовать API JDBC с использованием пула соединений,
<br> стандартного или разработанного самостоятельно. В качестве СУБД рекомендуется MySQL или Derby.
<br>+ 5. Приложение должно поддерживать работу с кириллицей (быть многоязычной), в том числе и при хранении информации в БД.
<br>+ 6. Архитектура приложения должна соответствовать шаблону Model-View-Controller.
<br>+- 7. При реализации алгоритмов бизнес-логики использовать
<br> шаблоны GoF: Factory Method, Command, Builder, Strategy, State, Observer etc.
<br>+ 8. Используя сервлеты и JSP, реализовать функциональности, предложенные в постановке конкретной задачи.
<br>+ 9. В страницах JSP применять библиотеку JSTL и разработать собственные теги.
<br>+ 10. При разработке бизнес логики использовать сессии и фильтры.
<br>+ 11. Выполнить журналирование событий, то есть информацию о возникающих исключениях и событиях в системе обрабатывать с помощью Log4j.
<br>+ 12.	Код должен содержать комментарии.

<br>+ 1. Create entities and classes.
<br>+ 2. Classes and method should have appropriative names and shoud be correctly structured by packages.
<br>+ 3. Java Code Convention rules required.
<br>+ 4. Information about entities should be stored in the DataBase, API JDBC and connections pool using to get access.
<br> As DBMS MySQL или Derby recommended.
<br>+ 5. Multilanguage app required.
<br>+ 6. Model-View-Controller architecture required.
<br>+- 7. Using GoF patterns: Factory Method, Command, Builder, Strategy, State, Observer etc.
<br>+ 8. Using Servlets and JSP, functionalities need to be realized.
<br>+ 9. JSTL in JSP usage required, implement own tags.
<br>+ 10. Sessions and Filters usage required.
<br>+ 11. Make logging using Log4j.
<br>+ 12.	Comments required.

