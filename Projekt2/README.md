## Online-Shop

### Tabelle 1: Ressourcen und Methoden  

| **ID** | **Methode** | **Ressource**                                        |
|--------|-------------|------------------------------------------------------|
| 1      | GET         | /api/users/{userId}                                  |
| 2      | PUT         | /api/users/{userId}                                  |
| 3      | DELETE      | /api/users/{userId}                                  |
| 4      | GET         | /api/users/{userId}/orders/{orderId}                 |
| 5      | PUT         | /api/users/{userId}/orders/{orderId}                 |
| 6      | DELETE      | /api/users/{userId}/orders/{orderId}                 |
| 7      | GET         | /api/users                                           |
| 8      | POST        | /api/users                                           |
| 9      | GET         | /api/users/{userId}/orders                           |
| 10     | POST        | /api/users/{userId}/orders                           |
| 11     | POST        | /api/users/{userId}/orders/{orderId}/payments        |
| 12     | GET         | /api/users/{userId}/payments                         |

### Tabelle 2: Funktionen für den Benutzer  

| **ID** | **Beschreibung**                                                                                     |
|--------|-----------------------------------------------------------------------------------------------------|
| 1      | Einen bestimmten Benutzer abrufen (`GET /api/users/{userId}`).                                       |
| 2      | Die Daten eines bestimmten Benutzers aktualisieren (`PUT /api/users/{userId}`).                      |
| 3      | Einen bestimmten Benutzer löschen (`DELETE /api/users/{userId}`).                                    |
| 4      | Eine bestimmte Bestellung eines Benutzers abrufen (`GET /api/users/{userId}/orders/{orderId}`).      |
| 5      | Eine bestimmte Bestellung eines Benutzers aktualisieren (`PUT /api/users/{userId}/orders/{orderId}`).|
| 6      | Eine bestimmte Bestellung eines Benutzers löschen (`DELETE /api/users/{userId}/orders/{orderId}`).   |
| 7      | Alle Benutzer abrufen (`GET /api/users`).                                                            |
| 8      | Einen neuen Benutzer erstellen (`POST /api/users`).                                                 |
| 9      | Alle Bestellungen eines Benutzers abrufen (`GET /api/users/{userId}/orders`).                        |
| 10     | Eine neue Bestellung für einen Benutzer erstellen (`POST /api/users/{userId}/orders`).              |
| 11     | Eine Zahlung für eine Bestellung eines Benutzers erstellen (`POST /api/users/{userId}/orders/{orderId}/payments`). |
| 12     | Alle Zahlungen eines Benutzers abrufen (`GET /api/users/{userId}/payments`).                         | 