# Project Goals
* Develop a Java application for replacing strings within a file.
* Get experience with an agile, test-driven process.

# Specification of Encode Utility
Developing a simple command-line utility called encode using Java.
* NAME:
> encode - encodes words in a file.
* SYNOPSIS
> encode OPT <filename> where OPT can be zero or more of
> * -n [integer]
> * (-r | -l) [integer]
> * -c [string]
> * -d [integer]

# Examples
* encode -c “aeiou” -l 3 file1.txt
> Changes all occurrences of characters ‘a’, ‘e’, ‘i’, ‘o’ and ‘u’ to uppercase, and all occurrences of characters  ‘A’, ‘E’, ‘I’, ‘O’ and ‘U’ to lowercase. Then, rotates all characters 3 spaces left in each line, with the first 3 characters ending up at the end of their line.
* encode -d 1 file1.txt
> Deletes all but the first occurence of each character in the file.
