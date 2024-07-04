# FlightManagementSystem

## Introduction

This project simulates a travel agency where users can create and manage flights. Users can also obtain all information about each flight, as well as details about pilots, airports, airlines, etc.

## Objectives

The objective of this project is to develop an application to manage a travel agency. This application allows users to manage flights, pilots, airlines, planes, and airports. For each of these classes, it is possible to view all their flights, whether complete, cancelled, delayed, or on time.

## Features

### LogIn and SignUp

In the login panel, there is a TextField and a PasswordField. When the login button is pressed, it checks all accounts to verify if the entered details are correct. During this process, the user's email is also checked to determine their status within the application (administrator, pilot, or customer).

In the SignUp panel, the logic is the same, but it verifies if there is no existing account with the entered email. Additionally, a method is used to verify if the email is valid. If the created account's email indicates that it is for a pilot, a new pilot is created with the name extracted from the email and added to the XML.

### Customer Panel

In the customer panel, there are two ComboBoxes with all available airports and two TextFields for entering the flight search dates. When the "Search Flights" button is pressed, the entered data is used to search for flights with the desired details and a table is shown with the requested flights.

### Pilot Panel

In the pilot panel, the pilot's name, flight hours, and a table of the pilot's upcoming flights are initially displayed. It is possible to switch between tables by clicking the corresponding buttons.

### Administrator Panel

In the administrator panel, four central buttons allow changing the ComboBox items depending on the pressed button. After pressing the "Search Flights" button, a table is shown with the flights of the previously selected object.
By clicking the "View All Flights" button, a table with all flights is shown. There are five buttons on the right side of the page. The first three buttons are used to change the status of a selected flight in the table, while the other two are for either removing a selected flight or creating a new flight.

## How to Run

1. Ensure you have Java installed on your machine.
2. Clone the repository:
    ```bash
    git clone https://github.com/dmcs19/Flight-Management-System.git
    ```
3. Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).
4. Build and run the project.


