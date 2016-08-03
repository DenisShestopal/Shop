# Shop
Shop project
# Task:
<br>Система Интернет-магазин. 
<br>+ 1. Администратор осуществляет ведение каталога Товаров.
<br>- 2. Клиент делает и оплачивает Заказ на Товары.
<br>+ 3. Администратор может занести неплательщиков в “черный список”.

#Done
<br>1. Domain model created.
<br>2. CRUD Service and Dao and Controllers interfaces and Classes were created.
<br>3. JSPs for each Entity for CRUD operations were created.

#Todo
<br>1. Realize Orders page so that it shows each user's products list added to basket (ordered).
<br>2. Realize correctly products and users editing.
<br>3. + Make possible to set a user out of the black list (using edit form).
<br>4. Realise addToOrder method.
<br>5. Realize confirm/pay methods.
<br>6. Realize Autorization.
<br>7. Implement own TAG which will include regular expressions for entered data.
<br>8. Write comments.
<br>9. Manage Logger.
<br>10. Make Tests.
<br>11. Change id type from int to Integer in the Base Entity.

#Requirements
<br>+ 1. На основе сущностей предметной области создать классы их описывающие.
<br>+ 2. Классы и методы должны иметь отражающую их функциональность названия
<br> и должны быть грамотно структурированы по пакетам.
<br>- 3. Оформление кода должно соответствовать Java Code Convention.
<br>+ 4. Информацию о предметной области хранить в БД, для доступа использовать API JDBC с использованием пула соединений,
<br> стандартного или разработанного самостоятельно. В качестве СУБД рекомендуется MySQL или Derby.
<br>- 5. Приложение должно поддерживать работу с кириллицей (быть многоязычной), в том числе и при хранении информации в БД.
<br>+ 6. Архитектура приложения должна соответствовать шаблону Model-View-Controller.
<br>- 7. При реализации алгоритмов бизнес-логики использовать
<br> шаблоны GoF: Factory Method, Command, Builder, Strategy, State, Observer etc.
<br>+ 8. Используя сервлеты и JSP, реализовать функциональности, предложенные в постановке конкретной задачи.
<br>- 9. В страницах JSP применять библиотеку JSTL и разработать собственные теги.
<br>- 10. При разработке бизнес логики использовать сессии и фильтры.
<br>- 11. Выполнить журналирование событий, то есть информацию о возникающих исключениях и событиях в системе обрабатывать с помощью Log4j.
<br>- 12.	Код должен содержать комментарии.

