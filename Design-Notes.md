## Microservice Architecture Design Principles Followed
1. ### Self Contained Applications
2. ### Database design
   1. #### How Schema changes are cascaded across related records in case of change
      ##### ** <u> Event Choreography (Event-Driven Cascading) </u> **
      This is the most common pattern. Instead of a tight synchronous network call, apps react to domain events.
      Step 1: The primary service (e.g., Userservice with User detail for a user deletion) executes its local delete.
      Step 2: It publishes an event (e.g., UserDeleted or CustomerClosedAccount) to a message broker like Kafka, RabbitMQ, or AWS EventBridge.
      Step 3: The downstream services (Loans, Cards, Accounts) subscribe to this event and independently delete the associated child records (e.g., all accounts linked to the user).
      Best Practice: Use the Transactional Outbox Pattern to ensure your local delete and event publishing happen atomically, preventing ghost data if the message broker fails.
