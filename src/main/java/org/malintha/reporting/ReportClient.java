package org.malintha.reporting;


public class ReportClient {
    public static void main(String[] args) {
        BIRTReportGenerator generator = new BIRTReportGenerator("/home/malintha/projects/wso2/Reporting/pc_test_report." +
                "rptdesign");
        generator.generateReport("/home/malintha/","report","pdf");
    }
}
