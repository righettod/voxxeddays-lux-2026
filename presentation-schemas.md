# Working process

> [!TIP]
> This [online tool](https://mermaid.live/) was used to create the schema.

```mermaid
sequenceDiagram
    participant S as Your servitor
    participant T as Team
    T->>T: Implement the upload service<br/>using the initial user story
    loop Until the upload service is not safe
        T->>S: Give access to a<br/>deployed version of the upload service
        S->>S: Try to find a way<br/>to upload a malicious file
        alt A way was found to<br/>upload a malicious file
            S->>T: Provide technical feedback, test content<br/>and additional validations proposal
            S->>T: Notify the team that the upload service<br/>cannot be considered as safe
            T->>T: Implement the additional validations
            T->>T: Increase the level of exasperation
            T->>S: Initiate the next round of validation
        else
            S->>T: Notify the team that the upload service<br/>can be considered as safe
        end
    end
```
