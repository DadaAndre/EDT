#!/bin/sh

showCommand() {
	echo "Usage : $0 <clean|build|run|test|javadoc|deploy>" 1>&2
	exit 1
}

buildProject() {
	echo '>build'

	if [ ! -d 'build' ]; then
		mkdir build
	fi

	javac -cp 'src:libs/scheduleio.jar' -d build src/edt/*.java -Xlint
}

cleanProject() {
	echo '>clean'

	if [ -d 'build' ]; then
		rm -rf build/
	fi

	if [ -d 'javadoc' ]; then
		rm -rf javadoc
	fi
}

createJavadoc() {
	echo '>javadoc'

	if [ ! -d 'javadoc' ]; then
		mkdir javadoc
	fi

	javadoc -subpackages edt -private -d javadoc -cp src
}

runProject() {
	buildProject

	echo '>run'

	java -cp 'build:libs/scheduleio.jar' edt.InteractiveScheduling
}

testProject() {
	buildProject

	echo '>test'

	java -cp 'build:libs/scheduleio.jar' edt.Test
}

deployProject() {
	echo '>deploy'

	zip fil_rouge_poo_groupe_40.zip -r src README.md
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
	elif [ $1 = "deploy" ]; then
		deployProject
	else
		showCommand
	fi

	shift
done
