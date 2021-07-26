package com.example.querydsl.dto;

import com.example.querydsl.annotation.Excel;
import com.example.querydsl.annotation.ExcelCulumn;

@Excel
public class ExcelExportVO {
    @ExcelCulumn
    private String anao;
}
