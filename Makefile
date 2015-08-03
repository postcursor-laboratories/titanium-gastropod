ifeq ($(findstring MINGW,$(shell uname)),MINGW)
	JAVA_FILES := $(shell ./find src -type f -name '*.java')
	CLASSPATH := "src;lib/jbox2d-library-2.3.1-SNAPSHOT.jar;lib/testbed.jar"
else
	JAVA_FILES := $(shell find src -type f -name '*.java')
	CLASSPATH := "src:lib/jbox2d-library-2.3.1-SNAPSHOT.jar:lib/testbed.jar"
endif
CLASS_FILES := $(patsubst src/%.java,obj/%.class,$(JAVA_FILES))
JARFILE := TG.jar

.PHONY: all jar clean

all:	$(CLASS_FILES)

obj/%.class: src/%.java
	@mkdir -p obj
	javac $< -d obj -cp $(CLASSPATH)

clean:
	$(RM) -r obj
	$(RM) $(JARFILE)

jar:	all
	jar cfm $(JARFILE) manifest.mf -C obj .

run:	jar
	java -jar TG.jar