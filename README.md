# Pizza-Delivery

*Software Engineering course project at University of Pavia  
Developed in collaboration with: @fecchioandrea, @matteogobbo01, @FrancescoMusitano and @FeBD8*

## General Overview
Implementation of a software for the management of orders and deliveries of a pizzeria.
The pizzeria only does delivery, and customers must be able to place orders independently through a graphical interface and a textual one.

#### Login Page

https://user-images.githubusercontent.com/48378307/167324137-0ba2e033-13d7-4ca3-b77a-0929e41af35d.mp4


## Pizzeria Management
Pizzeria owner can edit his menu by adding or deleting pizzas from it, also changing the possible toppings to be added to the various pizzas.He can also view the orders received.

<img width="593" alt="Pizza creation" src="https://user-images.githubusercontent.com/48378307/167324169-ccd785db-ad10-4fee-ad34-659ab1fb0a12.PNG">

## Customer Side

#### Home Page

<img width="590" alt="Home" src="https://user-images.githubusercontent.com/48378307/167324462-5c4ce119-3941-463b-ba84-3b14acee9671.PNG">

This software takes into account the possibility for the users of:
* Ordering pizzas listed in a predefined menu;

<img width="594" alt="Ordine" src="https://user-images.githubusercontent.com/48378307/167324498-6c9b3df3-9a34-44ef-9ac6-306304a2c101.PNG">

* Consult prices for individual products and total order.

<img width="592" alt="cart" src="https://user-images.githubusercontent.com/48378307/167324507-8ab4061f-aef6-4c36-b233-dcc5a5e3bd78.PNG">

Throughout the process, compatibility between orders, preparation time and delivery time is maintained at all times.

## Operating Instructions
Instructions for compiling and running the code:
- Install MySQL on your device;
- Extract the three external libraries contained in the project file "ExternalLibraries.zipper";
- Add these libraries to the Project Structure of the project.
- In Project Structure, verify that you are working at language level 8, with JDK 1.8;
- Import org.junit.Test (fix error in test.PizzeriaTest by adding the external library JUnit4);
- Start the program (both pizzeria side and customer side) through interfaces.TextualInterface or interfaces.GraphicInterface.
