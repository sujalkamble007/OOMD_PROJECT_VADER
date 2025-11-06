#!/bin/bash
# Script to run the VADER Sentiment Analysis GUI

cd "$(dirname "$0")"

# Check if project is compiled
if [ ! -d "target/classes" ]; then
    echo "Project not compiled. Compiling now..."
    mvn compile
fi

# Run the GUI
java -cp "target/classes:target/lib/*" com.vader.sentiment.gui.VaderSentimentGUI

