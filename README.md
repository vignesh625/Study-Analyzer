# Study-Analyzer
Study Assistant (Study Analyzer) is a Java-based chatbot that helps students create a personalized study plan based on their strengths, weaknesses, and available study time.
import java.util.Scanner;
import okhttp3.*;

public class StudyAssistant {

    private static final String OPENAI_API_KEY = "your_openai_api_key"; // Replace with your OpenAI API Key

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Collect user input
        System.out.println("Enter your strong subjects:");
        String strengths = scanner.nextLine();

        System.out.println("Enter your weak subjects:");
        String weaknesses = scanner.nextLine();

        System.out.println("How many hours per day can you study?");
        String hours = scanner.nextLine();

        scanner.close();

        // Prepare prompt for AI
        String prompt = "Create a personalized study plan for a student. "
                + "Strengths: " + strengths + ". Weaknesses: " + weaknesses + ". "
                + "Available study hours per day: " + hours + ". "
                + "Provide an effective schedule.";

        try {
            String response = callOpenAI(prompt);
            System.out.println("\nAI-Generated Study Plan:\n" + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // OpenAI API Call
    private static String callOpenAI(String prompt) throws Exception {
        OkHttpClient client = new OkHttpClient();

        String json = "{"
                + "\"model\": \"gpt-4\","
                + "\"messages\": [{\"role\": \"user\", \"content\": \"" + prompt.replace("\"", "\\\"") + "\"}],"
                + "\"max_tokens\": 200"
                + "}";

        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .addHeader("Authorization", "Bearer " + OPENAI_API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
