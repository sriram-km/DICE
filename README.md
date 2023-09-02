# Weather App

The Weather App is a Spring Boot application that provides weather information based on client credentials. It allows you to generate credentials, access weather data, and protect endpoints using client IDs and client secrets.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Configuration](#configuration)
- [APIs](#apis)
- [How to Start](#how-to-start)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 8 or higher installed on your system.
- Maven installed to build and manage project dependencies.

## Configuration

To configure the application, you can use the `application.properties` file located in the `src/main/resources` directory. Here are the properties you can configure:

- `server.port`: The port on which the application will run. Default is 8080.

- `rapidapi.key`: The API key used to access weather data. You can get a free API key from [RapidAPI](https://rapidapi.com/community/api/open-weather-map).
- `rapidapi.host`: The host used to access weather data.
- `csv.file.path`: The path to the CSV file to store credentials.

## APIs

### 1. Generate New Credentials

Generates new client credentials that can be used to access weather data.

- **Endpoint:** `/api/weather/get-new-credentials`
- **HTTP Method:** GET
- **Response:** JSON containing client ID and client secret.

Example Response:

```json
{
  "clientId":"9CAAHybp2YpqyhqC",
  "clientSecret":"phbcHPPLoIM94QWvUa1R8sYqUNaJJ1er"
}
```

### 2. Weather Summary Data

Retrieve weather summary data using client credentials.

- **Endpoint:** `/api/weather/summary`
- **HTTP Method:** GET
- **Request Headers:** Include `client-id` and `client-secret` headers with valid client credentials.
- **Response:** JSON containing weather summary information.

### 3. Weather Hourly Data

Retrieve weather hourly data using client credentials.

- **Endpoint:** `/api/weather/hourly`
- **HTTP Method:** GET
- **Request Headers:** Include `c---

Feel free to modify and expand this README.md to suit the specific details of your project. This template provides a starting point for documenting your Spring Boot application effectively.lient-id` and `client-secret` headers with valid client credentials.
- **Response:** JSON containing weather information.

## How to Start

Follow these steps to run the Weather App:

1. Clone the repository to your local machine.

2. Navigate to the project directory.

3. Build the project using Maven:

   ```bash
   mvn clean install
   ```

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```

The application should now be running locally on the specified port (default is 8080).

## Usage

1. Generate credentials by making a GET request to `/api/weather/get-new-credentials`.

2. Access weather data by making a GET request to `/api/weather/summary` or `/api/weather/hourly` with the `client-id` and `client-secret` headers set to the values generated in step 1.


## Contributing

To contribute to this project, follow these steps:

1. Fork this repository.

2. Create a new branch: `git checkout -b feature/your-feature-name`.

3. Commit your changes and push to the branch.

4. Create a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.