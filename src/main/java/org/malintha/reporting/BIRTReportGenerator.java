package org.malintha.reporting;

import java.util.HashMap;
import org.eclipse.birt.report.engine.api.*;

public class BIRTReportGenerator {
    private String designFilePath;

    public BIRTReportGenerator(String designFilePath){
        this.designFilePath = designFilePath;
    }

    public void generateReport(String outputFilePath, String outputFileName, String format) {
        //Variables used to control BIRT Engine instance
        EngineConfig conf = null;
        ReportEngine eng = null;
        IReportRunnable design = null;
        IRunAndRenderTask task = null;
        HTMLRenderContext renderContext = null;
        HashMap contextMap = null;
        PDFRenderOption options = null;

        conf = new EngineConfig();

        //Create new Report engine based off of the configuration
        eng = new ReportEngine( conf );

        //open the report design
        try
        {
            design = eng.openReportDesign(designFilePath);
        }
        catch (Exception e)
        {
            System.err.println("An error occured during the opening of the report file!");
            e.printStackTrace();
            System.exit(-1);
        }

        //With the file open, create the Run and Render task to run the report
        task = eng.createRunAndRenderTask(design);

        //Set Render context to handle url and image locataions, and apply to the
        //task
        renderContext = new HTMLRenderContext();
        renderContext.setImageDirectory("image");
        contextMap = new HashMap();
        contextMap.put( EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext );
        task.setAppContext( contextMap );

        //This will set the output file location, the format to rener to, and
        //apply to the task
        options = new PDFRenderOption();
        options.setOutputFileName(outputFilePath+outputFileName+"."+format);
        options.setOutputFormat(format);
        task.setRenderOption(options);

        //Cross our fingers and hope everything is set
        try
        {
            task.run();
        }
        catch (Exception e)
        {
            System.err.println("An error occured while running the report!");
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("All went well. Closing program!");
        eng.destroy();
    }
}