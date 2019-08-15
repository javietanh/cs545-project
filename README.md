## Project Teams:
<ul>
    <li>Phuong Tran (<a href="mailto:phuongtm1911@gmail.com">phuongtm1911@gmail.com</a>)</li>
    <li>Viet Anh Nguyen (<a href="mailto:ja.vietanh@gmail.com">ja.vietanh@gmail.com</a>)</li>
    <li>Jorge (<a href="mailto:japinto930614@gmail.com">japinto930614@gmail.com</a>)</li>    
</ul>

 	 
## Online Marketing Project

Github url: https://github.com/javietanh/cs545-project.git<br/> 
Diagram: https://www.draw.io/#G1r-3mbf9fY7bXn7rIoG83pTlCA4mc43R2

## Technologies

<ul>
    <li>Spring boot MVC with thymeleaf + Rest API.</li>
    <li>Hibernate, lombok + JPA as ORM.</li>
    <li>H2 in memory database.</li>
    <li>Bootstrap 4.3.1 as layout and template.</li>
    <li>jQuery + ajax for client manipulate with rest api.</li>
    <li>Springboot Security as authentication & authorization.</li>
    <li>flying-saucer-pdf library to print invoice and receipt as pdf file.</li>
    <li>Springboot multipart support for upload file.</li>
    <li>Mailgun Api to send email notify.</li>
</ul>

## Project analysis and development
Task assigned: In general, the project has 3 main parts which is: Seller, Buyer and Admin. After one day discuss, finalized the features and organized data storage we separated the project into 3 main modules which is take care of each team members.
<br/>
Github was used to organized and sharing source code, every features finish will be pushed and merged to the master branch.  

## Task Assignment
<ul>
    <li>Seller: Viet Anh Nguyen</li>
    <li>Buyer: Phuong Tran </li>  
    <li>Admin: Jorge </li>
</ul>  

## Project Requirements
This is an engineering proof of concept. The goal is to exercise the Spring MVC/Boots technology according to “company” guidelines to validate its use in future projects. 

<p>
    <strong>A group consists of 3~4 members.</strong>
</p> 
 
_The main hours spent on the project for each person is expected to be ~50(?) hours. Every Team member is to “own” a “front to back” scenario - View, Controller, Domain Object, Validation, Service and Repository._

##### Here is the list of the techniques you must include in your project as a Group.

Technique 
<ul>
    <li>XML config/Java Config</li>
    <li>PRG</li>
    <li>JSP, Thymeleaf</li>
    <li>Bean Validation or Spring Validator</li>
    <li>Custom Validation Annotation</li>
    <li>Custom Formatter</li>
    <li>Uploading files</li>
    <li>Exception Handling (Individual, Globally)</li>
    <li>REST/Ajax – Error Handling</li>
    <li>Spring Security (Database, Logout, Remember Me, csrf, etc)</li>
    <li>Security authorization – interceptor, AOP</li>
    <li>Persistence – Hibernate + Spring Data</li>
    <li>ONLY allow In memory database (H2, HSQL, etc)</li>
    <li>CSS Library</li>
    <li>GitHub</li>    
</ul>

It means your project must include those techniques which are checked 
What kind of project should I develop? Here is the variety of options you can choose: 

<ul>
    <li>Spring MVC monolith</li>
    <li>Spring MVC monolith Thymeleaf</li>
    <li>Spring REST: Client (JSP) + Server</li>
    <li>Spring REST: Client (Thymeleaf) + Server</li>
    <li>Spring boot monolith JSP</li>
    <li>Spring Boot: Client(JSP) + Server</li>
    <li>Spring Boot monolith Thymeleaf</li>
    <li>Spring Boot: Client(Thymeleaf) + Server</li>
</ul> 

If you need to use a new technique which isn’t covered in this course, you must get permission first. Otherwise, you’re gonna lose points in your group project grade. 
 
 
##Scrum/Agile Development Process 
 
Daily Scrum meeting – We’ll hold a daily meeting every day to check everyone’s status if you need any help. 
 
#### Project Topic – Some online Market
 
1.	Users: Admin, Seller and Buyer 
2.	Features of Admin<br/>
        <ul>
            <li>If seller register in this web site, need to get approval from Admin in order to post products in the web site</li>
            <li>Add Ads on pages</li>
            <li>Approve Review made by Buyer (no matter approve or reject, notify buyers by email)</li>
        </ul> 
 
3.	Features of Seller
        <ul> 
            <li>Register as Seller</li> 
            <li>Product (CRUD). When a product is added, the system should automatically notify all followers by website message</li> 
            <li>Seller cannot buy products from the website</li> 
            <li>Maintain orders  </li>
            <li>Cancel Order (Notify Buyer by website message), the status of order on buyer’s part should also changed</li> 
            <li>Change Order status (Shipped-On the way-Delivered)</li>
        </ul> 
 
4.	Features of Buyer
        <ul> 
            <li>Register as Buyer</li> 
            <li>Follow and Unfollow Seller</li> 
            <li>Can not sell items on this website</li> 
            <li>Can place an order </li>
            <li>Maintain Shopping Cart (CRUD)</li> 
            <li>Maintain Shipping and Billing Address</li> 
            <li>Maintain Payment </li>
            <li>Place order </li>
            <li>Every successful purchase (not returned), gain points from the website. You can use points to buy products (something like coupons).</li> 
            <li>Maintain Orders </li>
            <li>Check Order History </li>
            <li>Can cancel order before shipping, after shipping cannot</li> 
            <li>Download/Print receipt as PDF or Excel </li>
            <li>Write Product Review. Review must be approved by Admin before live</li> 
        </ul>

