# Scheduling-Desktop-Application
JavaFX application that connects with an external database to handle scheduling tasks for a fictional company

## Description                     
This application connects with a MySQL database to handle customer and appointment data for a fictional company. End-users have the ability
to manipulate data with SQL commands that are within application methods, including but not limited to: insertions, deletions, modifications, and retrievals. 
A user interface is provided for all pertinent functions to provide easy usability.

Viewing reports and options are also available and can be filtererd by the following parameters: timeframes (weeks, months, years), specific customers or appointments,
or attributes (country, division, contacts, etc).

The application handles time conversions between the database (UTC) and local time of the user. Appointment times are validated to ensure requests
are not made out of business hours or during another appointment. Reminders are generated for end-users when upcoming appointments are soon.

## Features
- JavaFX user interface
- MySQL database manipulation
- Data reports pertaining to specific attributes
- Appointment reminders
- Built-in time conversions between database (UTC) and local time
- Time parameters to prevent scheduling during ineligible hours
- Built-in error prevention and alerts
- Bilingual login screen
- Basic logging functions

## Requirements for Executing Program
- Database matching parameters in the "JDBC" class located in the utils folder
- my-sql-connector to connect application with database
- JavaFX
- Login username: 'test', password: 'test'
- Start of program: src/Main/main.java 

## Tools Used
- IntelliJ Community
- JavaFX 11.0.2
- Java JDK 11
- MySQL
- MySQL driver connector: 4, Version 8.0.27
- Scenebuilder
