import re

regex = r"[0-9]+([.][0-9]+)+"
version = input()

def replace_version(file_name, find, lower = True):
    with open(file_name) as f:
        lines = []

        for line in f.readlines():
            if find in (line.lower() if lower else line):
                lines.append(re.sub(regex, version, line))
            else:
                lines.append(line)

        open(file_name, "w").writelines(lines)


if re.fullmatch(regex, version):
    replace_version("README.md", "implementation")
    replace_version("gradle.properties", "version", False)
else:
    print("version doesnt match")