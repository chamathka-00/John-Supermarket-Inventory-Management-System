# John’s Super Market – Inventory Management System

CM1601 – Programming Fundamentals

BSc (Hons) Artificial Intelligence & Data Science

Robert Gordon University (RGU)

Coursework 2 – Y1S2

## 📌 Project Overview

John’s Super Market is a JavaFX-based GUI application developed for the second-semester Programming Fundamentals module. It simulates a real-world supermarket scenario where staff must manage inventory items and dealer information through an interactive graphical interface.

The system uses Object-Oriented Programming principles, custom validation, file handling, sorting, modular design, and a well-structured controller–model architecture. It continues the logic introduced in Coursework 1 but extends it into a full GUI application with enhanced functionality and usability.

## 🎯 Learning Objectives

This project demonstrates the ability to:

* Apply OOP principles (encapsulation, abstraction, modularity, SOLID).

* Develop GUI applications using JavaFX and FXML.

* Implement controllers, models, and loaders in a scalable architecture.

* Perform file handling for items and dealer data.

* Apply input validation and exception handling.

* Use custom sorting algorithms (bubble sort for category grouping).

* Build maintainable, testable Java code with JUnit tests.

* Design responsive UI layouts and user-friendly dialog interactions.

## 🖥️ System Features
🔹 Automatic Low-Stock Display on Startup

Loads all items from items.txt and displays any item whose quantity is below its threshold.

🔹 **Add Item**

* Opens an Add Item dialog with full form validation.

* Inputs include: code, name, brand, price, quantity, category, purchased date, threshold, and image.

* Uses InputValidator for strict validation.

* Saves to file after successful addition.

🔹 **Update Item**

* Finds an item by code and opens a pre-filled update dialog.

* Live validation for all editable fields.

* Saves updated data to file.

🔹 **Delete Item**

* Allows deletion by selecting from the table or entering an item code manually.

* Includes confirmation dialogs.

🔹 **View All Items by Category**

* Displays all items grouped by category using custom separator rows.

* Implements bubble sort for category and item ordering.

* Shows total item count and total value.

* Supports add/update/delete actions within the view.

🔹 **Save Item Details**

Items can be saved to items.txt at any time through the dialog controllers.

🔹 **Random Dealer Selection**

* Reads dealer information from dealers.txt.

* Randomly selects four dealers without duplicates.

* Displays full dealer info in sorted order by location.

🔹 **List Items of Selected Dealer**

* Shows items supplied by a chosen dealer using the dealer dialog.

🔹 **Exit**

Clean termination with confirmation prompt.

## ✔️ Technologies & Concepts Used

* Java (OOP, classes, objects, interfaces, modularity)

* JavaFX & FXML (GUI development, TableView, dialogs, event handling)

* Custom sorting algorithms (Bubble Sort)

* File handling (reading/writing structured text files)

* Exception handling & validation

* JUnit testing (ItemTest, DealerTest, FileManagerTest, etc.)

* Responsive GUI layout design

## 📜 Academic Integrity

This repository contains original work submitted to RGU for assessment.
Reusing or submitting this work elsewhere may violate academic integrity regulations.

## 📘 License

This project is licensed under the Apache License 2.0. You may view, use, and adapt the code for learning and educational purposes, provided that proper attribution is given as required by the license.

Submitting this work, or any modified version of it, as part of an academic assessment is strictly prohibited.
