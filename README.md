# Shop
Shop project
Already done:
1. Entity classes: Base, User, Product, Order were created.
2. According to the Model-View-Controller pattern were created Service and Dao interfaces and Classes, implemented them for each Entity.
3. For each Entity Controllers were created.
4. Java server pages 'index', 'users', 'products' and 'orders', 'product data' and 'user data' as UI were created.
5. A database 'Shop' with appropriate tables and relations was created.
6. The next functionality was implemented:
- User can manage the Products list;
- Administrator can add users to black list;
- User can manage Users list;
- User can edit User data in the Users table; //not correctly realized
- User can edit Product data in the Products table; //not correctly realized

Need to be done:
1. Realize Orders page so that it shows each user's products list added to basket (ordered).
2. Realize correctly products and users editing.
3. Make possible to set a user out of the black list.
4. Realise addToOrder method.
5. Realize confirm/pay methods.
6. Realize Autorization.
7. Implement own TAG which will include regular expressions for entered data.
8. Write comments.
9. Manage Logger.
10. Make Tests.
11. Change id type from int to Integer in the Base Entity.
