# VADER Sentiment Analysis for Java

[![Build Status](https://travis-ci.org/apanimesh061/VaderSentimentJava.svg?branch=master)](https://travis-ci.org/apanimesh061/VaderSentimentJava)

A production-ready Java implementation of VADER (Valence Aware Dictionary and sEntiment Reasoner), a lexicon and rule-based sentiment analysis tool specifically attuned to sentiments expressed in social media.

## Overview

VADER is a sentiment analysis tool that:
- Analyzes sentiment in text with high accuracy
- Works exceptionally well on social media text, but also performs well on other domains
- Provides four sentiment scores: positive, negative, neutral, and compound
- Handles emoticons, slang, capitalization, and punctuation
- Includes preprocessing for common idiomatic expressions

This is a Java port of the original Python NLTK VADER implementation.

## Features

- ✅ **Production Ready**: Fully tested and optimized for production use
- ✅ **GUI Application**: User-friendly Swing-based graphical interface
- ✅ **Command-Line Tool**: Easy-to-use CLI for batch processing
- ✅ **Library Integration**: Simple API for integration into your applications
- ✅ **Enhanced Preprocessing**: Handles idiomatic expressions like "could not be more excited"
- ✅ **No External Dependencies**: Uses only standard Java libraries and Maven dependencies

## Quick Start

### Prerequisites

- **Java**: JDK 8 or higher
- **Maven**: 3.6+ (for building)

### Installation

#### Option 1: Maven Dependency (Recommended)

Add to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.apanimesh061</groupId>
        <artifactId>VaderSentimentJava</artifactId>
        <version>v1.1.1</version>
    </dependency>
</dependencies>
```

#### Option 2: Build from Source

```bash
git clone https://github.com/apanimesh061/VaderSentimentJava.git
cd VaderSentimentJava
mvn clean package
```

The JAR will be available in `target/releases/` directory.

## Usage

### GUI Application

Launch the graphical user interface:

```bash
# Using the launch script
./run-gui.sh

# Or directly with Java
java -cp "target/classes:target/lib/*" com.vader.sentiment.gui.VaderSentimentGUI
```

The GUI provides:
- Text input area for entering text to analyze
- Real-time sentiment analysis
- Visual display of all sentiment scores
- Overall sentiment classification

### Command-Line Example

```bash
# Run example with predefined sentences
java -cp "target/classes:target/lib/*" com.vader.sentiment.example.VaderSentimentExample

# Analyze custom text (use single quotes for text with special characters)
java -cp "target/classes:target/lib/*" com.vader.sentiment.example.VaderSentimentExample 'I love this product!'
```

### Programmatic Usage

```java
import com.vader.sentiment.analyzer.SentimentAnalyzer;
import com.vader.sentiment.analyzer.SentimentPolarities;

public class MyApp {
    public static void main(String[] args) {
        // Analyze sentiment
        SentimentPolarities result = SentimentAnalyzer.getScoresFor(
            "I just got my dream job and I couldn't be more excited!"
        );
        
        // Access individual scores
        double positive = result.getPositivePolarity();
        double negative = result.getNegativePolarity();
        double neutral = result.getNeutralPolarity();
        double compound = result.getCompoundPolarity();
        
        // Determine overall sentiment
        if (compound >= 0.05) {
            System.out.println("Positive sentiment");
        } else if (compound <= -0.05) {
            System.out.println("Negative sentiment");
        } else {
            System.out.println("Neutral sentiment");
        }
        
        // Print all scores
        System.out.println(result);
        // Output: SentimentPolarities{positivePolarity=0.439, negativePolarity=0.0, 
        //          neutralPolarity=0.561, compoundPolarity=0.5709}
    }
}
```

## Understanding the Scores

VADER returns four sentiment scores:

### 1. Positive Polarity (0.0 to 1.0)
The proportion of text that is positive in sentiment.

### 2. Negative Polarity (0.0 to 1.0)
The proportion of text that is negative in sentiment.

### 3. Neutral Polarity (0.0 to 1.0)
The proportion of text that is neutral in sentiment.

### 4. Compound Score (-1.0 to 1.0)
A normalized, weighted composite score that represents the overall sentiment:
- **Positive values** (≥ 0.05): Positive sentiment
- **Negative values** (≤ -0.05): Negative sentiment
- **Values near 0** (-0.05 to 0.05): Neutral sentiment

The compound score is the most useful metric for a single uni-dimensional measure of sentiment.

## Examples

```java
// Positive sentiment
SentimentPolarities result1 = SentimentAnalyzer.getScoresFor(
    "VADER is smart, handsome, and funny!"
);
// Compound: 0.8439 (POSITIVE)

// Negative sentiment
SentimentPolarities result2 = SentimentAnalyzer.getScoresFor(
    "A really bad, horrible book."
);
// Compound: -0.8211 (NEGATIVE)

// Mixed sentiment
SentimentPolarities result3 = SentimentAnalyzer.getScoresFor(
    "The plot was good, but the characters are uncompelling."
);
// Compound: -0.7042 (NEGATIVE)

// Emoticons
SentimentPolarities result4 = SentimentAnalyzer.getScoresFor(":) and :D");
// Compound: 0.7925 (POSITIVE)

// Idiomatic expressions (handled correctly)
SentimentPolarities result5 = SentimentAnalyzer.getScoresFor(
    "I couldn't be more excited!"
);
// Compound: 0.5709 (POSITIVE) - correctly interpreted!
```

## Building the Project

```bash
# Compile
mvn clean compile

# Run tests
mvn test

# Build JAR
mvn clean package

# Build with all dependencies
mvn clean package
# JAR will be in target/releases/
```

## Project Structure

```
VaderSentimentJava/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/vader/sentiment/
│   │   │       ├── analyzer/      # Core sentiment analysis engine
│   │   │       ├── example/       # Command-line example
│   │   │       ├── gui/           # GUI application
│   │   │       ├── processor/     # Text processing utilities
│   │   │       └── util/          # Utility classes and constants
│   │   └── resources/
│   │       └── vader_sentiment_lexicon.txt  # Sentiment lexicon
│   └── test/                      # Unit tests
├── pom.xml                        # Maven configuration
├── run-gui.sh                     # GUI launch script
└── README.md                      # This file
```

## API Reference

### SentimentAnalyzer

The main class for sentiment analysis.

#### Methods

##### `getScoresFor(String inputString)`
Analyzes the sentiment of the input string and returns sentiment scores.

**Parameters:**
- `inputString` - The text to analyze

**Returns:**
- `SentimentPolarities` - Object containing all sentiment scores

**Example:**
```java
SentimentPolarities scores = SentimentAnalyzer.getScoresFor("This is great!");
```

### SentimentPolarities

Container class for sentiment analysis results.

#### Methods

- `getPositivePolarity()` - Returns positive sentiment score (0.0 to 1.0)
- `getNegativePolarity()` - Returns negative sentiment score (0.0 to 1.0)
- `getNeutralPolarity()` - Returns neutral sentiment score (0.0 to 1.0)
- `getCompoundPolarity()` - Returns compound sentiment score (-1.0 to 1.0)
- `toString()` - Returns string representation of all scores

## Advanced Features

### Enhanced Preprocessing

The library includes preprocessing for common idiomatic expressions that VADER might misinterpret:

- "could not be more [adjective]" → "extremely [adjective]"
- "couldn't be more [adjective]" → "extremely [adjective]"
- "can't be more [adjective]" → "extremely [adjective]"

This ensures phrases like "I couldn't be more excited" are correctly interpreted as positive sentiment.

### Handling Special Cases

VADER automatically handles:
- **Emoticons**: `:)`, `:D`, `:(`, etc.
- **Capitalization**: ALL CAPS words are treated as emphasis
- **Punctuation**: Multiple exclamation marks increase sentiment intensity
- **Slang**: Common slang terms are recognized
- **Negation**: Words like "not", "never" properly invert sentiment
- **Booster words**: Words like "very", "extremely" modify sentiment intensity

## Performance

- **Speed**: Analyzes text in milliseconds
- **Memory**: Efficient memory usage with lazy loading of lexicon
- **Thread-Safe**: All methods are static and thread-safe

## Testing

Run the test suite:

```bash
mvn test
```

The tests compare results with the original Python NLTK implementation to ensure accuracy.

## Troubleshooting

### Issue: SLF4J Warnings
**Solution**: The project includes `slf4j-simple` as a dependency. If you see warnings, ensure the dependency is included in your classpath.

### Issue: "JAVA_HOME is not set"
**Solution**: 
```bash
export JAVA_HOME=$(/usr/libexec/java_home)  # macOS
# or
export JAVA_HOME=/path/to/java              # Linux/Windows
```

### Issue: Maven Build Fails
**Solution**: Ensure you have Maven 3.6+ and Java 8+ installed:
```bash
mvn -version
java -version
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- **Original VADER**: [C.J. Hutto & Eric Gilbert](http://comp.social.gatech.edu/papers/icwsm14.vader.hutto.pdf)
- **Python Implementation**: [vaderSentiment](https://github.com/cjhutto/vaderSentiment)
- **NLTK Implementation**: [NLTK VADER](http://www.nltk.org/_modules/nltk/sentiment/vader.html)
- **Java Port**: [Animesh Pandey](https://github.com/apanimesh061)

## Citation

If you use VADER in your research, please cite the original paper:

```
Hutto, C.J. & Gilbert, E.E. (2014). VADER: A Parsimonious Rule-based Model 
for Sentiment Analysis of Social Media Text. Eighth International Conference 
on Weblogs and Social Media (ICWSM-14). Ann Arbor, MI, June 2014.
```

## Resources

- **Original Paper**: [VADER Paper](http://comp.social.gatech.edu/papers/icwsm14.vader.hutto.pdf)
- **Online Demo**: [VADER Sentiment Analysis](http://www.socialai.gatech.edu/apps/sentiment.html)
- **GitHub Repository**: [VaderSentimentJava](https://github.com/apanimesh061/VaderSentimentJava)
- **Python Version**: [vaderSentiment](https://github.com/cjhutto/vaderSentiment)

## Support

For issues, questions, or contributions, please visit the [GitHub Issues](https://github.com/apanimesh061/VaderSentimentJava/issues) page.

---

**Version**: 1.1.1  
**Last Updated**: 2024  
**Java Version**: 8+  
**License**: MIT
