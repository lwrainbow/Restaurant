# OnlineRestaurant
<h2>Introduction</h2>
<p>Our application is going to be an online food restaurant. With the help of latest technology and Java FX, we will be helping people to save their precious time. Instead of standing in long lines waiting for food for hours, customers will be able to order food online at their ease and can pick it up within few minutes.</p>

<h2>Sketches</h2>
<p>First Page:</p>

<h2>UML Diagram</h2>
<p></p>

<h2>OOP Choices</h2>
<p>To build our application, we will use a Customer class to gather the customer's data. There will be an abstract class called Food which will further be acquired by three sub-classes: Soup, Appetizer, and MainCourse. There will be an Order class which will have an ArrayList of Food. In this application, there will be one aggregated relationships and two composition relationship.</p>
<ul>
    <li>Order class has an aggregated relationship with Food class</li>
    <li>Customer class has a composition relationship with Bill class</li>
    <li>Order class has a composition relationship with Bill class</li>
</ul>
<p>In one order, it can have different food. But in a bill, only one customer’s information can be showed on this bill and the bill only contains one order list.</p>

<h2>Structure of Data</h2>
<p>Our app will be reading and writing the structure of data in a txt format. There will be two separate files for input and output of data structure. The input will be our food list that will be saving in the file. The output will be our existing customer’s information i.e. their name and phone number which will display on the receipt screen.</p>
