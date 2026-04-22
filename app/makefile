.PHONY: build

build-and-run:
	./gradlew installDist
	./build/install/app/bin/app -h

help:
	./build/install/app/bin/app -h

run-dist:
	./build/install/app/bin/app

build:
	rm -rf build
	./gradlew build