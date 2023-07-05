package com.bitflip.sanolagani.serviceimpl;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.*;
import java.util.List;
import java.util.Arrays;
import java.util.Properties;
// import org.springframework.stereotype.Service;


public class FilterFinancialStatementsImpl{


    public void deleteNonFinancialDocuments(Integer companyID){
        File companyDir = new File("src/main/resources/output/"+companyID);
        List<File> files = Arrays.asList(companyDir.listFiles());
        for(File file: files){
            System.out.println(file.getName());
        }
    }

    public void getAllFinancialStatements(Integer companyID) throws IOException {
        File companyDir = new File("src/main/resources/output/"+companyID);
        List<File> files = Arrays.asList(companyDir.listFiles());
        BufferedReader reader = null;
        for(File file: files){
            String text = "";
            try {
                reader = new BufferedReader(new FileReader(companyDir + "/"+file.getName()));
                while (reader.readLine() != null)
                // checkDocumentsContainingFinancialStatements(reader.readLine());
                    text += reader.readLine() + "\n";
                    break;
                } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                reader.close();
                checkDocumentsContainingFinancialStatements(text);
                System.out.println(text);
            }
        }
    }

    private void checkDocumentsContainingFinancialStatements(String s) {
        //setup pipeline properties
        Properties props = new Properties();
        //set the list of annotators to run
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, depparse");
        props.setProperty("coref.algorithm", "neural");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument document = new CoreDocument(s);
        pipeline.annotate(document);

    }

    
    public static void main(String[] args) throws IOException {
        FilterFinancialStatementsImpl filterFinancialStatementsImpl = new FilterFinancialStatementsImpl();
        filterFinancialStatementsImpl.getAllFinancialStatements(10);
    }
}
