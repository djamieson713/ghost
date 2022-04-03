srcdir := src
libdir := lib
bindir := bin
jardir := bin
jarName := AIalgorithms.jar
mainpackage := myCode
manifest := resources/META-INF/GhostProject.mf
mainfile := GhostSkeleton
mainargs:= "C:\Users\djamieson\OneDrive\CIT 239\HomeWork\GhostFinal";

sourcefiles := $(shell find $(srcdir) -name '*.java')

classfiles  = $(sourcefiles:.java=.class)

classpath = "$(libdir)/*"\;"$(bindir)"\;"$(srcdir)"

VPATH := $(shell find src -type d -print | tr '\012' ':' | sed 's/:$$//')

vpath %.jar $(libdir)/*
vpath %.class $(bindir)/*

destination = \
$(bindir)

build: $(classfiles)

%.class: %.java
	javac -g -d $(destination) -classpath $(classpath) $< 

run: build
	java -cp $(classpath):. $(mainpackage).$(mainfile) $(mainargs)

archive: build
	jar cfm $(jarName) $(manifest) @classes.list

inspect:
	jar tf $(jarName) 

clean:
	rm -R $(bindir) 