# CS3219 Assignment 1

To compile:

```bash
cd src
javac kwic/KWIC.java
```

In the same location, run the program with:

```bash
java kwic.KWIC -i [path-to-input-file]
```

**Note:** You can also open the project with [IntelliJ](https://www.jetbrains.com/idea/), where there is an existing run configuration that runs with `-i ./input.txt`.

The options are:

```
-input, -i          Path to input file
-output, -o         [optional] Path to output file, defaults to ./output.txt
-ignore, -g         [optional] Path to words to ignore file, defaults to no words to ignore
-architecture, -a   [optional] Type of architecture to use: [adt/subroutine], defaults to adt
```