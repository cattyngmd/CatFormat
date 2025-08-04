import re

version = input()

if re.fullmatch(r"[0-9]+([.][0-9]+)+", version):
    with open("README.md") as f:
        lines = []

        for line in f.readlines():
            if "implementation" in line.lower():
                print("replace")
                lines.append(re.sub(r"[0-9]+([.][0-9]+)+", version, line))
            else:
                lines.append(line)

        open("README.md", "w").writelines(lines)

    with open("gradle.properties") as f:
        lines = []

        for line in f.readlines():
            if line.startswith("version"):
                lines.append(re.sub(r"[0-9]+([.][0-9]+)+", version, line))
            else:
                lines.append(line)

        open("gradle.properties", "w").writelines(lines)
else:
    print("version doesnt match")