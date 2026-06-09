!! README IS A WIP !!

# CLARITY API

This is a RESTful API for my personal note taking web application. This project will allow users to create and customize notes to their liking, meeting both the aesthetic and functional needs a user might have. 

I built this project to address the concerns with data privacy I face when using Google Keep, as well as my dissatisfaction with their ui customization options. I also buit this project to gain hands-on experience with the Spring ecosystem and deepen my understanding of this framework, its available libraries, and standard backend development practices

This project demonstrates my current understanding of
- REST API design
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
- Retrival of labels associated with the user
- Deletion of labels

## UI template
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
The application follows a layered architecture with stateless authentication through JWT, with separation of responsibilities across different sections (separation of concerna).

DTO
↕
Controller Layer
↕
Service Layer
↕
Repository Layer
↕
MySQL Database

A typical request flow is as follows:
1) User logs in
2) JWT token is given
3) Client sends request to a protected endpoint using JWT token for authentication
4) Token is authenticated by JWT filter
5) Request goes to appropriate controller
6) Payload is deserialize
7) Controller sends DTO to service layer
8) Service layer calls repository layer and runs the bussiness logic
9) If a response is expected, DTO objeect is created and sent back to the client

## Key Design Decisions

### DTO Pattern
I use DTO's in order to protect sensitive user information as well as to not overwhelm the front-end with unecessary infromation everytime a requested is made. Not all information needs to be sent in every request, and DTOs let me control that information is transfered to and from the backend.

### Layered Architecture
I decided to Layer my application  becuase it makes maintaance much easier as well as improving code readability/debugability. When an update needs to be made, only a portion (mainly the service layer where the business logic lives) of the application needs to be updated, making the system much more resilient and less likely to completely break during a feature update.

### Authentication
JWT is a cheap but effective way of authenticating users. due to its statelessness it helps prevent unecessary db querying as well as simplifying the database schema itself since there is no need to have an authentication entitity (for the most part, refresh tokens will be implemented). The fact that JWT holds most of the needed user information also simplifies requests, since the service layer can pull the needed user information from the jwt token itself, allowing for more request integrity.

### Environmental variables
environmental variables offer a way for programers to hide sensitive data in their code, making it a good design choice in my system that has sensitive information that is required for the code to run (such as db passwords, jwt secret, etc...). ofc this doesnt come with its downside of these variables needing to be configured in each individual machine, but the security benefits was aoutweigh this con

