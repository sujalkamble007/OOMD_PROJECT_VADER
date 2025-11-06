/*
 * GUI application for VADER Sentiment Analysis
 */

package com.vader.sentiment.gui;

import com.vader.sentiment.analyzer.SentimentAnalyzer;
import com.vader.sentiment.analyzer.SentimentPolarities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * Simple GUI application for VADER Sentiment Analysis.
 */
public class VaderSentimentGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea inputTextArea;
    private JLabel positiveLabel;
    private JLabel negativeLabel;
    private JLabel neutralLabel;
    private JLabel compoundLabel;
    private JLabel sentimentLabel;

    /**
     * Constructor to initialize the GUI.
     */
    public VaderSentimentGUI() {
        initializeGUI();
    }

    /**
     * Initialize and set up the GUI components.
     */
    private void initializeGUI() {
        setTitle("VADER Sentiment Analysis");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setMinimumSize(new Dimension(700, 600));

        // Create main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Input panel
        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Results panel
        JPanel resultsPanel = createResultsPanel();
        mainPanel.add(resultsPanel, BorderLayout.SOUTH);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Create the input text area panel.
     *
     * @return input panel
     */
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Enter Text to Analyze"));

        inputTextArea = new JTextArea(10, 50);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        inputTextArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        inputTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(inputTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Create the results display panel.
     *
     * @return results panel
     */
    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Sentiment Analysis Results"));

        // Scores panel
        JPanel scoresPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        scoresPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Positive score
        JPanel positivePanel = createScorePanel("Positive", Color.GREEN);
        positiveLabel = (JLabel) positivePanel.getComponent(1);
        scoresPanel.add(positivePanel);

        // Negative score
        JPanel negativePanel = createScorePanel("Negative", Color.RED);
        negativeLabel = (JLabel) negativePanel.getComponent(1);
        scoresPanel.add(negativePanel);

        // Neutral score
        JPanel neutralPanel = createScorePanel("Neutral", Color.GRAY);
        neutralLabel = (JLabel) neutralPanel.getComponent(1);
        scoresPanel.add(neutralPanel);

        // Compound score
        JPanel compoundPanel = createScorePanel("Compound", Color.BLUE);
        compoundLabel = (JLabel) compoundPanel.getComponent(1);
        scoresPanel.add(compoundPanel);

        panel.add(scoresPanel, BorderLayout.CENTER);

        // Overall sentiment label
        JPanel sentimentPanel = new JPanel(new FlowLayout());
        sentimentLabel = new JLabel("Enter text and click 'Analyze' to see results");
        sentimentLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        sentimentPanel.add(sentimentLabel);
        panel.add(sentimentPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Create a score display panel.
     *
     * @param labelText label text
     * @param color color for the label
     * @return score panel
     */
    private JPanel createScorePanel(String labelText, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLoweredBevelBorder());

        JLabel label = new JLabel(labelText + ":");
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        label.setForeground(color);
        panel.add(label, BorderLayout.NORTH);

        JLabel valueLabel = new JLabel("0.000", JLabel.CENTER);
        valueLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        valueLabel.setForeground(color);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Create the button panel.
     *
     * @return button panel
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton analyzeButton = new JButton("Analyze Sentiment");
        analyzeButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        analyzeButton.setPreferredSize(new Dimension(200, 40));
        analyzeButton.addActionListener(new AnalyzeButtonListener());

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        clearButton.setPreferredSize(new Dimension(100, 40));
        clearButton.addActionListener(new ClearButtonListener());

        panel.add(analyzeButton);
        panel.add(clearButton);

        return panel;
    }

    /**
     * Action listener for the analyze button.
     */
    private class AnalyzeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = inputTextArea.getText().trim();
            if (text.isEmpty()) {
                sentimentLabel.setText("Please enter some text to analyze");
                sentimentLabel.setForeground(Color.RED);
                return;
            }

            try {
                SentimentPolarities result = SentimentAnalyzer.getScoresFor(text);

                // Update score labels
                positiveLabel.setText(String.format("%.3f", result.getPositivePolarity()));
                negativeLabel.setText(String.format("%.3f", result.getNegativePolarity()));
                neutralLabel.setText(String.format("%.3f", result.getNeutralPolarity()));
                compoundLabel.setText(String.format("%.4f", result.getCompoundPolarity()));

                // Determine overall sentiment
                double compound = result.getCompoundPolarity();
                String sentiment;
                Color sentimentColor;

                if (compound >= 0.05) {
                    sentiment = "POSITIVE";
                    sentimentColor = Color.GREEN;
                } else if (compound <= -0.05) {
                    sentiment = "NEGATIVE";
                    sentimentColor = Color.RED;
                } else {
                    sentiment = "NEUTRAL";
                    sentimentColor = Color.GRAY;
                }

                sentimentLabel.setText("Overall Sentiment: " + sentiment);
                sentimentLabel.setForeground(sentimentColor);

            } catch (Exception ex) {
                sentimentLabel.setText("Error analyzing text: " + ex.getMessage());
                sentimentLabel.setForeground(Color.RED);
            }
        }
    }

    /**
     * Action listener for the clear button.
     */
    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            inputTextArea.setText("");
            positiveLabel.setText("0.000");
            negativeLabel.setText("0.000");
            neutralLabel.setText("0.000");
            compoundLabel.setText("0.000");
            sentimentLabel.setText("Enter text and click 'Analyze' to see results");
            sentimentLabel.setForeground(Color.BLACK);
        }
    }

    /**
     * Main method to launch the GUI application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Set system look and feel for better appearance
                    javax.swing.UIManager.setLookAndFeel(
                        javax.swing.UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    // Use default look and feel if system L&F fails
                }

                VaderSentimentGUI gui = new VaderSentimentGUI();
                gui.setVisible(true);
            }
        });
    }
}

