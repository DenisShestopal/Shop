# Task:
<br>Система Интернет-магазин. 
<br>+ 1. Администратор осуществляет ведение каталога Товаров.
<br>- 2. Клиент делает и оплачивает Заказ на Товары.
<br>+ 3. Администратор может занести неплательщиков в “черный список”.

#Done
<br>1. Domain model.
<br>2. CRUD Service and Dao and Controllers interfaces and Classes.
<br>3. JSPs for each Entity for CRUD operations.

#Todo
<br>Fix addToOrder (basket) aspect
<br>Complete orders.jsp
<br>Divide 'add' and 'update' functional in add method in Controllers
<br>Authorization
<br>Add Logging
<br>Add Tests
<br>Write comments
<br>Change id type from int to Integer in the Base Entity
<br>Add exceptions

#Extra Todo
<br>Implement own TAG which will include regular expressions for entered data.

#Requirements
<br>+ 1. На основе сущностей предметной области создать классы их описывающие.
<br>+ 2. Классы и методы должны иметь отражающую их функциональность названия
<br> и должны быть грамотно структурированы по пакетам.
<br>+ 3. Оформление кода должно соответствовать Java Code Convention.
<br>+ 4. Информацию о предметной области хранить в БД, для доступа использовать API JDBC с использованием пула соединений,
<br> стандартного или разработанного самостоятельно. В качестве СУБД рекомендуется MySQL или Derby.
<br>+ 5. Приложение должно поддерживать работу с кириллицей (быть многоязычной), в том числе и при хранении информации в БД.
<br>+ 6. Архитектура приложения должна соответствовать шаблону Model-View-Controller.
<br>- 7. При реализации алгоритмов бизнес-логики использовать
<br> шаблоны GoF: Factory Method, Command, Builder, Strategy, State, Observer etc.
<br>+- 8. Используя сервлеты и JSP, реализовать функциональности, предложенные в постановке конкретной задачи.
<br>+- 9. В страницах JSP применять библиотеку JSTL и разработать собственные теги.
<br>+- 10. При разработке бизнес логики использовать сессии и фильтры.
<br>- 11. Выполнить журналирование событий, то есть информацию о возникающих исключениях и событиях в системе обрабатывать с помощью Log4j.
<br>- 12.	Код должен содержать комментарии.
