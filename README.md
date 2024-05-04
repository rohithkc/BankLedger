**Important: Don't forget to update the [Candidate README](#candidate-readme) section**

Real-time Transaction Challenge
===============================
## Overview
Welcome to Current's take-home technical assessment for backend engineers! We appreciate you taking the time to complete this, and we're excited to see what you come up with.

You are tasked with building a simple bank ledger system that utilizes the [event sourcing](https://martinfowler.com/eaaDev/EventSourcing.html) pattern to maintain a transaction history. The system should allow users to perform basic banking operations such as depositing funds, withdrawing funds, and checking balances. The ledger should maintain a complete and immutable record of all transactions, enabling auditability and reconstruction of account balances at any point in time.

## Details
The [included service.yml](service.yml) is the OpenAPI 3.0 schema to a service we would like you to create and host.

The service accepts two types of transactions:
1) Loads: Add money to a user (credit)

2) Authorizations: Conditionally remove money from a user (debit)

Every load or authorization PUT should return the updated balance following the transaction. Authorization declines should be saved, even if they do not impact balance calculation.


Implement the event sourcing pattern to record all banking transactions as immutable events. Each event should capture relevant information such as transaction type, amount, timestamp, and account identifier.
Define the structure of events and ensure they can be easily serialized and persisted to a data store of your choice. We do not expect you to use a persistent store (you can you in-memory object), but you can if you want. We should be able to bootstrap your project locally to test.

## Expectations
We are looking for attention in the following areas:
1) Do you accept all requests supported by the schema, in the format described?

2) Do your responses conform to the prescribed schema?

3) Does the authorizations endpoint work as documented in the schema?

4) Do you have unit and integrations test on the functionality?

Here’s a breakdown of the key criteria we’ll be considering when grading your submission:

**Adherence to Design Patterns:** We’ll evaluate whether your implementation follows established design patterns such as following the event sourcing model.

**Correctness**: We’ll assess whether your implementation effectively implements the desired pattern and meets the specified requirements.

**Testing:** We’ll assess the comprehensiveness and effectiveness of your test suite, including unit tests, integration tests, and possibly end-to-end tests. Your tests should cover critical functionalities, edge cases, and potential failure scenarios to ensure the stability of the system.

**Documentation and Clarity:** We’ll assess the clarity of your documentation, including comments within the code, README files, architectural diagrams, and explanations of design decisions. Your documentation should provide sufficient context for reviewers to understand the problem, solution, and implementation details.

# Candidate README
## Bootstrap instructions
1) Start by cloning the source code from the GitHub repository to your local machine. Open your terminal and run the following command:
    git clone https://github.com/yourusername/yourrepository.git
    cd yourrepository

2) Inside the project directory, execute the following Maven command to build the application and download all necessary dependencies:
    mvn clean install

3) Once the build is complete, you can start the server by running:
    mvn spring-boot:run

4) Open a web browser or use a tool like curl to verify that the server is running correctly. For example, you can make a request to the root URL:
    curl http://localhost:8080
    
5) You can also run all the integration/unit tests by running:
    mvn test

## Design considerations
When building this application, I focused on several critical design choices that significantly boost its functionality, scalability, and how easy it is to maintain:

1) I chose to implement a ConcurrentHashMap for managing transactions in TransactionRepository.java rather than using a standard HashMap. My decision was mainly driven by the necessity for thread safety and enhancing performance in situations where multiple threads are accessing and modifying data concurrently. The ConcurrentHashMap is particularly suited for our transaction handling system, as it supports high levels of concurrency without sacrificing thread safety, which is crucial in a banking system where transaction requests are frequently processed simultaneously.

2) Instead of bundling EventType with Account.java or other model classes, I decided it warranted its own space and created a separate Java enum file for it. This separation does more than just tidy up the code—it boosts the modular nature of our application, allowing for cleaner management and updates of event types without tangling with account logic. This clarity not only makes the codebase easier to navigate but also simplifies scaling and maintaining it.

3) I updated the return type in our TransactionController from a plain Double to ResponseEntity<Double>. This adjustment transforms how responses are handled, providing not just the transaction data but also detailed HTTP status codes and headers. Utilizing ResponseEntity<Double> enriches our API's feedback, enabling it to deliver precise transaction outcomes along with relevant HTTP statuses. This detail is essential in REST APIs, where the HTTP response can immediately inform the client about the success of their request or if there were issues, like a bad request or a server error.

4) I decided to implement the server using Spring Boot due to its vast ecosystem and support for rapidly setting up web applications. Spring Boot allows for easy integration of various data sources and third-party libraries, which enhanced the development process significantly. The use of Spring Boot's dependency injection and its comprehensive suite of testing tools also supported a clean, test-driven development approach.



## Assumptions
While designing the service, I assumed that the system would handle a moderate load of concurrent requests. This assumption influenced the choice of a non-blocking web framework and an in-memory database for speed and simplicity, suitable for demonstration purposes but scalable with minor adjustments for production environments.

## Bonus: Deployment considerations
For deployment, I would use Docker to containerize the application, ensuring consistency across different environments. The container would then be deployed to a Kubernetes cluster managed by a cloud provider such as AWS or Azure for scalability and high availability. 

Additionally, I would integrate a CI/CD pipeline using Jenkins or GitHub Actions to automate the testing and deployment process, enabling continuous delivery. For monitoring and logging, tools like Splunk and New Relic would be integrated to ensure the application's health and performance are transparently managed.


## License

At CodeScreen, we strongly value the integrity and privacy of our assessments. As a result, this repository is under exclusive copyright, which means you **do not** have permission to share your solution to this test publicly (i.e., inside a public GitHub/GitLab repo, on Reddit, etc.). <br>

## Submitting your solution

Please push your changes to the `main branch` of this repository. You can push one or more commits. <br>

Once you are finished with the task, please click the `Submit Solution` link on <a href="https://app.codescreen.com/candidate/e0f134ec-773e-4200-9dc2-ce50e81872c6" target="_blank">this screen</a>.