package com.sp.questionnaire.utils;

import com.sp.questionnaire.entity.view.AddPaperViewPaper;
import com.sp.questionnaire.entity.view.AddPaperViewQuestion;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-21 Friday 13:54
 */
@Service
public class FileConvertToPaper {
    public AddPaperViewPaper convert(MultipartFile file) {
        AddPaperViewPaper paper = new AddPaperViewPaper();
        try {
            paper.setStartTime(null);
            paper.setEndTime(null);
            paper.setStatus(0);
            List<AddPaperViewQuestion> questions = new ArrayList<>();
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            int rowNumber = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rowNumber; i++) {
                Row row = sheet.getRow(i);
                AddPaperViewQuestion question = new AddPaperViewQuestion();
                List<String> options = new ArrayList<>();

                for (int j = 0; j < row.getPhysicalNumberOfCells() && j < 10; j++) {
                    row.getCell(j).setCellType(CellType.STRING);
                    String value = row.getCell(j).getStringCellValue();
                    if (i == 0) {   //set title
                        paper.setTitle(value);
                        break;
                    } else if (i == 1) {    //info text
                        break;
                    } else {  //questions
                        if (j == 0) {
                            question.setQuestionType(Integer.parseInt(value));
                        } else if (j == 1) {
                            question.setQuestionTitle(value);
                        } else {
                            options.add(value);
                        }
                    }
                    //System.out.print(row.getCell(j).getStringCellValue() + "\t");
                }
                if (i > 1) {
                    question.setQuestionOption(options);
                    questions.add(question);
                }

            }
            paper.setQuestions(questions);
        } catch (Exception e) {
            return null;
        }
        return paper;
    }

}
