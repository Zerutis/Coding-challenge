# Coding-Challenge-API
Small API that manages bank account statements storage and bank account balance calculations. 

## Getting started TODO

## Endpoints

Base URL `http://coding-challenge-api:8080/v1/bank-accounts-statements`

### GET retrieves bank account statements in _optional_ time range (Download CSV)
Example `http://coding-challenge-api:8080/v1/bank-accounts-statements/csv?from=<date>&to=<date>`

### POST one or many bank account statements (Upload CSV)
Example `http://coding-challenge-api:8080/v1/bank-accounts-statements/csv`

### GET calculate balance of selected bank account in _optional_ time range 
Example `http://coding-challenge-api:8080/v1/bank-accounts-statements/account/<account_number>/balance?from=<date>&to=<date>`

## Edge-cases TODO

## Unfulfilled TODO

