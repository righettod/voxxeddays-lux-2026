import requests
import sys
import base64

test_file_name = sys.argv[1]
test_file_path = f"../pdf-samples/{test_file_name}"
with open(test_file_path, "rb") as file:
    binary_data = file.read()
    base64_bytes = base64.b64encode(binary_data)
    base64_string = base64_bytes.decode("utf-8")
content = {"name": test_file_name, "base64EncodedContent": base64_string}
response = requests.post("http://upload-service.local:8080/files", json=content)
status = "REJECTED"
if response.status_code == 201:
    status = "ACCEPTED"
print(f"ℹ️ File {status}: HTTP response code {response.status_code} received.")
