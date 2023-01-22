# User API Dev Guide

## Building
docker compose run --service-ports mysql

## Testing
./gradlew test

## Deploying
in the readme it suggested spend 3~4 hours, I am not sure how that is possible to include deployment to some infrasture.
so not included, we can discuss deployment options in deep dive.

## Additional Information
configuration

| Configuration | Description                                          |
|---------------|------------------------------------------------------|
| user-api.minExpendableIncome        | minium expendable income = monthly salary - expendse |
| user-api.maxNubmerAccountAssociation     | max account association number                       |
