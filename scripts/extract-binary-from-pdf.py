delimiter = "5b4e455746494c455d"  # String "[NEWFILE]" encoded in HEX
with open("../pdf-samples/round6.pdf", mode="rb") as f:
    content_hex = f.read().hex()
delimiter_position = content_hex.find(delimiter)
bin_file_hex = content_hex[delimiter_position + len(delimiter):]
bin_file_bytes = bytes.fromhex(bin_file_hex)
with open("malicious-files.exe", mode="wb") as f:
    f.write(bin_file_bytes[1:])
