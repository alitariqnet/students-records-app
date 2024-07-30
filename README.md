This app takes a uploaded text file which contains students' records and parses it and saves the records in student repository. Also some APIs are included to search students with JPA criteria API using Specification. 
##### File to parse:
![image](https://github.com/user-attachments/assets/c94166ca-87eb-4eb6-85f3-8bdba625b128)

##### Running application upload form
![image](https://github.com/user-attachments/assets/cced9847-523e-4b0a-b52b-c1e2374fa1dc)


##### Swagger UI API documentation url
![image](https://github.com/user-attachments/assets/8a9fa988-8234-47b0-b4f9-2aa6043b144d)


##### Postman collection API endpoints with payload
[Student-Record-API.postman_collection.json](https://github.com/user-attachments/files/16317797/Student-Record-API.postman_collection.json)

##### Helping screenshots
![image](https://github.com/user-attachments/assets/34d2bd4a-a4e4-4926-8bee-4daa0238a6e9)
![image](https://github.com/user-attachments/assets/564756ec-3003-4b16-b520-b668c778b2d6)

##### File parsing per record logging
![image](https://github.com/user-attachments/assets/838cc549-e50f-4267-9778-72139d09af91)

##### Docker support has been added
###### To run this application in a containerized environment do the following,
1. In the root folder where Dockerfile is present run this command docker build -t studentrecord .
2. After it finishes to build the image, run docker-compose up
   and it will spin up two containers, one for the student record app and other for the postgreSQL so you
   no need to install postgreSQL locally. Remember one thing, the tag you give to the student app must match with the image value
   under app under services in docker-compose.yml file, otherwise it can cause error due to unable to find that image.
