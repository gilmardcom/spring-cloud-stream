# Spring Cloud Stream: Sample Project

This project aims to provide a simple yet complete example of how Spring Cloud Stream can facilitate the communication between microservices.

## Disclaimer

to do.

## License

to do.

## Project Structure

### The microservices landscape is as the following:

### Communication between services via SC-Stream

* User asks a question
* Gateway gets the question and sends to DistributerService
* DistributerService distributes the question to either TimeService or DateService
    * based on the question type
* TimeService or DateService provides the answer
* Gateway sends the answer back to User

**Note:** all the communications are done via Stream 'topics' including: *Question, Answer, TimeQuestion, DateQuestion*

<img src="doc/system-landscape.svg" alt="System landscape">

