
### Tabelle 1: Ressourcen und Methoden 

| ID  | HTTP-Methode | Ressource                     |
|-----|--------------|-------------------------------|
| 1   | GET          | /users                        |
| 2   | GET          | /users/{userId}               |
| 3   | POST         | /users                        |
| 4   | PUT          | /users/{userId}               |
| 5   | DELETE       | /users/{userId}               |
| 6   | GET          | /users/{userId}/todos         |
| 7   | POST         | /users/{userId}/todos         |
| 8   | PUT          | /users/{userId}/todos/{todoId}|
| 9   | DELETE       | /users/{userId}/todos/{todoId}|
| 10  | GET          | /users/{userId}/bookmarks     |
| 11  | GET          | /users/{userId}/bookmarks/{bookmarkId} |
| 12  | POST         | /users/{userId}/bookmarks     |
| 13  | PUT          | /users/{userId}/bookmarks/{bookmarkId} |
| 14  | DELETE       | /users/{userId}/bookmarks/{bookmarkId} |

---

### Tabelle 2: Funktionen für den Benutzer  


| **ID** | **Beschreibung**                                                                                     |
|--------|-----------------------------------------------------------------------------------------------------|
| 1      | Gibt eine Liste aller Benutzer zurück (`GET /users`).                                                |
| 2      | Gibt die Informationen eines bestimmten Benutzers basierend auf der ID zurück (`GET /users/{userId}`).|
| 3      | Erstellt einen neuen Benutzer (`POST /users`).                                                       |
| 4      | Aktualisiert die Daten eines Benutzers basierend auf der ID (`PUT /users/{userId}`).                 |
| 5      | Löscht einen Benutzer basierend auf der ID (`DELETE /users/{userId}`).                               |
| 6      | Gibt eine Liste aller ToDos eines bestimmten Benutzers zurück (`GET /users/{userId}/todos`).         |
| 7      | Erstellt ein neues ToDo für einen bestimmten Benutzer (`POST /users/{userId}/todos`).                |
| 8      | Aktualisiert ein ToDo basierend auf Benutzer- und ToDo-ID (`PUT /users/{userId}/todos/{todoId}`).    |
| 9      | Löscht ein ToDo basierend auf Benutzer- und ToDo-ID (`DELETE /users/{userId}/todos/{todoId}`).       |
| 10     | Gibt eine Liste aller Bookmarks eines bestimmten Benutzers zurück (`GET /users/{userId}/bookmarks`). |
| 11     | Gibt die Details eines bestimmten Bookmarks zurück (`GET /users/{userId}/bookmarks/{bookmarkId}`).   |
| 12     | Erstellt ein neues Bookmark für einen bestimmten Benutzer (`POST /users/{userId}/bookmarks`).        |
| 13     | Aktualisiert ein Bookmark basierend auf Benutzer- und Bookmark-ID (`PUT /users/{userId}/bookmarks/{bookmarkId}`). |
| 14     | Löscht ein Bookmark basierend auf Benutzer- und Bookmark-ID (`DELETE /users/{userId}/bookmarks/{bookmarkId}`). |