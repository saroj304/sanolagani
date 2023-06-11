package com.bitflip.sanolagani.document.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Controller
public class TablesController {
        private List<Map<String, String>> convertTsvToJson(String filePath) throws IOException {
            List<Map<String, String>> jsonList = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                String[] headers = null;
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split("\t");

                    if (headers == null) {
                        headers = values;
                    } else {
                        Map<String, String> jsonMap = new LinkedHashMap<>();
                        for (int i = 0; i < headers.length; i++) {
                            String header = headers[i];
                            String value = (i < values.length) ? values[i] : "";
                            jsonMap.put(header, value);
                        }
                        jsonList.add(jsonMap);
                    }
                }
            }

            return jsonList;
        }

        @GetMapping("/table/${firm}")
        public String getTable(@RequestParam String firm, @org.jetbrains.annotations.NotNull Model model) throws IOException {
            String filePath = "src/main/resources/output/${firm}";
            List<Map<String, String>> table = convertTsvToJson("src/main/resources/output/${company}/table98.tsv");
            model.addAttribute("table",table);
            return "documents/table";
        }



}

