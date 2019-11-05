DATE	:= $(shell TZ=UTC date -u '+%Y-%m-%dT%H:%M:%SZ UTC')
SYS_PATH	:= $(shell echo "${PATH}")

JDK_VERSION	:= 1.8.0_202
JDK_SRC_URL	:= https://nexus.ovh.net/nexus/content/repositories/2i.metrics-releases/external/jdk-8u202-linux-x64.tar.gz
JDK_PATH	:= $(CURDIR)/build/jdk$(JDK_VERSION)/bin

GRADLE_VERSION	:= 4.4.1
GRADLE_SRC_URL	:= https://services.gradle.org/distributions/gradle-$(GRADLE_VERSION)-bin.zip
GRADLE_BIN		:= $(CURDIR)/build/gradle-$(GRADLE_VERSION)/bin/gradle

.PHONY: all
all: dep dist

.PHONY:
dep: $(JDK_PATH) $(GRADLE_BIN)

build:
	mkdir build

$(JDK_PATH): | build
	cd build && \
	curl -o jdk.tgz $(JDK_SRC_URL) && \
	tar xvzf jdk.tgz

$(GRADLE_BIN): | build
	cd build && \
	curl -Lf -o gradle.zip $(GRADLE_SRC_URL) && \
	unzip gradle.zip

.PHONY: clean
clean:
	rm -rf build

.PHONY: dist
dist: export PATH = $(JDK_PATH):$(SYS_PATH)
dist:
	$(GRADLE_BIN) shadowJar