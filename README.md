
<p align="center">
  <img src="/src/main/resources/static/images/gene-app.png" width="400px" height="300px"/>
</p>

# Genehelix
```$xslt
Genehelix is a proposed healthcare application that helps in saving the cost and time of booking medical appointment. It gives an easy way to manage healthcare data.
Patients also have access to their healthcare data. It proposes a better way to allow patients to always have access to healthcare services at the comfort of their home. 
The review system is patient-centric which will also helps in product and service improvement.
It has various capabilities to enable customer-friendly. Some of the capabilities are 

1. Single click for registration.
2. Any customers can access healthcare services guaranttee at the comfort of their home
3. Page flow are easy to comprehend,
4. Nice UI interface built with ThymeLeaf with some JavaScript code.
5. Pages enhanced with Search-box for easy navigation
6. It is also enhanced with Spring-Security for registered service providers to manage some sensitive APIs.
7. A multipurpose login button for all users.

```

## Home page
<p align="center">
<img src="/src/main/resources/static/images/home-page.png"/>
</p>

```$xslt
There is search-box for easy location of managers with their data including reviews from other patients
```

## Typical Registration page
<p align="center">
  <img src="/src/main/resources/static/images/typical-reg-page.png"/>
</p>

```$xslt
This shows page for typical registration on the system.
```

## Admin login page

```$xslt
This display admin view page.
```
<p align="center">
 <img src="/src/main/resources/static/images/admin-page.png"/>
</p>

```$xslt
Admin can perform CRUD operations here and also view every customer on the system.
```

## Patient page

```$xslt
This is customer page where operations like 
1. Upload resume
2. View manager 
3. Submit review 
4. Change password 
5. on so on
can be performed.
```
<p align="center">
 <img src="/src/main/resources/static/images/c-page.png"/>
</p>


## Employee page
 ```$xslt
The below is an employee page where different operations can be performed, amongst others are
1. View review list from patient
2. Upload patient medical statement
3. register from medical service. 
```
<p align="center">
 <img src="/src/main/resources/static/images/e-page.png"/>
</p>



## Typical user detail registration page
```$xslt
This shows typical user detail registation and update while login.
```
<p align="center">
 <img src="/src/main/resources/static/images/user-d-page.png"/>
</p>



## Spring boot Setup
```$xslt
A starter project can be downloaded with all dependencies from
```
 <a href="https://start.spring.io/">spring.io</a>


## Environment Setup
```$xslt
The environment for this application is setup in the file below 
Note the setup lines:
  ' spring.servlet.multipart.max-file-size=1MB
    spring.jpa.hibernate.ddl-auto=update '
     _______________________________________
   The UPDATE will enable you create table in the database at a fly.
   While the DD_AUTO will limit file size to 1M, you can set it to below or higher, 
   MySql Default is 4MB, so you will have to set it up in MYSQL Database if you want to exceed 4MB
       
```
<span style="background-color: blue; padding: 5px ">/application.properties</a>

<p align="center">
 <img src="/src/main/resources/static/images/application-p.png"/>
</p>

<h3>Steps  to setup project</h3>

```$xslt
1. Open the project starter pack in Intellij IDEA; my favourite you can use others
2. Make sure to connect to internet, then open the command line interface in your IDEA.
3. input $ git clone https://github.com/Nazel7/servbyte 
4. It will download to your work directory.
5. Create new User in Mysql Databse Workbench as below
```
<p align="center">
  <img src="/src/main/resources/static/images/mysql-user.png"/>
</p>



## How to Run
```$xslt
Application runs in main method
```

<p align="center">
  <img src="/src/main/resources/static/images/app-run.png"/>
</p>


```$xslt
 Point to note, comment out "ddl-auto= update"
 It is not advisable to use in production environment,
 so it is better to be cconcious about it.

```

## Some Important APIs

<h3>Secured EndPoints</h3>


###
1. GET http://localhost:8081/dashboard
###
2. GET http://localhost:8081/customer/photoUpload
###
3. GET http://localhost:8081/customer/c-photo-update
###
4. GET http://localhost:8081/customers/search
###
5. GET http://localhost:8081/employee/photoUpload
###
6. GET http://localhost:8081/employee/e-photo-update
###
7. GET http://localhost:8081/employee/e-photo-update/employee/resumeUpload

<h3>Public Access EndPoints</h3>

###
1. The home-page: GET http://localhost:8081
###
2. The Sign up: GET http://localhost:8081/home-page/customer-reg
###
3. The home-search: GET http://localhost:8081/home-search
###
4. The login: GET http://localhost:8081/login-page

## Thank you for reading.





