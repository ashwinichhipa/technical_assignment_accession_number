# Accession Number Ordering

### Setup
 ```
 JDK version : 1.8.0
 OS tested  : Windows 10
 Maven version : 3.5.0
 Eclipse version : Oxygen 4.7.0
```

``` 
 git clone https://github.com/ashwinichhipa/technical_assignment_accession_number.git 
 cd technical_assignment_accession_number
```
  
### Test cases execution
 ``` 
 mvn test 
 ``` 

### To run the application:

```
 mvn spring-boot:run

 or,

 mvn package && java -jar target/Accession-0.0.1-SNAPSHOT.jar 
```

```
 Once server is started , access the application using 

 http://localhost:8080/home

```

### Assumptions:
 Expect the input data in LLLL..LLNNN...NN format only
 Where LL..L denotes one or more ASCII letters and NNN...NN denotes one or more digits
