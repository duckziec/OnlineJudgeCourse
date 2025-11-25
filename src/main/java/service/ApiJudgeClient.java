package service;

import app.AppConfig;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * ApiJudgeClient
 * ----------------
 * Chuyên trách gọi API Judge0.
 * Không xử lý nghiệp vụ, chỉ gửi code + test case và nhận JSON kết quả.
 */
public class ApiJudgeClient {

    private static final String API_URL = AppConfig.getApiUrl();
    private static final String API_KEY = AppConfig.getApiKey();
    private static final String API_HOST = AppConfig.getApiHost();

    // =================== KIỂM TRA KẾT NỐI API ===================
    public static void testConnection() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://" + API_URL + "/about"))
                    .header("x-rapidapi-key", API_KEY)
                    .header("x-rapidapi-host", API_HOST)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Kết nối API thành công!");
            System.out.println(response.body());
        } catch (Exception e) {
            System.err.println("Lỗi khi kết nối API Judge0:");
            e.printStackTrace();
        }
    }

    // =================== GỌI API ĐỂ CHẤM 1 TEST CASE ===================
    public static JSONObject runTest(String code, String lang, String input, String expected) throws Exception {
        int langId = switch (lang.toLowerCase()) {
            case "python" -> 71;
            case "c" -> 50;
            case "cpp", "c++ cơ bản", "c++ nâng cao" -> 54;
            case "java", "oop với java", "lập trình java cơ bản" -> 62;
            case "c#", "csharp" -> 51;
            case "pascal" -> 67;
            case "sql", "cơ sở dữ liệu và sql" -> 82;
            default -> 71;
        };

        JSONObject body = new JSONObject();
        body.put("language_id", langId);
        body.put("source_code", code);
        body.put("stdin", input);
        body.put("expected_output", expected);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("X-RapidAPI-Key", API_KEY)
                    .header("X-RapidAPI-Host", API_HOST)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return new JSONObject(response.body());
        } catch (Exception e) {
            throw new Exception("Lỗi khi gọi API Judge0: " + e.getMessage(), e);
        }
    }
}
