JAVA_FILES := $(shell find src -type f -name '*.java')
CLASS_FILES := $(patsubst src/%.java,obj/%.class,$(JAVA_FILES))
JARFILE := TG.jar

.PHONY: all jar clean

all:	$(CLASS_FILES)

obj/%.class: src/%.java
	@mkdir -p obj
	javac $< -d obj -cp obj

clean:
	$(RM) -r obj
	$(RM) $(JARFILE)

jar:	all
# 	This patsubst thing is so that classes get recognized as themselves instead of as obj.Class
	jar cfm $(JARFILE) manifest.mf -C obj $(patsubst obj/%,%,$(CLASS_FILES))
