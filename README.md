!! README IS A WIP !!

# CLARITY API

This is a RESTful API for my personal note-taking web application. This project will allow users to create and customize notes to their liking, meeting both the aesthetic and functional needs a user might have. 

I am building this project to address the concerns with data privacy I face when using Google Keep, as well as my dissatisfaction with their ui customization options. Im also building this project to gain hands-on experience with the Spring ecosystem and deepen my understanding of this framework, its available libraries, and standard backend development practices.

This project demonstrates my current understanding of
- RESTful API design
- DTO pattern
- Layered Architecture
- Stateless Authentication through JWT tokens
- Separation of Concerns
- Spring's Dependency Injection
- Externalized configuration management
- Database design
- Containerization


# FEATURES
This API has basic CRUD operations for all the key entities, except for the User entity, which has a login endpoint that returns a valid JWT token to gain access to protected endpoints.

## Authentication
- User authentication using JWT tokens
- Secure access to protected endpoints
  
## User
This entity manages all user account information
- User registration
- User authentication
- Retrieval of basic user info (for frontend use)
- Updating of user information (i.e. email, username, password)
- Deletion of user account

## Notes
This entity manages all note related information with the help of supporting entities
- Creation of notes
- Updating of the note as a whole
- Updating of individual note aspects (i.e. title, content, label, pinned status..etc)
- Retrieval of all notes or individual notes associated with the user
- Customization of an individual note's colors
- Deletion of notes

## Note Color
This is a supporting entity for Notes, it stores all of the users saved colors
- Creation of a color
- Updating of a color associated with the user
- Retrival of all colors associated with the user
- Deletion of a color

## Label
This entity manages all label related information
- Creation of custom labels
- Updating of labels
- Retrieval of labels associated with the user
- Deletion of labels

## UI Template
This entity manages all UI templates, whether they be default or user-made
- Creation of UI Templates
- Updating of UI Templates
- Retrieval of UI Templates
- Deletion of UI Templates

As far as api documentation goes, I used Swagger UI for interactive documentation as well as OpenAPI specification generation. To access the documentation go to http://localhost:8080/swagger-ui/index.html#/ after starting the project

# TECH STACK
## Backend
- Java
- Spring Boot
- Spring Security
- JSON Web Tokens (JWT)
- Lombok
## Data
- MySQL
- Spring Data JPA
## Testing
- JUnit 5
- Rest Assured
## DevOps
- Docker
- Docker Compose
- Environmental Variables
## Documentation
- Swagger IU
- SpringDoc OpenAPI

# Architecture
The application follows a layered architecture with stateless authentication through JWT, with separation of responsibilities across different sections (separation of concerns).

DTO
⇄
Controller Layer
⇄
Service Layer
⇄
Repository Layer
⇄
MySQL Database

A typical request flow is as follows:
1) User logs in
2) JWT token is returned
3) Client sends request to a protected endpoint using JWT token for authentication
4) Token is authenticated by JWT filter
5) Request goes to appropriate controller
6) Payload is deserialize to required DTO
7) Controller sends DTO to service layer
8) Service layer calls repository layer and runs the business logic
9) If a response is expected, DTO object is created and sent back to the client

## Key Design Decisions

### DTO Pattern
I use DTO's in order to protect sensitive user information as well as to not overwhelm the front-end with unecessary information everytime a requested is made. Not all information needs to be sent in every request, and DTOs let me control what information is transfered to and from the backend.

### Input Validation done internally by each DTO
I decided to not rely on the built-in validation annotations that come with lombok 1) because i wanted full control of how each object gets validated to deppen my understanding of input validation, but 2) becuase this is a small program where i dont expect the basic structure to change much over time, i opted to put in the extra validation work in order to not have surprise bugs pop up in the case that a library update takes place, the only reason why the validation wil get updated/changed is becuase of feature updates or fixing of already known bugs, reducing the amount of maintanance needed in the long run.

### Layered Architecture
I decided to Layer my application  becuase it makes maintenance much easier as well as improving code readability/debugability. When an update needs to be made, only a portion (mainly the service layer where the business logic lives) of the application needs to be updated, making the system much more resilient and less likely to completely break during a feature update.

### Authentication
JWT is a cheap but effective way of authenticating users. due to its statelessness it helps prevent unecessary db querying as well as simplifying the database schema itself since there is no need to have an authentication entitity, unlike session-bases authentication. The fact that JWT holds most of the needed user information also simplifies requests, since the service layer can pull the needed user information from the jwt token itself, allowing for more request integrity. 

To mitigate the risks that come with using these tokens, i plan on implementing refresh tokens, lowering the validity window of the token, and maybe implementing a token blacklist if i feel like the extra security is needed (if the app ever gets used by someone other than me). The current validity windown of the token is 30 mins, to ease testing with postman, but it will be reduced significantly when the refresh token system is up and running.

### Environmental variables
environmental variables offer a way for programers to hide sensitive data in their code, making it a good design choice in my system that has sensitive information that is required for the code to run (such as db passwords, jwt secret, etc...). ofc this doesnt come with its downside of these variables needing to be configured in each individual machine, but the security benefits was aoutweigh this con

