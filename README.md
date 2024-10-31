# IncuDO Course Management

- IncuDO is a Java application for managing reservations of professional courses aimed at migrants and disadvantaged individuals. The purpose of IncuDO is to  offer training programs for preserving traditional crafts, supporting the repopulation of small towns.
- The application manages users, courses, and reservations through a command-line interface (CLI) with data loaded from CSV files at startup.
- License: MIT

## Features
- **Automatic Data Loading**: `utenti.csv`, `corsi.csv`, and `prenotazioni.csv` files are automatically read and loaded into memory at the application's startup.
- **Command-Line Interface (CLI)**: Users can interact with the application via a text-based menu.
- **Reservation Management**:
  - View all available courses.
  - Book and cancel course reservations.
- **Data Export**:
  - Export available reservations to a CSV file with the current date.
  - Export lists of users and courses.
- **Additional Commands**:
  - View and manage users, courses, and reservations by ID.
  - Delete users, courses, and reservations by ID.


## Getting Up and Running Locally

### Prerequisites
- **Java Development Kit (JDK)** 17 or higher
- **Apache Maven**: This project requires Maven for dependency management and building. If Maven is not already installed on your system, please install it first by following the instructions at [Maven Installation Guide](https://maven.apache.org/install.html).

### Setup

```bash
git clone https://github.com/Isacco-B/IncuDO.git
```

```bash
cd IncuDO/app/
```

```bash
mvn clean package
```

```bash
java -jar incudo-app.jar
```

### Important!
- To successfully load the CSV data, ensure the .jar file is located in the same directory as the resources folder containing the required CSV files (utenti.csv, corsi.csv, and prenotazioni.csv). The application will not work if run from a different directory structure.


## 🔗 Links

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/isacco-bertoli-10aa16252/)
