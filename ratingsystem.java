import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;


public class ratingsystem {

    public static void main(String[] args) throws IOException {
        
        int totalEntries = 0;
        int totalRatingSum = 0;
 
        int countExcellent = 0; 
        int countGood      = 0;  
        int countAverage   = 0;   
        int countPoor      = 0;   
        int countVeryPoor  = 0;   
 
        StringBuilder entries = new StringBuilder();
        

        while(true) {
            
             String studentName = JOptionPane.showInputDialog(
                null,
                "Enter Student Name:",
                "Student Feedback System",
                JOptionPane.PLAIN_MESSAGE
            );

             if (studentName == null || studentName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "Student name cannot be empty. Please try again.",
                    "Input Error", JOptionPane.WARNING_MESSAGE);
                continue;
            }

            String course = JOptionPane.showInputDialog(
                null,
                "Enter Course / Subject:",
                "Student Feedback System",
                JOptionPane.PLAIN_MESSAGE
            );

            if (course == null || course.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "Course/Subject cannot be empty. Please try again.",
                    "Input Error", JOptionPane.WARNING_MESSAGE);
                continue;
            }
            
            String feedbackMessage = JOptionPane.showInputDialog(
                null,
                "Enter Feedback Message:",
                "Student Feedback System",
                JOptionPane.PLAIN_MESSAGE
            );

            if (feedbackMessage == null || feedbackMessage.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "Feedback message cannot be empty. Please try again.",
                    "Input Error", JOptionPane.WARNING_MESSAGE);
                continue;
            }

            int rating = 0;
            boolean validRating = false;

            while (!validRating) {
                String ratingInput = JOptionPane.showInputDialog(
                    null,
                    "Enter Rating (1 = Very Poor, 2 = Poor, 3 = Average,\n"
                    + "4 = Good, 5 = Excellent):",
                    "Student Feedback System",
                    JOptionPane.PLAIN_MESSAGE
                );
                if (ratingInput == null) {
                    // User cancelled — skip this entry
                    break;
                }
                try {
                    rating = Integer.parseInt(ratingInput.trim());
                    if (rating >= 1 && rating <= 5) {
                        validRating = true;
                    } else {
                        JOptionPane.showMessageDialog(null,
                            "Rating must be between 1 and 5. Please try again.",
                            "Invalid Rating", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                        "Please enter a whole number from 1 to 5.",
                        "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            }

            if (!validRating) {
            }  else {
                
                totalEntries++;
                totalRatingSum += rating;
                switch (rating) {
                    case 5: countExcellent++; break;
                    case 4: countGood++;      break;
                    case 3: countAverage++;   break;
                    case 2: countPoor++;      break;
                    case 1: countVeryPoor++;  break;
        }
                String ratingLabel = getRatingLabel(rating);
                String record =
                    "====================================\n" +
                    "Entry #" + totalEntries + "\n" +
                    "Student Name : " + studentName.trim() + "\n" +
                    "Course       : " + course.trim() + "\n" +
                    "Feedback     : " + feedbackMessage.trim() + "\n" +
                    "Rating       : " + rating + " - " + ratingLabel + "\n" +
                    "====================================\n";
 
                entries.append(record);

                try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("StudentFeedback.txt", false))) {
                    writer.write(record);
                    writer.newLine();
                   } catch (IOException e) {
                    JOptionPane.showMessageDialog(null,
                        "Error saving feedback to file:\n" + e.getMessage(),
                        "File Error", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(null,
                    "✔ Feedback saved successfully!",
                    "Saved", JOptionPane.INFORMATION_MESSAGE);
                 }
                 
                 int choice = JOptionPane.showConfirmDialog(
                null,
                "Would you like to add another feedback entry?",
                "Continue?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE );

                 boolean continueLoop = choice == JOptionPane.YES_OPTION;
                 if (!continueLoop) {
                    break;
                }
                continueLoop = choice == JOptionPane.YES_OPTION;
        }

        if (totalEntries == 0) {
            JOptionPane.showMessageDialog(null,
                "No feedback entries were recorded.",
                "No Data", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
 
        double averageRating = (double) totalRatingSum / totalEntries;
        String overallFeedback = getOverallFeedback(averageRating);

        String summary =
            "FEEDBACK SUMMARY REPORT      \n" +
            "Total Feedback Entries : " + totalEntries + "\n" +
            "Average Rating         : " + String.format("%.2f", averageRating) + "\n\n" +
            "─── Rating Breakdown ───────────────\n" +
            "  5 - Excellent : " + countExcellent + "\n" +
            "  4 - Good      : " + countGood      + "\n" +
            "  3 - Average   : " + countAverage   + "\n" +
            "  2 - Poor      : " + countPoor      + "\n" +
            "  1 - Very Poor : " + countVeryPoor  + "\n\n" +
            "─── Overall Assessment ─────────────\n" +
            "  " + overallFeedback + "\n";
            

        try (BufferedWriter writer = new BufferedWriter(
            new FileWriter("StudentFeedback.txt", true))) {
            writer.newLine();
            writer.write(summary);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                "Error saving summary to file:\n" + e.getMessage(),
                "File Error", JOptionPane.ERROR_MESSAGE);
        }
 
        
        JOptionPane.showMessageDialog(null,
            summary,
            "Feedback Summary",
            JOptionPane.INFORMATION_MESSAGE);
        }

         private static String getRatingLabel(int rating) {
        switch (rating) {
            case 5: return "Excellent";
            case 4: return "Good";
            case 3: return "Average";
            case 2: return "Poor";
            case 1: return "Very Poor";
            default: return "Unknown";
        }
    }

    private static String getOverallFeedback(double avg) {
        if (avg >= 4.5) {
            return "Outstanding Feedback!";
        } else if (avg >= 3.5) {
            return "Good Feedback!";
        } else if (avg >= 2.5) {
            return "Average Feedback";
        } else {
            return "Needs Improvement";
        }
    }
}

