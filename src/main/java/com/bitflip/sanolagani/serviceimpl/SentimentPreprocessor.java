package com.bitflip.sanolagani.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.Feedback;
import com.bitflip.sanolagani.repository.CompanyRepo;
import com.bitflip.sanolagani.repository.FeedbackRepo;

import java.util.List;
import java.util.ArrayList;

import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;


import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import java.util.Properties;



@Service
public class SentimentPreprocessor {
	
	@Autowired
	CompanyRepo c_repo;
	@Autowired
	FeedbackRepo f_repo;
	
    private final StanfordCoreNLP pipeline;

    public SentimentPreprocessor() {
       
    	Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit,pos, lemma, parse, sentiment");
        this.pipeline = new StanfordCoreNLP(props);

    }
	
	public List<Company> getCompaniesWithGoodSentiment() {
		List<Company> companies= new ArrayList<>();
		List<Feedback> feedbacks = f_repo.findAll();
		for(Feedback feedback: feedbacks) {
			int id = feedback.getCompany().getId();
			Company company = c_repo.getById(id);
			companies.add(company);
		}
		
        Map<Company, Double> sentimentScores = calculateSentimentScores(companies, feedbacks);

        // Filter companies with good sentiment scores
        List<Company> companiesWithGoodSentiment = filterCompaniesWithGoodSentiment(sentimentScores);
        
	   
	    return companiesWithGoodSentiment;
	}
	
	
	 public Map<Company, Double> calculateSentimentScores(List<Company> companies, List<Feedback> feedbacks) {
	        Map<Company, Double> sentimentScores = new HashMap<>();

	        // Iterate over each company
	        for (Company company : companies) {
	            double totalScore = 0.0;
	            int count = 0;
	            
	            
	            for (Feedback feedback : feedbacks) {
	        
	                if (feedback.getCompany().getId()== company.getId()) {
	                	String text = preprocessText(feedback.getFeedbacktext());
	                    double sentimentScore = performSentimentAnalysis(text);

	                    totalScore += sentimentScore;
	                    count++;
	                }
	            }
	            
	            double averageScore = (count > 0) ? totalScore / count : 0.0;
	
	            sentimentScores.put(company, averageScore);
	        }
	       
	        return sentimentScores;
	    }
	        
	        
	    public double performSentimentAnalysis(String feedbackText) {
	      
	        double sentimentScore=0.0;
	        Annotation annotation = new Annotation(feedbackText);
	        pipeline.annotate(annotation);
	        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
	           sentimentScore = sentence.get(SentimentCoreAnnotations.SentimentClass.class).equalsIgnoreCase("positive") ? 1.0 : -1.0;
	           System.out.println(sentimentScore);
	           return sentimentScore;
	        }
	        return sentimentScore;
	    } 
	        
	    private List<Company> filterCompaniesWithGoodSentiment(Map<Company, Double> sentimentScores) {
	        List<Company> companiesWithGoodSentiment = new ArrayList<>();
	        
	        // Define the threshold for good sentiment
	        double threshold = 0.7;
	        
	        // Iterate over the sentiment scores
	        for (Map.Entry<Company, Double> entry : sentimentScores.entrySet()) {
	            Company company = entry.getKey();
	            double sentimentScore = entry.getValue();
	            
	            // Check if the sentiment score is above the threshold
	            if (sentimentScore >= threshold) {
	                companiesWithGoodSentiment.add(company);
	            }
	        }
	 
	        return companiesWithGoodSentiment;
	    }

	
	        
	        

	        
	        

    public String preprocessText(String text) {
       
        text = text.replaceAll("[^a-zA-Z ]", "");
        text = text.toLowerCase();
        text = text.trim().replaceAll(" +", " ");
        text = removeStopwords(text);

        return text;
    }

    private String removeStopwords(String text) {
        String[] stopwords = {"a", "an", "the", "in", "on", "at", "is", "are", "and", "but", "."};

        // Create a regex pattern of all stopwords
        String pattern = "\\b(" + String.join("|", stopwords) + ")\\b";

        // Remove stopwords from the text
        text = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(text).replaceAll("");

        return text;
    }
}


