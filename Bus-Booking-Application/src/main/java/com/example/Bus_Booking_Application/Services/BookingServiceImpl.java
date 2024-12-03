package com.example.Bus_Booking_Application.Services;



import com.example.Bus_Booking_Application.Entity.Bookings;
import com.example.Bus_Booking_Application.Repository.BookingRepo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepo bookingsRepository;

    @Override
    public Bookings saveBooking(Bookings booking) {
        return bookingsRepository.save(booking);
    }


    @Override
    public List<Bookings> viewAllBus() {
        return bookingsRepository.findAll();
    }


    @Override
    public List<Bookings> getBookingsByEmail(String email) {
        return bookingsRepository.findByEmail(email);

    }

    @Override
    public void generatePdfWithPDFBox(Bookings booking, HttpServletResponse response) throws IOException {
        // Create a new document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // Start writing content
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.setLeading(18f);
            contentStream.newLineAtOffset(50, 750); // Starting position

            // Title
            contentStream.showText("Booking Confirmation");
            contentStream.newLine();
            contentStream.newLine();

            // Booking Details
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.showText("Booking ID: " + booking.getId());
            contentStream.newLine();
            contentStream.showText("Name: " + booking.getName());
            contentStream.newLine();
            contentStream.showText("Email: " + booking.getEmail());
            contentStream.newLine();
            contentStream.showText("Age: " + booking.getAge());
            contentStream.newLine();
            contentStream.showText("Gender: " + booking.getGender());
            contentStream.newLine();
            contentStream.showText("Phone Number: " + booking.getPhoneNumber());
            contentStream.newLine();
            contentStream.showText("Bus Name: " + booking.getBusName());
            contentStream.newLine();
            contentStream.showText("Route: " + booking.getSource() + " to " + booking.getDestination());
            contentStream.newLine();
            contentStream.showText("Date: " + booking.getDate() + " | Time: " + booking.getTime());
            contentStream.newLine();
            contentStream.showText("Price: " + booking.getPrice());
            contentStream.newLine();

            contentStream.endText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Write PDF to HTTP response
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=booking.pdf");

        document.save(response.getOutputStream());
        document.close();
    }
}