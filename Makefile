APPNAME  := $(shell basename ${PWD})
REGISTRY := $(if $(REGISTRY) $(REGISTRY),192.168.0.202:5000)
VERSION  := $(shell sed '/^  <version>/!d;s/.*>\([^<]*\)<.*/\1/' pom.xml)
GITDIFF  := $(shell git status --porcelain)
TAG      := $(shell git describe --tags --always --dirty="-dev")
IMGNAME  := $(APPNAME)$(if $(TAG) ,:$(TAG))
MVN      := mvn
JARFILE  := target/$(APPNAME)-$(VERSION).jar
SKIPTEST := true
CONFLIST := spring,logging,ora11g,red50,$(APPNAME)
PROFILE  := dev
MVNFLAG  := -Dmaven.test.skip=$(SKIPTEST)
MVNFLAG  += -Dspring.application.name=$(CONFLIST)
MVNFLAG  += -Dspring.profiles.active=$(PROFILE)
MVNFLAG  += -Dspring.cloud.config.profile=$(PROFILE)
MVNFLAG  += -Dspring.cloud.config.name=$(CONFLIST)
MVNFLAG  += -Dspring.cloud.config.uri=$(PEIZHI_URL)
# MVNFLAG  += -Dorg.slf4j.simpleLogger.defaultLogLevel=WARN

# Source Code Formatting
GJFJAR   := ~/.emacs.d/resource/google-java-format-1.7-all-deps.jar
JAVASRCS := $(shell find src -iname "*.java")
JAVADIFF := $(shell git status --porcelain | grep java | cut -c4- | sort | uniq)

################################################################################
#  _     ___ _____ _____ ______   ______ _     _____
# | |   |_ _|  ___| ____/ ___\ \ / / ___| |   | ____|
# | |    | || |_  |  _|| |    \ V / |   | |   |  _|
# | |___ | ||  _| | |__| |___  | || |___| |___| |___
# |_____|___|_|   |_____\____| |_| \____|_____|_____|
################################################################################
all: clean run
	echo 'Done all things'

run:
	$(MVN) spring-boot:run

compile:
	$(MVN) $(MVNFLAG) compile

clean:
	$(MVN) $(MVNFLAG) clean

test:
	$(MVN) $(MVNFLAG) test

package: clean compile
	$(MVN) $(MVNFLAG) package

copy:
	[ -f $(JARFILE) ] && cp $(JARFILE) assets/app.jar

build: package copy
	docker build -t $(IMGNAME) .

push: build
	[ "$(REGISTRY)" != "" ] && \
	docker tag $(IMGNAME) $(REGISTRY)/$(IMGNAME) && \
	docker push $(REGISTRY)/$(IMGNAME) && \
	docker rmi $(REGISTRY)/$(IMGNAME)

################################################################################
#  __  __    _    _   _    _    ____ _____ __  __ _____ _   _ _____
# |  \/  |  / \  | \ | |  / \  / ___| ____|  \/  | ____| \ | |_   _|
# | |\/| | / _ \ |  \| | / _ \| |  _|  _| | |\/| |  _| |  \| | | |
# | |  | |/ ___ \| |\  |/ ___ \ |_| | |___| |  | | |___| |\  | | |
# |_|  |_/_/   \_\_| \_/_/   \_\____|_____|_|  |_|_____|_| \_| |_|
################################################################################
ready:
	[ "$(GITDIFF)" = " M pom.xml" ]

tag: ready
	git commit -am 'v$(VERSION)' && git tag v$(VERSION)

prettify:
	parallel -i java -jar $(GJFJAR) -i {} -- $(JAVADIFF)

format:
	parallel -i java -jar $(GJFJAR) -i {} -- $(JAVASRCS)

tree:
	$(MVN) $(MVNFLAG) "dependency:tree"

list:
	$(MVN) $(MVNFLAG) "dependency:list"

javadoc:
	$(MVN) $(MVNFLAG) "javadoc:javadoc"

sources:
	$(MVN) "dependency:sources" && $(MVN) "dependency:resolve" -Dclassifier=javadoc

# codetta: start
# sed '/^[0-9A-Za-z]*:/!d;s/:.*$//' Makefile | sort | tr '\n' ' ' | xargs -I {} printf ".PHONY:\n\t{}\n"
# codetta: output
.PHONY:
	all build clean compile copy format javadoc list package prettify push ready run sources tag test tree
# codetta: end
