JARFILE := TG.jar

.PHONY: all jar

all:	src/*.java
	javac $< -d bin

jar:	all			# TODO
	jar cf $(JARFILE) bin/*.class
