# Table of Contents

- [Clarity API](#clarity-api)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Key Design Decisions](#key-design-decisions)
- [Authentication & Security](#authentication--security)
- [Database Design](#database-design)
- [API Documentation](#api-documentation)
- [Recommended Testing Order](recommended-testing-order)
- [Testing](#testing-approach)
- [Getting Started](#getting-started)
- [Stopping the Application](#stopping-the-application)
- [Challenges and Lessons Learned](#challenges-and-lessons-learned)
----

# CLARITY API

This is a RESTful API for my personal note-taking web application. The goal of this project is to allow users to create and customize notes to their liking, meeting both the aesthetic and functional needs they may have.

I am building this project to address the data privacy concerns I have when using Google Keep, as well as my dissatisfaction with its UI customization options. I'm also building this project to gain hands-on experience with the Spring ecosystem and deepen my understanding of the framework, its libraries, and standard backend development practices.

This project demonstrates my current understanding of:

* RESTful API design
* DTO pattern
* Layered architecture
* Stateless authentication with JWTs
* Separation of concerns
* Spring dependency injection
* Externalized configuration management
* Database design
* Containerization


# FEATURES

This API supports basic CRUD operations for all key entities, with the exception of the User entity. Instead, users authenticate through a login endpoint that returns a valid JWT, which can then be used to access protected endpoints.

## Authentication

* User authentication using JWTs
* Secure access to protected endpoints

## User

* User registration
* User authentication
* Retrieval of basic user information (for frontend use)
* Updating user information (e.g. email, username, password)
* Deletion of user accounts

## Notes

* Creation of notes
* Updating a note as a whole
* Updating individual note attributes (e.g. title, content, label, pinned status, etc.)
* Retrieval of all notes or individual notes associated with the authenticated user
* Customization of note colors
* Deletion of notes

## Note Color

* Creation of custom colors
* Updating colors associated with the user
* Retrieval of all colors associated with the user
* Deletion of colors

## Label

* Creation of custom labels
* Updating labels
* Retrieval of labels associated with the user
* Deletion of labels

## UI Template

* Creation of UI templates
* Updating UI templates
* Retrieval of UI templates
* Deletion of UI templates

## API Documentation

For API documentation, I used Swagger UI for interactive endpoint exploration and OpenAPI specification generation.

After starting the application, the documentation can be accessed at:

`http://localhost:8080/swagger-ui/index.html#/`

# TECH STACK

## Backend

* Java
* Spring Boot
* Spring Security
* JSON Web Tokens (JWT)
* Lombok

## Data

* MySQL
* Spring Data JPA (Hibernate)

## Testing

* JUnit 5
* Rest Assured (unit testing)

## DevOps

* Docker
* Docker Compose
* Environment Variables

## Documentation

* Swagger UI
* SpringDoc OpenAPI

# Architecture

The application follows a layered architecture with stateless authentication using JWTs. Responsibilities are separated across different layers to maintain separation of concerns and keep the application modular and easier to maintain.

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

1. User logs in
2. JWT is returned upon successful authentication
3. Client sends a request to a protected endpoint using the JWT for authentication
4. The JWT filter validates the token
5. The request is routed to the appropriate controller
6. The request payload is deserialized into the required DTO
7. The controller passes the DTO to the service layer
8. The service layer executes the business logic and interacts with the repository layer
9. The repository layer performs the necessary database operations
10. The result is returned to the service layer
11. The service layer returns the response to the controller
12. The controller returns an HTTP response to the client

## Key Design Decisions

### DTO Pattern

I use DTOs to protect sensitive user information and to avoid overwhelming the frontend with unnecessary data every time a request is made. Not all information needs to be sent with every request, and DTOs give me fine-grained control over what data is transferred to and from the backend.

### Input Validation Within DTOs

I decided not to rely on the built-in validation annotations provided by Spring because I wanted full control over how each object gets validated and to deepen my understanding of input validation.

Since this is a relatively small application and I don't expect the basic structure of these objects to change frequently, I felt the extra effort was worthwhile. By handling validation myself, I reduce the chances of unexpected behavior caused by framework or library updates. Validation rules will only change when features change or known bugs are fixed, making long-term maintenance more predictable.

### Layered Architecture

I chose a layered architecture because it makes maintenance easier while also improving readability and debuggability.

When changes need to be made, they are often isolated to a specific layer, usually the service layer where the business logic lives. This reduces the impact of feature updates and makes the application more resilient to change.

### Authentication

JWTs are a simple but effective way to authenticate users. Because they are stateless, they reduce unnecessary database queries and simplify the overall system design by eliminating the need for server-side session storage.

Another advantage is that the token already contains much of the information needed to identify the user. This allows the service layer to extract user information directly from the token rather than requiring additional request data, helping maintain request integrity and keeping endpoints cleaner.

To mitigate some of the risks associated with JWTs, I plan to implement refresh tokens and reduce the access token validity window. I may also implement token blacklisting if the application ever grows beyond personal use and requires additional security measures.

The current token validity period is 30 minutes to make testing with Postman easier, but it will be reduced once a refresh token system is implemented.

### Environment Variables

Environment variables provide a way to keep sensitive information out of the codebase, making them a natural choice for storing values such as database credentials and JWT secrets.

This approach does come with the tradeoff of requiring additional configuration on each machine where the application is deployed, but I believe the security benefits outweigh that inconvenience.

### Global Exception Handler

The primary reason for implementing a global exception handler is maintainability.

If an exception response needs to change—for example, changing an HTTP status code from Conflict to Not Found—I only need to update that logic in one place rather than searching through the entire codebase. This reduces duplicated error-handling logic and lowers the chance of inconsistent or incorrect error responses.


# Authentication & Security

### Authentication Flow

1. User creates an account through the public registration endpoint (`/user/createUser`)
2. User provides their email and password through the login endpoint (`/user/login`)

   * This endpoint validates the user's credentials, generates a JWT using the JJWT library upon successful authentication, and returns it to the frontend in the response payload.
3. The client sends a request to a protected endpoint with the token included in the `Authorization` header.
4. The JWT filter verifies the validity of the token and its claims. If the token is valid, the user is authenticated; otherwise, an `Unauthorized` response is returned.
5. The request continues through the normal request flow.

### Password Security

Passwords are hashed before being stored using BCrypt with a strength factor of 10.

It is also important to note that BCrypt automatically handles salting. When a user logs in, the incoming password is checked against the stored hash using BCrypt's verification process. The application never stores actual user passwords.

Not only is storing plaintext passwords a major security risk, but it also goes against one of the primary reasons I started this project in the first place: data privacy.

### Protected Routes

All endpoints, with the exception of login, registration, and Swagger UI documentation endpoints, are protected through Spring Security.

### Environment & Secrets

The JWT secret is stored as an environment variable. This allows the application to use sensitive data without exposing it in the codebase.

The provided `.env.example` file contains the critical variables required for local development:

* Database username
* Database password
* Sample JWT secret

The sample JWT secret is intended for local testing and development only. A secure, randomly generated secret should always be used in production environments.

# Database Design

I used a local MySQL database for development and testing, and I also containerized a database instance using Docker. Dummy data has been provided for the following users:

**(Demo User) Alice**

* [alice@example.com](mailto:alice@example.com)
* UserPassword@123

**(Demo User) Bob**

* [bob@example.com](mailto:bob@example.com)
* SamplePassword@123

I use Spring Data JPA (Hibernate) to interact with the database. The schema itself is manually managed through the `schema.sql` file located in the resources directory. I chose this approach because I wanted to deepen my understanding of DDL and database management rather than relying entirely on ORM-generated schemas.

Here is an overview of the database:

![Clarity Physical ERD PNG](./documents/Database%20Physical%20ERD.png)

## Main Entities & Important Fields

I tried to push as much validation as possible down to the database level to help ensure data integrity. This reduces the chances of invalid data being stored due to bugs or insufficient validation elsewhere in the application.

### User

This entity manages all user account information.

#### Email

* Although email addresses are unique by nature, I explicitly enforced uniqueness at the database level to prevent duplicate accounts from being created.
* I also researched common email length limits before choosing the field size.

#### Password

* Since passwords are hashed using BCrypt, I sized the field according to the expected BCrypt output length.
* This avoids unnecessary storage usage while also making it easier to detect issues if an unexpectedly large value is ever stored.

Since this is a core entity, it has several relationships:

* Many-to-One (Optional) with UI Template

  * A user can own many templates, while a template can belong to one user or none at all. This allows room for system-provided templates.
* One-to-Many with Note Color

  * A user can create many colors, while each color belongs to a single user.
* One-to-Many with Label

  * A user can create many labels, while each label belongs to a single user.
* One-to-Many with Note

  * A user can create many notes, while each note belongs to a single user.

### Notes

This entity manages all note-related information with the help of supporting entities.

#### Title

* Similar to the email field, I researched common note title lengths before choosing a size limit.

#### Text Content & Cosmetics

* Since the frontend is still being developed, I do not yet know the final structure of this data.
* For now, I chose text-based fields because they provide the flexibility I expect to need. These field types may change as the project evolves.

#### Deleted & Time Left Before Deletion

* These fields support a 30-day soft delete system.
* A deleted note can be restored at any point during that period before being permanently removed.
* I plan to add a database trigger that automatically updates the remaining deletion time whenever the deleted status changes.

Relationships:

* Many-to-One with User

  * A note belongs to one user, while a user can own many notes.
* Many-to-One (Optional) with Label

  * A note can use one label or none, while a label can be associated with many notes.
* Many-to-One (Optional) with Note Color

  * A note can use one color or none, while a color can be associated with many notes.

### Note Color

This supporting entity stores user-created note colors.

#### Color Hex

* The field supports 9 characters, allowing storage of RGBA hex values with alpha transparency.

Relationships:

* Many-to-One with User

  * A color belongs to one user, while a user can create many colors.
* One-to-Many with Note

  * A color can be used by many notes, while a note can use one color or none.

### Label

This entity manages note labels.

No particularly notable field constraints exist beyond standard validation.

Relationships:

* Many-to-One with User

  * A label belongs to one user, while a user can own many labels.
* One-to-Many with Note

  * A label can be used by many notes, while a note can use one label or none.

### UI Template

This entity manages both system-provided and user-created UI templates.

#### Template Name

* Similar to the content and cosmetics fields, I am still determining what the final structure of template data will look like.
* For now, I chose the field type that provides the flexibility I expect to need.

Relationships:

* Many-to-One (Optional) with User

  * A template can belong to one user or none at all, while a user can own many templates.

## Important Constraints

```sql
ALTER TABLE note
ADD CONSTRAINT chk_values_are_different
CHECK (NOT (pinned = TRUE AND hidden = TRUE));
```

I created this constraint to ensure a note cannot be both pinned and hidden at the same time, since those states conflict with one another. Enforcing this rule at the database level helps prevent invalid application states.

```sql
ALTER TABLE notecolor
ADD CONSTRAINT unique_user_noteColor
UNIQUE (user_id, color_hex);
```

This constraint ensures users cannot save the same color multiple times, reducing redundant data and lowering long-term maintenance requirements.

```sql
ALTER TABLE uitemplate
ADD CONSTRAINT unique_user_template
UNIQUE (user_id, template_name);
```

This constraint ensures users cannot save duplicate templates.

## Indexes

I added indexes to fields that are expected to be queried frequently:

### User

* Email

### Note

* User ID (Foreign Key)
* Label ID (Foreign Key)

### Label

* User ID (Foreign Key)

### Note Color

* User ID (Foreign Key)

### UI Template

* User ID (Foreign Key)

Primary keys are also indexed automatically by MySQL.

# Testing Approach

Testing is currently in the very early stages of development and I’m treating it as its own phase of the project moving forward.

Most of the API so far was built and tested manually using Postman. This helped me check that endpoints work as expected, that data is being stored correctly, and that the general request flow is behaving the way I intended while I was still building features.

Now that most of the core functionality is in place, I’m focusing on automated testing instead of manually testing things as I go. This should help a lot with maintaining the intended behavior of the endpoints if I need to change the code later on.

For now, I plan to focus on:

- Unit tests with JUnit 5 mainly for service-layer logic and business rules
- API tests with Rest Assured to test endpoints through real HTTP requests

At the moment, I’m not focusing much on integration testing. Most of the service logic is fairly isolated, and the main interactions happen between services and repository layers. Because of that, I’m prioritizing unit tests and API tests first before moving onto anything more complex.


## API Documentation

For API documentation, I decided to use SpringDoc OpenAPI and Swagger UI to provide clean and consistent documentation.

I chose these tools because:

* They significantly reduce the amount of time spent writing and maintaining documentation since most of it is generated automatically.
* They allow developers to quickly test endpoints without needing additional tools.
* They make it easy to understand available endpoints, request bodies, and response structures, reducing the amount of time spent reading documentation and increasing the time spent actually building against the API.

As mentioned earlier, the API documentation is publicly accessible after starting the application:

`http://localhost:8080/swagger-ui/index.html#/`

Thanks to SpringDoc OpenAPI, all endpoints are documented automatically, with additional details provided through annotations in the code. All DTO schemas are also available in the **Schemas** section at the bottom of the page.

All endpoints can be tested directly through the Swagger UI. To access protected endpoints, a user must first obtain a JWT through the login endpoint and then provide it using the **Authorize** button. This allows authenticated requests to be made directly from within Swagger.

Although Swagger provides convenient endpoint testing, I still consider Postman collections necessary for more in-depth testing.

Swagger request examples are largely driven by annotations in the code, meaning that testing certain edge cases may require code changes and application restarts. With Postman, the server only needs to be started once, and request payloads can be modified freely, making it much faster to test different scenarios and invalid inputs.

The documentation is still a work in progress. Error responses and error schemas have not yet been fully documented.

# Recommended Testing Order

Some endpoint sample requests depend on resources created by earlier steps. For the smoothest testing experience, I recommend testing the endpoints in the following order:

1. Create a user (`/user/createUser`)
2. Log in (`/user/login`)

   * Feel free to explore the rest of the User endpoints. Just keep in mind that any changes you make to the sample user (such as updating the email or password) will affect subsequent requests.
3. Copy the generated JWT and authorize your requests using the **Authorize** button in Swagger.
4. Create a label (`/label/createLabel`)

   * Feel free to explore the rest of the Label endpoints. I recommend saving the delete endpoint until after Step 7. If you delete the label earlier, simply create another one and update the label ID in any subsequent sample requests.
5. Create a note color (`/noteColor/createColor`)

   * Feel free to explore the rest of the Note Color endpoints. As with labels, I recommend leaving the delete endpoint until after Step 7. If you delete the color earlier, recreate it and update the note color ID in any subsequent sample requests.
6. Create a note (`/note/createNote`)
7. Update the note (`/note/updateNote`)

Since the UI Template feature is not part of the typical user workflow, I did not include it in the recommended testing order. After completing the steps above, feel free to explore the remaining endpoints. The various `GET` endpoints are useful for confirming that your changes were applied as expected.

If you'd like to explore the data that already belongs to one of the demo users, simply log in using their credentials, authorize using the generated JWT, and use the `GET` endpoints in each entity group to view their associated data.

# Getting Started

The easiest way to run and test this API is through Docker.

### Prerequisites

- Docker Desktop
- Git
- IDE (optional, for exploring the codebase)

### Installation

1. If you do not already have Docker Desktop installed, follow Docker's official installation guide for your operating system:

   * Windows: `https://docs.docker.com/desktop/setup/install/windows-install/`
   * macOS: `https://docs.docker.com/desktop/setup/install/mac-install/`
   * Linux: `https://docs.docker.com/desktop/setup/install/linux/`

2. Clone this repository.

3. Open `.env.example` and update any environment variables you wish to customize, such as database credentials.

4. Rename `.env.example` to `.env`.

5. Start the application:

```bash
docker compose up
```

The initial startup may take a minute while Docker builds the images and initializes the database.

As long as port `8080` is available, the application should start without issues.

To verify that everything started successfully, navigate to:

```text
http://localhost:8080/swagger-ui/index.html#/
```

If the Swagger UI loads correctly, the API and database are running successfully.

### Stopping the Application

To stop the application run:

```bash
docker compose down
```

## Challenges and Lessons Learned

### JWT

By far, my biggest hurdle at the beginning of this project was implementing JWT.

It was especially difficult because I did not yet have a deep understanding of how Spring works under the hood. It took a lot of research, reading official documentation, and going through tutorials to fully understand the authentication flow and how Spring components work together to process requests and responses.

The most challenging part was learning the necessary classes and their methods. Although I had a solid theoretical understanding of the authentication flow, translating that into a working implementation was not straightforward.

However, the process was extremely fulfilling, especially seeing requests being properly processed after everything was implemented. This challenge solidified my resolve to finish the project regardless of difficulty and increased my confidence as a developer.

### Docker & Docker Compose

As this was my first time containerizing an application, I ran into several issues.

First, I had to understand Docker itself; how it works, what happens inside a container, how environment variables are passed, and how Spring application properties interact with containerized environments. I studied the official documentation and various online resources to gain this understanding.

Even after that, implementation was still challenging. My main issue was that the database container was starting too late, meaning my API was attempting to connect to a database that was not yet running. After implementing health checks and a `depends_on` configuration, everything worked as expected.

This process taught me a lot about Docker as a platform and helped me appreciate how how impressive and useful of a tool it is in real-world development.

### Models, JPA, and Hibernate

Understanding how models and database tables relate to each other took some time at the beginning.

Initially, I struggled with the idea that I was supposed to model entities closely to the database schema, especially since I did not want Hibernate to automatically generate tables from my models. I did not feel confident enough in my model design at the time to rely on that behavior.

After researching entity relationships implementations, I was able to successfully model the database and use Hibernate primarily for validation against the schema rather than generation. Once everything started running correctly and consistently, I felt a huge feeling of satisfaction and once again I felt my confidence as a developer increase.

### Debugging

Debugging a system while also trying to understand it was one of the most challenging parts of this project.

Being able to see how data moves line by line through the system; how it is received, transformed, and returned across layers, was extremely valuable. It helped me understand how all the components work together in practice rather than just in theory.
