# BankManagementSystem
1) to use postreges db connection, from InteliJ go to File -> Project Structure -> Dependencies -> Add JAR file and add the path to the driver
2) do not name a table "user"
3) do not name the attributes with capital letters (Idk why, but it works just with small letters)
4) set cascade delete for the foreign keys to make it easier for ex to delete an user and all it's accounts and the history of transactions at the same time
5) to use scene builder File->Settings->Libraries->add the path to scene builder
6) install maven to solve dependencies
