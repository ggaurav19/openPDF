package com.lowagie.text.pdf;


import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * This test class contains a series of smoke tests. The goal of these tests is not to validate the generated document,
 * but to ensure no exception is thrown.
 */
public class ColumnTextTableTest {

    public static float A4_MARGIN_LEFT = 40;
    public static float A4_MARGIN_RIGHT = 40;
    public static float A4_MARGIN_TOP = 100;
    public static float A4_MARGIN_BOTTOM = A4_MARGIN_TOP;
    public static float A4_WIDTH_BODY = PageSize.A4.getWidth()-A4_MARGIN_LEFT-A4_MARGIN_RIGHT;
    public static float A4_HEIGHT_BODY = PageSize.A4.getHeight()-A4_MARGIN_TOP-A4_MARGIN_BOTTOM;

    protected PdfWriter pdfWriter;

    /**
     * Test scenario: Generate a pdf file with a table, and this table is generated
     * by the ColumnText class.
     */
    @Test
    public void testGenerateTableByColumnText() throws Exception{
        String filePath = System.getProperty("user.dir") + "/src/test/resources";

        File outputPDF = new File(filePath +"/columnTextTableTest.pdf");

        Document document = new Document(PageSize.A4);
        document.setMargins(A4_MARGIN_LEFT, A4_MARGIN_RIGHT, A4_MARGIN_TOP, A4_MARGIN_BOTTOM);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pdfWriter = PdfWriter.getInstance(document, baos);
        pdfWriter.setStrictImageSequence(true);

        document.open();

        document.add(new Chunk("Single table example page"));

        PdfPTable table = getPdfPTable();

        ColumnText ct = new ColumnText(pdfWriter.getDirectContent());
        ct.setSimpleColumn(A4_MARGIN_LEFT, A4_MARGIN_BOTTOM, A4_WIDTH_BODY+A4_MARGIN_LEFT, A4_HEIGHT_BODY+A4_MARGIN_BOTTOM);


        ct.addElement(table);

        ct.setYLine(ct.getYLine() - 10);

        ct.go(false);

        document.close();

        FileOutputStream fos = new FileOutputStream(outputPDF);
        fos.write(baos.toByteArray());
        fos.close();
    }

    /**
     * Test scenario: Generate a pdf file with multi tables, and this tables is generated
     * by the ColumnText class.
     */
    @Test
    public void testGenerateMultiTablesByColumnText() throws Exception{
        String filePath = System.getProperty("user.dir") + "/src/test/resources";

        File outputPDF = new File(filePath +"/columnTextMultiTableTest.pdf");

        Document document = new Document(PageSize.A4);
        document.setMargins(A4_MARGIN_LEFT, A4_MARGIN_RIGHT, A4_MARGIN_TOP, A4_MARGIN_BOTTOM);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pdfWriter = PdfWriter.getInstance(document, baos);
        pdfWriter.setStrictImageSequence(true);

        document.open();

        document.add(new Chunk("Multi tables example page"));

        PdfPTable table = getPdfPTable();

        ColumnText ct = new ColumnText(pdfWriter.getDirectContent());
        ct.setSimpleColumn(A4_MARGIN_LEFT, A4_MARGIN_BOTTOM, A4_WIDTH_BODY+A4_MARGIN_LEFT, A4_HEIGHT_BODY+A4_MARGIN_BOTTOM);

        ct.addElement(table);

        ct.setYLine(ct.getYLine()-10);

        ct.go(false);

        ct.setYLine(ct.getYLine()-10);

        ct.addElement(table);

        ct.go(false);

        document.close();

        FileOutputStream fos = new FileOutputStream(outputPDF);
        fos.write(baos.toByteArray());
        fos.close();
    }

    /**
     * Get the PdfPTable which will be written into the PDF file.
     * @return PdfPTable
     */
    PdfPTable getPdfPTable(){
        Paragraph f1,f2;
        PdfPTable table;
        table = new PdfPTable(1);
        table.setTotalWidth(A4_WIDTH_BODY);
        table.setLockedWidth(true);
        table.setSplitRows(false);
        PdfPCell cell;
        f1 = new Paragraph( new Chunk("cell1 example content"));
        f1.add(Chunk.NEWLINE);
        f1.setAlignment(Element.ALIGN_CENTER);
        f2 = new Paragraph( new Chunk("cell2 example content"));
        f2.add(Chunk.NEWLINE);
        f2.setAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell();
        cell.addElement(f1);
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(f2);
        table.addCell(cell);
        return table;
    }


}
