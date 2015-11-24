# Spring Batch with Retry

This project is a simple example of a Spring Boot based Spring Batch project that has a job configured with retry capabilities.

You can test this project by simply running the Application class.

This will cause the importUserJob to be launched.  You will see the first record of the sample-data.csv file processed, then a contrived exception is thrown trying to transform the record for Joe, and then Joe and the remaining records are processed.

The RetryTemplate bean configured in the BatchConfiguration is setup with a simple policy that will just retry the call a max of 5 times.  In this case, the PersonItemProcessor is setup to fail the first time through when it encounters a Person object with the first name of "Joe", but then succeeds the rest of the time.

You can read more about retry configurations at http://docs.spring.io/spring-batch/reference/html/retry.html