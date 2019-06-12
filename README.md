# AirR&D Backend

This is the backend server portion of the AirR&D project.

**Make sure you have Maven and Java 1.7 or greater**

```bash

# install the repo with mvn
./mvn install

# start the server
./mvn spring-boot:run

# the app will be running on port 8080
# Example user accounts
# - username10:123456
# - username20:123456
```

## Configuration

- **WebSecurityConfig.java**: The server-side authentication configurations.
- **application.yml**: Application level properties i.e the token expire time, token secret etc. You can find a reference of all application properties [here](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html).
- **JWT token TTL**: JWT Tokens are configured to expire after 10 minutes, you can get a new token by signing in again.
- **Using a different database**: This project is using an embedded H2 database that is automatically configured by Spring Boot. If you want to connect to another database you have to specify the connection in the *application.yml* in the resource directory. Here is an example for a MySQL DB:

## File Structure
```
├── angular-spring-starter.iml
├── docker-compose.yml
├── Dockerfile
├── LICENSE
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── README-original.md
└── src                                                             * our source files
    └── main
        ├── java
        │   └── com
        │       └── bfwg
        │           ├── Application.java                            * Application main enterance
        │           ├── config
        │           │   ├── SwaggerConfig.java
        │           │   ├── ThymeleafConfig.java
        │           │   ├── WebConfig.java
        │           │   └── WebSecurityConfig.java                  * security configureation file, all the important things.
        │           ├── exception
        │           │   ├── ExceptionHandlingController.java
        │           │   ├── ExceptionResponse.java
        │           │   └── ResourceConflictException.java
        │           ├── model
        │           │   ├── Authority.java
        │           │   ├── Available.java
        │           │   ├── CustomLoginResponse.java
        │           │   ├── CustomUserLoginResponse.java
        │           │   ├── Image.java
        │           │   ├── Location.java
        │           │   ├── Reservation.java
        │           │   ├── ReservationRequest.java
        │           │   ├── Review.java
        │           │   ├── User.java                               * our main user model which implements UserDetails.
        │           │   ├── UserRequest.java
        │           │   ├── UserRoleName.java
        │           │   ├── UserTokenState.java                     * stores the token states like token_key and token_ttl.
        │           │   ├── VehicleEnrichmentResponse.java
        │           │   ├── Vehicle.java
        │           │   └── VehicleRequest.java
        │           ├── repository                                  * repositories folder for accessing database
        │           │   ├── AuthorityRepository.java
        │           │   ├── AvailableRepository.java
        │           │   ├── ImageRepository.java
        │           │   ├── ReservationRepository.java
        │           │   ├── ReviewRepository.java
        │           │   ├── UserRepository.java
        │           │   └── VehicleRepository.java
        │           ├── rest                                        * rest endpoint folder
        │           │   ├── AuthenticationController.java           * auth related REST controller.
        │           │   ├── AvailableController.java    
        │           │   ├── GeocodingController.java
        │           │   ├── ImageController.java
        │           │   ├── PublicController.java
        │           │   ├── ReservationController.java
        │           │   ├── ReviewController.java
        │           │   ├── TwitterController.java
        │           │   ├── UserController.java                     * user/admin REST controller to handle User related requests
        │           │   └── VehicleController.java
        │           ├── security                                    * Security related folder(JWT, filters)
        │           │   ├── auth
        │           │   │   ├── AnonAuthentication.java             * it creates Anonymous user authentication object. If the user doesn't have a token, we mark the user as an anonymous visitor.
        │           │   │   ├── AuthenticationFacade.java
        │           │   │   ├── AuthenticationFailureHandler.java   * login fail handler, configrued in WebSecurityConfig
        │           │   │   ├── AuthenticationSuccessHandler.java   * login success handler, configrued in WebSecurityConfig
        │           │   │   ├── IAuthenticationFacade.java
        │           │   │   ├── LogoutSuccess.java                  * controls the behavior after sign out.
        │           │   │   ├── RestAuthenticationEntryPoint.java   * logout success handler, configrued in WebSecurityConfig
        │           │   │   ├── TokenAuthenticationFilter.java      * the JWT token filter, configured in WebSecurityConfig
        │           │   │   └── TokenBasedAuthentication.java       * this is our custom Authentication class and it extends AbstractAuthenticationToken.
        │           │   ├── CorsFilter.java
        │           │   └── TokenHelper.java                        * token helper class that responsible to token generation, validation, etc.
        │           ├── service
        │           │   ├── AuthorityService.java
        │           │   ├── AvailableService.java
        │           │   ├── EmailService.java
        │           │   ├── GeocodingService.java
        │           │   ├── ImageService.java
        │           │   ├── impl
        │           │   │   ├── AuthorityServiceImpl.java
        │           │   │   ├── AvailableServiceImpl.java
        │           │   │   ├── CustomUserDetailsService.java       * custom UserDatilsService implementataion, tells formLogin() where to check username/password
        │           │   │   ├── EmailServiceImpl.java
        │           │   │   ├── GeocodingServiceImpl.java
        │           │   │   ├── ImageServiceImpl.java
        │           │   │   ├── ReservationServiceImpl.java
        │           │   │   ├── ReviewServiceImpl.java
        │           │   │   ├── TwitterServiceImpl.java
        │           │   │   ├── UserServiceImpl.java
        │           │   │   ├── VehicleInformationServiceImpl.java
        │           │   │   └── VehicleServiceImpl.java
        │           │   ├── ReservationService.java
        │           │   ├── ReviewService.java
        │           │   ├── TwitterService.java
        │           │   ├── UserService.java
        │           │   ├── VehicleInformationService.java
        │           │   └── VehicleService.java
        │           └── stale_outputs_checked
        └── resources
            ├── application-dev.yml.example
            ├── application.yml                                     * application variables are configured here
            ├── data.sql
            ├── import.sql                                          * h2 database query(table creation)
            ├── static
            │   └── images
            ├── templates
            └── twitter4j.properties.example
    └── test
            └── java
                └── com
                    └── bfwg
                        ├── AbstractTest.java
                        ├── ControllerTests
                        │   └── UserControllerTests.java
                        ├── MockMvcConfig.java
                        ├── repository
                        │   ├── ReservationRepositoryIntegrationTest.java
                        │   └── VehicleRepositoryIntegrationTest.java
                        ├── rest
                        │   ├── AvailableControllerIntegrationTests.java
                        │   ├── ImageControllerIntegrationTest.java
                        │   ├── ReservationControllerIntegrationTest.java
                        │   ├── ReviewControllerIntegrationTest.java
                        │   ├── UserControllerIntegrationTests.java
                        │   └── VehicleControllerIntegrationTests.java
                        ├── security
                        │   └── TokenHelperTest.java
                        └── service
                            ├── impl
                            │   └── ReservationServiceImplIntegrationTest.java
                            └── UserServiceTest.java
```
