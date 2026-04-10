# Testing related:
## 1. Unit Testing
Unit testing focuses on testing individual functions or classes in isolation, usually with mocked dependencies. It is the smallest level of testing and helps quickly detect bugs in business logic.
## 2. Functional Testing
Functional testing verifies whether a feature behaves according to requirements from a user perspective. It focuses on inputs and outputs rather than internal implementation.
For example, calling an API to create a comment and checking whether the response is correct.
## 3. Integration Testing
Integration testing checks how different components work together, such as service and database interaction. It ensures that modules communicate correctly.
For example, testing CommentService together with CommentRepository.
## 4. Regression Testing
Regression testing ensures that new code changes do not break existing functionality. It is usually done after bug fixes or new features.
For example, after modifying comment logic, re-running tests to ensure old APIs still work.
## 5. Smoke Testing
Smoke testing is a quick check to verify that the application starts and core features work. It is usually performed before deeper testing.
For example, verifying that the server starts and basic endpoints return responses.
## 6. Performance Testing
Performance testing evaluates system behavior under expected load, focusing on response time and throughput.
For example, measuring how fast the comment API responds under normal user traffic.
## 7. Stress Testing
Stress testing pushes the system beyond its limits to observe failure behavior. It helps identify system stability and bottlenecks.
For example, sending thousands of requests to the comment API to see when it crashes.
## 8. A/B Testing
A/B testing compares two versions of a feature to determine which performs better. It is commonly used in product and UI decisions.
For example, testing two different comment layouts and measuring user engagement.
## 9. End-to-End Testing
End-to-end testing verifies the entire workflow of a system from start to finish. It simulates real user behavior across multiple components.
For example, user logs in → creates post → adds comment → views comment.
## 10. User Acceptance Testing
UAT is performed by end users or clients to confirm that the system meets business requirements. It is usually the final validation before release.
For example, stakeholders testing whether the comment feature satisfies product expectations.
# Environment related:

## 1. Development
The development environment is used by developers to write and test code locally. It is flexible and may contain incomplete features.
For example, running the redbook project on a local machine with H2 database.
## 2. QA (Quality Assurance)
The QA environment is used for testing features in a controlled setting. It ensures that the system meets quality standards before release.
For example, testers verifying comment APIs and catching bugs.
## 3. Pre-prod/Staging
The staging environment closely mirrors production and is used for final testing. It helps validate system behavior before deployment.
For example, testing the full application with production-like data and configurations.
## 4. Production
The production environment is the live system used by real users. Stability, security, and performance are critical.
For example, deployed redbook application serving real users’ comments