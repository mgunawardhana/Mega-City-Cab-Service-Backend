package com.megacity.backend.booking_management.service.impl;

import com.megacity.backend.domain.entity.Booking;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ExcelExportService {
    public byte[] generateExcel(List<Booking> bookings) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Bookings");
            Row headerRow = sheet.createRow(0);

            String[] headers = {"Booking Number", "Booking Date", "Pickup Location", "Drop Off Location", "Car Number", "Taxes", "Distance", "Estimated Time", "Tax Without Cost", "Total Amount", "Customer Reg Number", "Driver ID", "Status", "Created Date", "Updated Date"};

            // Header Style
            CellStyle headerCellStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex()); // White font
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex()); // Black background
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Create header cells
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
                sheet.autoSizeColumn(i);
            }

            // Define colors for statuses
            CellStyle completedStyle = createCustomColorStyle(workbook, (byte) 46, (byte) 204, (byte) 113); // #2ecc71
            CellStyle closedStyle = createCustomColorStyle(workbook, (byte) 52, (byte) 152, (byte) 219); // #3498db
            CellStyle pendingStyle = createCustomColorStyle(workbook, (byte) 243, (byte) 156, (byte) 18); // #f39c12
            CellStyle cancelledStyle = createCustomColorStyle(workbook, (byte) 231, (byte) 76, (byte) 60); // #e74c3c


            int rowNum = 1;
            double totalTaxes = 0;
            double totalTaxWithoutCost = 0;
            double totalAmount = 0;

            // Populate booking data
            for (Booking booking : bookings) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(booking.getBookingNumber());
                row.createCell(1).setCellValue(booking.getBookingDate().toString());
                row.createCell(2).setCellValue(booking.getPickupLocation());
                row.createCell(3).setCellValue(booking.getDropOffLocation());
                row.createCell(4).setCellValue(booking.getCarNumber());

                Cell taxCell = row.createCell(5);
                taxCell.setCellValue(booking.getTaxes().doubleValue());
                totalTaxes += booking.getTaxes().doubleValue();

                row.createCell(6).setCellValue(booking.getDistance());
                row.createCell(7).setCellValue(booking.getEstimatedTime());

                Cell taxWithoutCostCell = row.createCell(8);
                taxWithoutCostCell.setCellValue(booking.getTaxWithoutCost());
                totalTaxWithoutCost += booking.getTaxWithoutCost();

                Cell totalAmountCell = row.createCell(9);
                totalAmountCell.setCellValue(booking.getTotalAmount().doubleValue());
                totalAmount += booking.getTotalAmount().doubleValue();

                row.createCell(10).setCellValue(booking.getCustomerRegistrationNumber());
                row.createCell(11).setCellValue(booking.getDriverId());

                // Apply status-based styling
                Cell statusCell = row.createCell(12);
                statusCell.setCellValue(booking.getStatus());

                switch (booking.getStatus().toUpperCase()) {
                    case "COMPLETED":
                        statusCell.setCellStyle(completedStyle);
                        break;
                    case "CLOSED":
                        statusCell.setCellStyle(closedStyle);
                        break;
                    case "PENDING":
                        statusCell.setCellStyle(pendingStyle);
                        break;
                    case "CANCELLED":
                        statusCell.setCellStyle(cancelledStyle);
                        break;
                }

                row.createCell(13).setCellValue(booking.getCreatedDate().toString());
                row.createCell(14).setCellValue(booking.getUpdatedDate().toString());
            }

            // Create total row
            Row totalRow = sheet.createRow(rowNum);
            Cell totalLabelCell = totalRow.createCell(4);
            totalLabelCell.setCellValue("Total:");

            // Style for total row
            CellStyle totalCellStyle = workbook.createCellStyle();
            Font totalFont = workbook.createFont();
            totalFont.setBold(true);
            totalFont.setColor(IndexedColors.BLACK.getIndex()); // Black font
            totalCellStyle.setFont(totalFont);
            totalCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex()); // Yellow background
            totalCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Apply style to the label
            totalLabelCell.setCellStyle(totalCellStyle);

            // Insert total values
            Cell totalTaxesCell = totalRow.createCell(5);
            totalTaxesCell.setCellValue(totalTaxes);
            totalTaxesCell.setCellStyle(totalCellStyle);

            Cell totalTaxWithoutCostCell = totalRow.createCell(8);
            totalTaxWithoutCostCell.setCellValue(totalTaxWithoutCost);
            totalTaxWithoutCostCell.setCellStyle(totalCellStyle);

            Cell totalAmountCell = totalRow.createCell(9);
            totalAmountCell.setCellValue(totalAmount);
            totalAmountCell.setCellStyle(totalCellStyle);

            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    /**
     * Utility method to create a CellStyle with a given RGB background color.
     */
    private CellStyle createCustomColorStyle(Workbook workbook, byte r, byte g, byte b) {
        CellStyle style = workbook.createCellStyle();

        XSSFColor color = new XSSFColor(new byte[]{r, g, b}, null);
        ((XSSFCellStyle) style).setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.BLACK.getIndex()); // Black font
        style.setFont(font);

        return style;
    }
}

