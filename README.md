# dreamHome_Group1

## Git Arbeitsschritte

### bei Arbeitsbgeinn

1. `git checkout develop`
2. `git pull`
3. `git checkout {euer branch}`
4. `git merge develop --no-ff`

### während dem Arbeiten

1. Änderungen durchführen
2. `git add -A` um alle Änderungen zum nächsten Commit hinzuzufügen
3. `git commit -m "{commit message}"`

Die drei Schritte für jede kleine Aufgabe wiederholen

### bei Arbeitsende

1. `git push`

## Angular

im Projektordner in dei cmd `ng serve` eingeben um den Entwicklungsserver zu starten.
Der Server ist unter `localhost:4200` erreichbar.
Mit `npm install` können fehlende Packete nachinstalliert werden

## Spring Boot
in der pom.xml sind alle benötigten Packete hinterlegt

### REST API
die Anwendung hört auf Port 8080, lokale URL: localhost:8080
`/api` ist der Endpunkt für die Schnittstelle

#### apartments
* Endpoint: `/apartments`
* `GET` return Map<String, List<Apartment>>, Key: apartments
* `GET /{id}` return Map<String, List<Apartment>>, Key: apartments, kann auf Grund der ID nur ein Element in der ArrayList enthalten
* `POST` muss ein Apartment im Body enthalten, returns  Map<String, String>, Key: message
* `PUT` muss ein Apartment im Body enthalten, returns  Map<String, String>, Key: message
* `DELETE /{id}` returns  Map<String, String>, Key: message

#### houses
* Endpoint `/houses`
* `GET` return Map<String, List<House>>, Key: houses
* `GET /{id}` return Map<String, List<House>>, Key: houses, kann auf Grund der ID nur ein Element in der ArrayList enthalten
* `POST` muss ein House im Body enthalten, returns  Map<String, String>, Key: message
* `PUT` muss ein House im Body enthalten, returns  Map<String, String>, Key: message
* `DELETE /{id}` returns  Map<String, String>, Key: message

#### lease contracts
* Endpoint `/lease-contracts`
* `GET` return Map<String, List<LeaseContract>>, Key: leaseContracts
* `GET /{id}` return Map<String, List<LeaseContract>>, Key: houses, kann auf Grund der ID nur ein Element in der ArrayList enthalten
* `POST` muss ein LeaseContract im Body enthalten, returns  Map<String, String>, Key: message
* `PUT` muss ein LeaseContract im Body enthalten, returns  Map<String, String>, Key: message
* `DELETE /{id}` returns  Map<String, String>, Key: message

#### operating cost statement
* Endpoint `/operating-cost-statement`
* `GET` return Map<String, List<OperatingCostStatementRenter>>, Key: operatingCostStatementRenters
* `GET /q` braucht die beiden Request Parameter "renterId" als Integer und "year" als Year, return ResponseEntity, Content Disposition: attachment; filename="operating_cost_statement_{renterId}_{year}.xlsx

#### renters
* Endpoint `/renters`
* `GET` return Map<String, List<Renter>>, Key: renters
* `GET /{id}` return Map<String, List<Renter>>, Key: houses, kann auf Grund der ID nur ein Element in der ArrayList enthalten
* `POST` muss ein Renter im Body enthalten, returns  Map<String, String>, Key: message
* `PUT` muss ein Renter im Body enthalten, returns  Map<String, String>, Key: message
* `DELETE /{id}` returns  Map<String, String>, Key: message

#### statement of bank account
* Endpoint `/statement-of-bank-account`
* `GET` return Map<String, List<StatementOfBankAccount>>, Key: statementsOfBankAccount
* `POST /upload` bracuht den Request Parameter file als Multipart File (csv), return Map<String, String>, Key: message

## SQL Server
Host: itnt0005
Port: 1433

### schema
Das ERM liegt im Ordner docs

### primary Keys
Die letzte Ziffer der PKs gibt an, um welche Entität es sich handlet.
* XXXX0 Renter
* XXXX1 Apartment
* XXXX2 Lease Contract
* XXXX3 Account Movement
* XXXX4 House
