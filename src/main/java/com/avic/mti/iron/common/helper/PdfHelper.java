package com.avic.mti.iron.common.helper;

import java.io.*;
import java.text.MessageFormat;
import java.util.List;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * PDF 工具类
 *
 * @author Jinghui Hu
 * @since 2020-09-24, JDK1.8
 */
public class PdfHelper {

  /**
   * 合并多个 PDF 文件
   *
   * @author Jinghui Hu
   * @since 2020-09-24, JDK1.8
   */
  public static byte[] merge(List<byte[]> byteList) throws IOException {
    return merge(byteList, false);
  }

  /**
   * 合并多个 PDF 文件
   *
   * @author Jinghui Hu
   * @since 2020-09-24, JDK1.8
   */
  public static byte[] merge(List<byte[]> byteList, boolean showPageNumber) throws IOException {
    PDFMergerUtility pdfMerger = new PDFMergerUtility();

    PDDocument mainDocument = new PDDocument();
    for (byte[] bytes : byteList) {
      PDDocument partDocument = PDDocument.load(bytes);
      pdfMerger.appendDocument(mainDocument, partDocument);
      partDocument.close();
    }

    if (showPageNumber) {
      int pageNumber = 0;
      int pageTotal = mainDocument.getNumberOfPages();
      for (PDPage page : mainDocument.getPages()) {
        PDPageContentStream contentStream =
            new PDPageContentStream(
                mainDocument, page, PDPageContentStream.AppendMode.APPEND, true, false);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ITALIC, 12);
        PDRectangle pageSize = page.getMediaBox();
        float x = pageSize.getLowerLeftX();
        float y = pageSize.getLowerLeftY();
        contentStream.newLineAtOffset(x + pageSize.getWidth() / 2, y + 20);
        String text = MessageFormat.format("{0} / {1}", ++pageNumber, pageTotal);
        contentStream.showText(text);
        contentStream.endText();
        contentStream.close();
      }
    }

    ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
    mainDocument.save(bytesOut);
    byte[] bytes = bytesOut.toByteArray();
    mainDocument.close();
    bytesOut.close();

    return bytes;
  }

  private PdfHelper() {}
}
