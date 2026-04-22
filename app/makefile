.PHONY: build

build-and-run:
	./gradlew installDist
	./build/install/app/bin/app

help:
	./build/install/app/bin/app -h

run-dist:
	./build/install/app/bin/app

build:
	rm -rf build
	./gradlew build