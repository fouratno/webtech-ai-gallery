# Potential Bugs and Risks

## Database configuration inconsistencies
- `src/main/resources/application-prod.properties` expects environment variables `DB_URL`, `DB_USER`, and `DB_PASSWORD`, but the repository readme and prior requirements reference `DB_USERNAME`. Using the wrong variable name will prevent the application from obtaining database credentials in production, leading to failed connections at startup.
- The default `src/main/resources/application.properties` still configures an in-memory H2 database. If `SPRING_PROFILES_ACTIVE=prod` is not set in production, the service will start against H2 instead of Postgres, so data will not persist and tables will be recreated on each restart.

## API/runtime behaviors
- The frontend form submission in `frontend/src/components/ConceptList.vue` does not validate or guard against empty strings beyond the HTML `required` attribute. If the backend adds validation constraints later (e.g., @NotBlank), the current UI will surface only generic error messages without field-level guidance, which could be confusing to users.
