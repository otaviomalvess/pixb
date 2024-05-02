# PixB

An HTTP API emulating how the pix (brazilian instant payment) system works.

Obs.: project meant to study back-end development with Java + Quarkus.

## Description

The application is separated into two main performers: the Bank and Bacen.

The Bank has functionality to manage user accounts, controls to draw and deposit, and manage pix requests states.

The Bacen has any functionality to manage pix entries and requests. It emulates the Dict and SPI APIs.  

## Technologies

Java
Quarkus
PostgreSQL
Docker

## Running the Application

As of now, the application only runs in quarkus development mode. Please, do:
```shell script
./mvnw compile quarkus:dev
```

Or

```shell script
quarkus dev
```

## API Docs

Base path:
```http
http://localhost:8080
```

### Account

#### Create new account

Creates a new account.

```http
  POST /bank/account
```

| Body Schema   | Type       | Description                           |
| :---------- | :--------- | :---------------------------------- |
| `cpf` | `string` | The account tax number. |
| `name` | `string` | The user name. |
| `branch` | `string` | The bank branch associated to the account. |

#### Get account

Returns an account.

```http
  GET /bank/account/{cpf}
```

| Path Param   | Type       | Description                                   |
| :---------- | :--------- | :------------------------------------------ |
| `cpf`      | `string` | The account tax number. |

#### Deposit to

Makes a deposit to an account.

```http
    PUT /bank/account/{cpf}/deposit
```

| Path Param   | Type       | Description                                   |
| :---------- | :--------- | :------------------------------------------ |
| `cpf`      | `string` | The account tax number. |

| Body Schema   | Type       | Description                                   |
| :---------- | :--------- | :------------------------------------------ |
| `value`      | `float` | The value of the deposit. |

### Pix

#### Create Pix Requests

Creates new pix requests.

```http
    POST /bank/pix/create-pix-request
```

| Body Schema (Array)   | Type       | Description                                   |
| :---------- | :--------- | :------------------------------------------ |
| `key`      | `string` | The pix key. |
| `value`      | `float` | The value of the transfer. |

#### Consult Pix Requests

Consults pending pix requests. If any returned, a deposit is made to the assocated accounts and an state update is sent.

```http
    GET /bank/pix/consult-pix-requests
```

#### Consult Pix Updates

Consults recently updated pix requests. If any are returned, a rolback operation is made on those with a failed state.

```http
    GET /bank/pix/consult-pix-requests
```

## Known Issues

The following are known issues that will be addressed in upcoming updates.

* Tests not working.
