# Test

This project is a sample application that provides functionality to upload CSV or XML files, read and validate information from them, and store the information in a PostgreSQL database.

## Prerequisites

Before running this project, ensure you have the following installed:

- Gradle: [Installation Guide](https://gradle.org/install/)
- PostgreSQL: [Installation Guide](https://www.postgresql.org/download/)

## Setting up the Database

1. **Create a PostgreSQL Database**: Create a PostgreSQL database on your local machine.

2. **Insert Database Connection Details**: Open the `application.properties` file in the project and replace the database URL with your PostgreSQL database URL. Update the `username` and `password` fields as per your PostgreSQL setup.

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
   spring.datasource.username=your_username
   spring.datasource.password=your_password

  And here: .url("jdbc:postgresql://localhost:5432/test")

  ## Running the application
    run command in terminal: gradle build 

## Important Note
  Add application.properties to .gitignore: Ensure you add the application.properties file to your .gitignore to avoid exposing sensitive information like database credentials.
