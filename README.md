# Assessment

Front End - Angular
Backend - Spring boot
DB - h2

Front End :
1. Download code from usermanagement-frontend and open the folder usermanagement-frontend/angular-usermanagement from vs code
2. open terminal and type ng serve to start the applicaton
3. Application can be accessed from "http://localhost:4200"

Backend :
1. Import the project in the folder "spring-boot-usermanagement" from eclipse
2. Open com.assessment.usermanagement.SpringBootUsermanagementApplication -> Right click and select Run as Java application
3. Back end Application with Spring Rest and Spring Data rest will be up to be accessed by the frontend

Application :

1. Open url in browser "http://localhost:4200" to view the application
2. Application has file upload and users tab from left menu
3. Click on file upload and upload the file to insert the records into DB
4. if the record with same employee id exists in database, it will replace otherwise insert
5. while replacing the record if there is any other record with same login it will swap the login with that record to maintain the login unique
6. uploaded records can be viewed from users menu with pagination
7. users menu also provide functionality to add new employee, edit employee and delete employee
8. users details can be filtered based on minimum and maximum salary with the help of search button.


Crud and search operations on employee records from Rest client

Crud and search operations on employee records from Rest client
1. Use postman to test the crud and search operations
2. To upload csv file select "POST" and URL "http://localhost:8080/cud/users/upload" , select form-data-> enter key as file and choose type as file->
upload csv file in content and click on send
3. To retrieve all uploaded details, select "GET" -> URL "http://localhost:8080/cud/getUsers" and click send to retrieve all employee details
4. To retrieve employee records based on criteria , select "GET" -> URL "http://localhost:8080/cud/users?minSalary=0&maxSalary=4000&offset=0&limit=30&sort=-name"
and click send to retrieve the employee records based on criteria.
5. To create employee record, select "POST" ->URL "http://localhost:8080/cud/users/create" -> select headers ->add key as "Content-type" and value as "application/json"
->select body -> choose raw -> and paste json  {"id": "e0011","login": "Emplogin27","name": "Arminius","salary": 34.23} in the body -> click send. this new record will be saved in db.
6. To retrieve the employee record by id select "GET" -> URL "http://localhost:8080/cud/users/e0011" -> click send to view the result in response.
7. To update the employee record by id select "PATCH" -> URL "http://localhost:8080/cud/users/update/e0011" -> select headers ->add key as "Content-type" and value as "application/json"
->select body -> choose raw -> and paste json  {"id": "e0011","login": "Emplogin27","name": "Arminius Dev","salary": 3400.23} in the body -> click send. Employee record e0011 will be updated
in db.
8. To delete record select "DELETE" -> URL "http://localhost:8080/cud/users/delete/e0001" and click send. The employee record "e0011" will be deleted from DB.

