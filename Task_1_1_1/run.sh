javac src/main/java/ru/nsu/kuzminov/HeapSort.java -d ./build
javadoc -d build/docs/javadoc -sourcepath src/main/java -subpackages ru.nsu.kuzminov
java -cp ./build ru.nsu.kuzminov.Main