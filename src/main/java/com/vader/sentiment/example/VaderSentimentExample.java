/*
 * Example class to demonstrate VADER Sentiment Analysis usage
 */

package com.vader.sentiment.example;

import com.vader.sentiment.analyzer.SentimentAnalyzer;
import com.vader.sentiment.analyzer.SentimentPolarities;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple example demonstrating how to use VADER Sentiment Analyzer.
 */
public class VaderSentimentExample {

    /**
     * Main method to run the example.
     *
     * @param args command line arguments (optional text to analyze)
     */
    public static void main(String[] args) {
        System.out.println("=== VADER Sentiment Analysis Example ===\n");

        // Example sentences to analyze
        List<String> sentences = new ArrayList<String>() {
                {
                    add("VADER is smart, handsome, and funny.");
                    add("VADER is smart, handsome, and funny!");
                    add("VADER is very smart, handsome, and funny.");
                    add("VADER is VERY SMART, handsome, and FUNNY.");
                    add("VADER is VERY SMART, handsome, and FUNNY!!!");
                    add("VADER is VERY SMART, really handsome, and INCREDIBLY FUNNY!!!");
                    add("The book was good.");
                    add("The book was kind of good.");
                    add("The plot was good, but the characters are uncompelling and the dialog is not great.");
                    add("A really bad, horrible book.");
                    add("At least it isn't a horrible book.");
                    add(":) and :D");
                    add("Today sux");
                    add("Today sux!");
                    add("Today SUX!");
                    add("Today kinda sux! But I'll get by, lol");
                }
            };

        // Analyze each sentence
        for (String sentence : sentences) {
            System.out.println("Text: " + sentence);
            SentimentPolarities sentimentPolarities = SentimentAnalyzer.getScoresFor(sentence);
            System.out.println("Sentiment Scores:");
            System.out.println("  Positive: " + sentimentPolarities.getPositivePolarity());
            System.out.println("  Negative: " + sentimentPolarities.getNegativePolarity());
            System.out.println("  Neutral:  " + sentimentPolarities.getNeutralPolarity());
            System.out.println("  Compound: " + sentimentPolarities.getCompoundPolarity());
            System.out.println();
        }

        // Interactive mode - analyze user input
        if (args.length > 0) {
            System.out.println("\n=== Analyzing provided text ===");
            String inputText = String.join(" ", args);
            System.out.println("Text: " + inputText);
            SentimentPolarities result = SentimentAnalyzer.getScoresFor(inputText);
            System.out.println(result);
        }
    }
}