### Global Exception Handler
This is all about maintanability, If a chnage needs to be made to an exception, say chnage the error code from Conflict to Not Found, i only need to change it in one place rather than scowering the whole program for all places where that exceltion takes place, reducing the chances of faulty error responses as well

# Authentication & Security
### Authentication Flow
1) User created an account through the open registration endpoint (/user/createUser)
2) User provides their email and password in through login endpoint (/user/login)
- this endpoint takes care of validating the user information, generating a valid jwt token (using the jsonwebtoken(JJWT) library) after successful authentication, and sending it back to the frontend in the response payload
3) Client makes a request to a protected endpoint with token as part of the Authorization request header
4) JWT filter verifies the validity of the token and its claims, authenticating the user if everything is valid, if not a Unauthorized error is sent back
5) Request moves through to the next step in the request flow

### Password Security
Passwords are hashed before storing using Bcrypt(10 strength). It is also important to note that passwords are salted automatically by bcrypt, so anytime a user logs in, the incoming password is also hashed using the same strength and salt, and the resulting hash are compared to the existing one. The system does not store actual user passwords, not only is it bad practice and not secure, but also goes agaisnt the whole reason why I started this project, data privacy.

### Protected Routes
All but the login, registration, and swagger ui endpoints are protected by spring security. 

### Environment & Secrets
The JWT secret is stored as an environmental variable, this allows me to still use sensitive data in the code while not exposing.

the current env.example provides all the crutial variables needed for the application to work; these being
- the Database username and password
- A sample JWT secret, this secret is for local testing and development only. A secure secret should always be generated for production environment.


# Database Design
I used a local MySql database for all testing and conteinarized one as well, dummy data has been provided for the following users:

alice,
alice@example.com,
UserPassword@123

Bob,
bob@example.com,
SamplePassword@123

I use Spring Data JPA to interact with the database, which includes Hibernate. The database is manually managed through the schema.sql file in resources. I did this so that i could deepen my understanding of ddl and db management.

Here is a an overview of the database

![Clarity Physical ERD PNG](documents/Database Physical ERD.png)

## Main Entities & Important Fields
I tried to have as much of the data validation to be dont by the server to ensure data integrity. This also reduced the chances of faulty data bbeing stored due to poor data validation on the api, since any faulty information sent to the db would be denied.

### User
- Email
  - although all emails are unique by default, i decided to make the field unique to prevent duplicate users from being added, since emails are unique, there is no reason why two users should have the same email.
  - As for the size of the field, I did some research to figure out the standard field sizze for an email and landed on that number
- Password
  - Since im using Bcrypt to hash my passwords, I made the field as big as the usual Bcrypt output to not use up unnecesary storage space, as well as being able to catch errors, since if a password added is longer tha 60 chars, ill know something is up with my hashing process.

Since this is a core entity, it has many relationships:
- Many:Optional 1 to UI Template
  - A user can own/create many templates, but a template can belong to 1 or no users (allows room for system provided templates)
- Many:1 to Note Color
  - A user can own/create many colors, but a color can belong to only one user
- Many:1 to Label
  - A user can own/create many labes, but a label can only belong to one user
- Many:1 to Note
  - A user can own/create many notes, but a note can only belong to one user


### Notes
- Title
  - just like with the email field, I did some research to figure out the standard field sizze for a note title and landed on that number
- Text Content & Cosmetics
  - Since im still in the process of figuring out what the content of these fields will look like (will figure it out after frontend is done) i opted to make then text fields, sice this is the most likely data type that ill need, however these field types are subject to change in the future
- Deleted & Time Left Before Before Deletion
  - I have these two fields to be able to have a 30 day soft delete before fully deleting a note, allowing it to be restored at any point before the 30 days are over. I plan on adding a trigger to the db to update the Time Left Before Before Deletion based on changes to the Deleted field.

Since this is also a core entity, it also has many relationships:
- 1:Many to User
  - A Note can belong to only 1 user, but a User can own many notes
- Optional 1:Many to Label
  - A Note can use 1 or no label, but a label can be used by many notes
- Optional 1:Many to Note Color
  -A Note can use 1 or no color, but a color can be used by many notes

## Note Color
- Color Hex
  - 9 chars allow support for color hex with alpha transparency

Relationships:
- 1:Many to User
  - A color can belong to 1 user, but a user can own/create many colors
- Many:Optional 1 to Note
  - A color can be used by many notes, but Note can use 1 or no color

## Label
no notable fields/field types

Relationships:
- 1:Many to User
  -A Label can belong to one user, but a user can own many labels
- Many:Optional 1 to Note
  - a label can be used by many notes, but a note can use one or no label

## UI Template
- Template Name
  - just like Text Content & Cosmetics, im the the middle of figuring out that the content of this field will look like in reality, so i choose the most optimal field.
 
Relationships:
- Optional 1:Many
  - a template can belong to 1 or no users, but a user can have many templates


