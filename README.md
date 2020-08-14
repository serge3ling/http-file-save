# http-file-save

This Spring Boot application manages saving files in a database.
It handles HTTP requests for that, such as POST, GET and DELETE.

The controller of the application provides
2 groups of methods
for handling HTTP requests
- working like a REST API microservice, or
- with web page interface.

The database management system used:
- MongoDB.

The application saves a file in MongoDB as a BSON document
or in MongoDB's GridFS, depending on its size. 

You can change port, database connection,
BSON/GridFS threshold
in ``application.yml``.
