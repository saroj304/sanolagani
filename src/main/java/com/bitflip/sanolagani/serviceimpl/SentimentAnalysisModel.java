package com.bitflip.sanolagani.serviceimpl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.pipeline.CoreNLPProtos.Sentiment;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.*;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;

@Service
public class SentimentAnalysisModel {
	
	private StanfordCoreNLP pipeline;
	
	 public SentimentAnalysisModel() {
	        // Initialize the sentiment analysis pipeline
	        Properties props = new Properties();
	        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
	        pipeline = new StanfordCoreNLP(props);
	    }
	 
	 public Sentiment analyzeSentiment(String text) {
	        // Perform sentiment analysis on the given text
	        Annotation annotation = new Annotation(text);
	        pipeline.annotate(annotation);
	        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

	        Sentiment overallSentiment = Sentiment.NEUTRAL;
	        for (CoreMap sentence : sentences) {
	            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
	            Sentiment sentiment = Sentiment.valueOf(RNNCoreAnnotations.getPredictedClass(tree));
	            if (sentiment.ordinal() > overallSentiment.ordinal()) {
	                overallSentiment = sentiment;
	            }
	        }
	        return overallSentiment;
	    }
	}

