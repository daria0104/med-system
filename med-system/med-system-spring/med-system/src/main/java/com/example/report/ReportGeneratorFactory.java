package com.example.report;

public class ReportGeneratorFactory {
    public static ReportGenerator getReportGenerator(String format) {
        switch (format){
            case "txt":
                return new TxtReportGenerator();
            case "html":
            default:
                return new HtmlReportGenerator();
        }
    }
}
