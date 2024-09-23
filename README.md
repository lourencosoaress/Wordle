# Wordle Game (Java Edition)

## Overview
This is a simple Wordle-like game implemented in Java. Players are tasked with guessing a randomly selected 5-letter word from a pool of **400 different words**. The goal is to guess the correct word within **6 attempts**.

For each guess, the program provides feedback:
- Correct letters in the correct position are highlighted in **green**.
- Letters present in the word but in the wrong position are highlighted in **yellow**.
- Letters not present in the word are shown as-is.

## Features
- **Over 400 different words** to guess, ensuring that no two games are the same.
- **Six attempts** to guess the correct word.
- Uses **color formatting** in the terminal for visual feedback.
- Random word selection powered by Java's `SecureRandom` for added security.
- The game runs entirely in the console, making it lightweight and easy to use.

## Requirements
- Java 8 or higher 
