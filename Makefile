JAVA_FILES := $(shell find src -type f -name '*.java')
CLASS_FILES := $(patsubst src/%.java,obj/%.class,$(JAVA_FILES))

JARFILE := TG.jar

.PHONY: all jar clean

all:	$(CLASS_FILES)

obj/%.class: src/%.java
	javac $< -d obj

clean:
	$(RM) obj/*

jar:	all			# TODO
	jar cf $(JARFILE) obj/*.class
