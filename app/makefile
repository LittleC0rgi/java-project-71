.PHONY: build

build-and-run:
	./gradlew installDist
	./build/install/app/bin/app -h

help:
	./build/install/app/bin/app -h

run-dist:
	./build/install/app/bin/app

run-yaml:
	./build/install/app/bin/app ./files/yaml/file1.yaml ./files/yaml/file2.yaml

run-json:
	./build/install/app/bin/app ./files/json/file1.json ./files/json/file2.json

run-json-2:
	./build/install/app/bin/app ./files/json/file3.json ./files/json/file4.json

build:
	rm -rf build
	./gradlew build

test:
	./gradlew test
