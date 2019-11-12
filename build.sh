#!/bin/sh

showCommand() {
	echo "Usage : $0 <build|run|clean|javadoc|test>" 1>&2
	exit 1
}

cleanProject() {
	if [ -d 'build' ]; then
		rm -rf build/
	fi

	if [ -d 'javadoc' ]; then
		rm -rf javadoc
	fi
}

buildProject() {
	if [ ! -d 'build' ]; then
		mkdir build
	fi

	javac -cp 'src:libs/scheduleio.jar' -d build src/edt/*.java -Xlint
}

runProject() {
	buildProject

	java -cp 'build:libs/scheduleio.jar' edt.InteractiveScheduling
}

testProject() {
	buildProject

	java -cp 'build:libs/scheduleio.jar' edt.Test
}

createJavadoc() {
	if [ ! -d 'javadoc' ]; then
		mkdir javadoc
	fi

	javadoc -subpackages edt -private -d javadoc -cp src
}

if [ $# -lt 1 ]; then
	showCommand
fi

while [ ! -z $1 ]
do
	if [ $1 = "build" ]; then
		buildProject
	elif [ $1 = "clean" ]; then
		cleanProject
	elif [ $1 = "javadoc" ]; then
		createJavadoc
	elif [ $1 = "run" ]; then
		runProject
	elif [ $1 = "test" ]; then
		testProject
	else
		showCommand
	fi

	shift
done
