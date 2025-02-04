# Prediction API Documentation (Revised)

This document provides details about the endpoints available in the `PredictionController` for managing and retrieving prediction data. All endpoints are prefixed with `/api/v1/predictions`.

---

## **JSON Field Descriptions**

The prediction data is represented in the following JSON format. Below is an explanation of each field:

| Field         | Type    | Description                                                                 |
|---------------|---------|-----------------------------------------------------------------------------|
| `season`      | Integer | The year of the season (e.g., `2023`).                                      |
| `round`       | String  | The round identifier (e.g., `"Round 1"`).                                   |
| `venue`       | String  | The name of the venue where the match is held (e.g., `"Stadium A"`).        |
| `date`        | String  | The date of the match in `YYYY-MM-DD` format (e.g., `"2023-03-15"`).        |
| `homeTeam`    | String  | The name of the home team (e.g., `"Team A"`).                               |
| `awayTeam`    | String  | The name of the away team (e.g., `"Team B"`).                               |
| `prediction`  | Integer | The prediction outcome: `1` for home team win, `0` for home team loss.      |
| `homeScore`   | Integer | The score of the home team (e.g., `24`).                                    |
| `awayScore`   | Integer | The score of the away team (e.g., `12`).                                    |
| `margin`      | Integer | The margin of victory for the winner (e.g., `12` for a 12-point win).       |

---

## **Endpoints**

### **1. Get All Predictions**
**GET** `/`  
Retrieves a list of all predictions stored in the system.

#### Example Request:
```http
GET /api/v1/predictions
```

#### Response:
```json
[
  {
    "season": 2023,
    "round": "Round 1",
    "venue": "Stadium A",
    "date": "2023-03-15",
    "homeTeam": "Team A",
    "awayTeam": "Team B",
    "prediction": 1,
    "homeScore": 24,
    "awayScore": 12,
    "margin": 12
  },
  ...
]
```

---

### **2. Get Latest Predictions**
**GET** `/latest`  
Retrieves the most recently added predictions (e.g., latest season/round).

#### Example Request:
```http
GET /api/v1/predictions/latest
```

#### Response:
```json
[
  {
    "season": 2023,
    "round": "Round 10",
    "venue": "Stadium B",
    "date": "2023-08-20",
    "homeTeam": "Team X",
    "awayTeam": "Team Y",
    "prediction": 0,
    "homeScore": 10,
    "awayScore": 18,
    "margin": 8
  },
  ...
]
```

---

### **3. Get Predictions by Season and Round**
**GET** `/season/{season}/round/{round}`  
Retrieves predictions for a specific `season` and `round`.

#### Path Parameters:
- `season` (Integer): The season year (e.g., `2023`).
- `round` (String): The round identifier (e.g., `"Round 5"`).

#### Example Request:
```http
GET /api/v1/predictions/season/2023/round/Round%205
```

#### Response:
```json
[
  {
    "season": 2023,
    "round": "Round 5",
    "venue": "Stadium C",
    "date": "2023-05-01",
    "homeTeam": "Team C",
    "awayTeam": "Team D",
    "prediction": 1,
    "homeScore": 30,
    "awayScore": 28,
    "margin": 2
  }
]
```

---

### **4. Get Predictions by Home Team**
**GET** `/homeTeam/{homeTeam}`  
Retrieves predictions where the specified team is the **home team**.

#### Path Parameters:
- `homeTeam` (String): Name of the home team (e.g., `"Team G"`).

#### Example Request:
```http
GET /api/v1/predictions/homeTeam/Team%20G
```

#### Response:
```json
[
  {
    "season": 2023,
    "round": "Round 3",
    "venue": "Stadium A",
    "date": "2023-04-10",
    "homeTeam": "Team G",
    "awayTeam": "Team B",
    "prediction": 1,
    "homeScore": 22,
    "awayScore": 15,
    "margin": 7
  }
]
```

---

### **5. Get Predictions by Away Team**
**GET** `/awayTeam/{awayTeam}`  
Retrieves predictions where the specified team is the **away team**.

#### Path Parameters:
- `awayTeam` (String): Name of the away team (e.g., `"Team B"`).

#### Example Request:
```http
GET /api/v1/predictions/awayTeam/Team%20B
```

#### Response:
Same structure as **Endpoint 4**, filtered by `awayTeam`.

---

### **6. Get Predictions by Season**
**GET** `/season/{season}`  
Retrieves all predictions for a specific season.

#### Path Parameters:
- `season` (Integer): The season year (e.g., `2023`).

#### Example Request:
```http
GET /api/v1/predictions/season/2023
```

#### Response:
List of predictions for the season (see **Endpoint 1** for format).

---

### **7. Get a Specific Prediction by Composite Key**
**GET** `/{season}/{round}/{homeTeam}`  
Retrieves a single prediction using its composite key (`season`, `round`, `homeTeam`).

#### Path Parameters:
- `season` (Integer): The season year (e.g., `2023`).
- `round` (String): The round identifier (e.g., `"Round 1"`).
- `homeTeam` (String): Name of the home team (e.g., `"Team A"`).

#### Example Request:
```http
GET /api/v1/predictions/2023/Round%201/Team%20A
```

#### Response:
```json
{
  "season": 2023,
  "round": "Round 1",
  "venue": "Stadium A",
  "date": "2023-03-15",
  "homeTeam": "Team A",
  "awayTeam": "Team B",
  "prediction": 1,
  "homeScore": 24,
  "awayScore": 12,
  "margin": 12
}
```

---

### **8. Create a Prediction**
**POST** `/`  
Creates a new prediction.

#### Request Body:
```json
{
  "season": 2023,
  "round": "Round 1",
  "venue": "Stadium A",
  "date": "2023-03-15",
  "homeTeam": "Team A",
  "awayTeam": "Team B",
  "prediction": 1,
  "homeScore": 24,
  "awayScore": 12,
  "margin": 12
}
```

#### Response:
- **201 Created**: Returns the saved prediction.
- **400 Bad Request**: Invalid input (e.g., missing required fields).

---

### **9. Update a Prediction**
**PUT** `/{season}/{round}/{homeTeam}`  
Updates an existing prediction using its composite key.

#### Path Parameters:
- `season`, `round`, `homeTeam` (same as **Endpoint 7**).

#### Request Body:
```json
{
  "venue": "Updated Stadium",
  "prediction": 0,
  "homeScore": 20,
  "awayScore": 25,
  "margin": 5
}
```

#### Response:
- **200 OK**: Returns the updated prediction.

---

### **10. Delete a Prediction**
**DELETE** `/{season}/{round}/{homeTeam}`  
Deletes a prediction using its composite key.

#### Response:
- **200 OK**: Success message.
- **404 Not Found**: If prediction does not exist.

---

## **Composite Key Structure**
Predictions are uniquely identified by:
- `season` (Integer)
- `round` (String)
- `homeTeam` (String)

Ensure all three values are provided for relevant endpoints (e.g., GET, PUT, DELETE).

---

