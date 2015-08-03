ifeq ($(findstring MINGW,$(shell uname)),MINGW)
	JAVA_FILES := $(shell ./find src -type f -name '*.java')
else
	JAVA_FILES := $(shell find src -type f -name '*.java')
endif
CLASS_FILES := $(patsubst src/%.java,obj/%.class,$(JAVA_FILES))
JARFILE := TG.jar
CLASSPATH := obj:lib/jbox2d-library-2.3.1-SNAPSHOT.jar #lib/jbox2d-library-2.1.2.2.jar

.PHONY: all jar clean

all:	$(CLASS_FILES)

obj/%.class: src/%.java
	@mkdir -p obj
	javac $< -d obj -cp $(CLASSPATH)

clean:
	$(RM) -r obj
	$(RM) $(JARFILE)

jar:	all
# 	This patsubst thing is so that classes get recognized as themselves instead of as obj.Class
	jar cfm $(JARFILE) manifest.mf -C obj .
