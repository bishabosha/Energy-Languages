# Scala CLI setup: 
# curl -sSLf https://virtuslab.github.io/scala-cli-packages/scala-setup.sh | sh

# cloning jRAPL repo might take a long time
build-jrapl:
	(test -d "jRAPL" && git -C jRAPL pull) || git clone https://github.com/aservet1/jRAPL
	make -C jRAPL
	cp jRAPL/src/java/target/jRAPL-1.0.jar ./jRAPL-1.0.jar

compile:
	scalac sRAPL.scala -d sRAPL.jar -cp ./jRAPL-1.0.jar
	
clean:
	rm -rf jRAPL