package ui.exercises;

import java.util.ArrayList;
import java.util.List;

/**
 * Class quản lý dữ liệu test cho Exercise
 * Sau này có thể thay thế bằng dữ liệu từ database
 *
 */
public class TestDataExample {

    /**
     * Lấy danh sách bài tập mẫu
     *
     * @return List các bài tập
     */
    public static List<Exercises> getSampleExercises() {
        List<Exercises> exercises = new ArrayList<>();

        // Bài 1: Số nguyên tố sinh đôi
        exercises.add(new Exercises(
                1,
                "Bài 95. Số nguyên tố sinh đôi",
                "Lý Thuyết Số",
                "Toán Học",
                1.0,
                "24.5%",
                206,
                "Cho số nguyên dương N. Hãy in ra các cặp số nguyên tố sinh đôi nhỏ hơn hoặc bằng N.\n\n" +
                        "Số nguyên tố sinh đôi là hai số nguyên tố liên tiếp có hiệu bằng 2.\n" +
                        "Ví dụ: (3, 5), (5, 7), (11, 13), (17, 19), (29, 31),...",
                "Dòng đầu tiên chứa số nguyên dương N (N ≤ 10^6)",
                "Thời gian: 1 giây\nBộ nhớ: 256 MB",
                "In ra các cặp số nguyên tố sinh đôi, mỗi cặp trên một dòng theo định dạng (a, b)",
                "100",
                "(3, 5)\n(5, 7)\n(11, 13)\n(17, 19)\n(29, 31)\n(41, 43)\n(59, 61)\n(71, 73)"
        ));

        // Bài 2: Phép cộng 28Tech
        exercises.add(new Exercises(
                2,
                "Phép cộng 28Tech",
                "String",
                "Cơ Bản",
                1.0,
                "56.9%",
                226,
                "Cho hai số nguyên không âm A và B có thể rất lớn (tối đa 1000 chữ số).\n" +
                        "Nhiệm vụ của bạn là tính tổng A + B và in ra kết quả.\n\n" +
                        "Gợi ý: Sử dụng xâu ký tự để lưu trữ số và thực hiện phép cộng như cộng thủ công.",
                "Hai dòng lần lượt chứa hai số nguyên A và B",
                "Thời gian: 1 giây\nBộ nhớ: 256 MB\nĐộ dài mỗi số: ≤ 1000 chữ số",
                "In ra tổng của A và B",
                "123456789\n987654321",
                "1111111110"
        ));

        // Bài 3: Nhỏ hơn
        exercises.add(new Exercises(
                3,
                "Bài 52. Nhỏ hơn",
                "Mảng 1 Chiều",
                "Cơ Bản",
                1.0,
                "62.4%",
                1511,
                "Cho mảng số nguyên A[] gồm N phần tử và số nguyên X.\n" +
                        "Hãy đếm xem trong mảng có bao nhiêu phần tử nhỏ hơn X.\n\n" +
                        "Đây là bài tập cơ bản về duyệt mảng và so sánh.",
                "Dòng đầu tiên chứa N và X (1 ≤ N ≤ 10^6, 0 ≤ X ≤ 10^9)\n" +
                        "Dòng thứ hai chứa N số nguyên A[i] (0 ≤ A[i] ≤ 10^9)",
                "Thời gian: 1 giây\nBộ nhớ: 256 MB",
                "In ra số lượng phần tử trong mảng nhỏ hơn X",
                "5 10\n1 5 8 12 15",
                "3"
        ));

        // Bài 4: Duyệt mảng
        exercises.add(new Exercises(
                4,
                "Bài 51. Duyệt mảng",
                "Mảng 1 Chiều",
                "Nâng Cao",
                1.0,
                "63.2%",
                1182,
                "Cho mảng số nguyên A[] gồm N phần tử.\n" +
                        "Hãy in ra các phần tử trong mảng theo hai cách:\n" +
                        "- Thứ tự xuôi (từ đầu đến cuối)\n" +
                        "- Thứ tự ngược (từ cuối về đầu)\n\n" +
                        "Đây là bài tập về kỹ thuật duyệt mảng cơ bản.",
                "Dòng đầu tiên chứa N (1 ≤ N ≤ 10^6)\n" +
                        "Dòng thứ hai chứa N số nguyên A[i] (0 ≤ A[i] ≤ 10^9)",
                "Thời gian: 1 giây\nBộ nhớ: 256 MB",
                "Dòng đầu tiên in các phần tử từ đầu đến cuối, cách nhau bởi dấu cách\n" +
                        "Dòng thứ hai in các phần tử từ cuối về đầu, cách nhau bởi dấu cách",
                "5\n1 2 3 4 5",
                "1 2 3 4 5\n5 4 3 2 1"
        ));

        // Bài 5: Trailing zero
        exercises.add(new Exercises(
                5,
                "Bài 14. Trailing zero",
                "Lý Thuyết Số",
                "Toán Học",
                1.0,
                "32.8%",
                857,
                "Cho số nguyên dương N. Hãy đếm số lượng chữ số 0 tận cùng trong N! (giai thừa của N).\n\n" +
                        "Ví dụ: 10! = 3628800 có 2 chữ số 0 tận cùng\n" +
                        "Gợi ý: Số chữ số 0 tận cùng = số lần 10 chia hết N! = min(số ước 2, số ước 5)\n" +
                        "Vì số ước 2 luôn nhiều hơn số ước 5, nên ta chỉ cần đếm số ước 5.",
                "Một số nguyên dương N (1 ≤ N ≤ 10^18)",
                "Thời gian: 1 giây\nBộ nhớ: 256 MB",
                "In ra số lượng chữ số 0 tận cùng trong N!",
                "100",
                "24"
        ));

        // Bài 6: Tìm max
        exercises.add(new Exercises(
                6,
                "Bài 53. Tìm số lớn nhất",
                "Mảng 1 Chiều",
                "Cơ Bản",
                2.0,
                "78.2%",
                2341,
                "Cho mảng số nguyên A[] gồm N phần tử.\n" +
                        "Hãy tìm phần tử lớn nhất trong mảng và vị trí xuất hiện đầu tiên của nó.\n\n" +
                        "Lưu ý: Vị trí tính từ 0.",
                "Dòng đầu tiên chứa N (1 ≤ N ≤ 10^6)\n" +
                        "Dòng thứ hai chứa N số nguyên A[i] (-10^9 ≤ A[i] ≤ 10^9)",
                "Thời gian: 1 giây\nBộ nhớ: 256 MB",
                "In ra giá trị lớn nhất và vị trí xuất hiện đầu tiên (chỉ số mảng), cách nhau bởi dấu cách",
                "5\n3 7 2 7 5",
                "7 1"
        ));

        // Bài 7: Đếm số chẵn lẻ
        exercises.add(new Exercises(
                7,
                "Bài 54. Đếm số chẵn lẻ",
                "Mảng 1 Chiều",
                "Cơ Bản",
                1.0,
                "85.6%",
                3210,
                "Cho mảng số nguyên A[] gồm N phần tử.\n" +
                        "Hãy đếm số lượng số chẵn và số lẻ trong mảng.",
                "Dòng đầu tiên chứa N (1 ≤ N ≤ 10^6)\n" +
                        "Dòng thứ hai chứa N số nguyên A[i] (0 ≤ A[i] ≤ 10^9)",
                "Thời gian: 1 giây\nBộ nhớ: 256 MB",
                "Dòng đầu tiên in số lượng số chẵn\n" +
                        "Dòng thứ hai in số lượng số lẻ",
                "6\n1 2 3 4 5 6",
                "3\n3"
        ));

        // Bài 8: Tổng và tích
        exercises.add(new Exercises(
                8,
                "Bài 1. Tổng và tích",
                "Lý Thuyết Số",
                "Cơ Bản",
                1.0,
                "91.3%",
                4567,
                "Cho hai số nguyên A và B.\n" +
                        "Hãy tính tổng và tích của chúng.",
                "Một dòng chứa hai số nguyên A và B (1 ≤ A, B ≤ 10^9)",
                "Thời gian: 1 giây\nBộ nhớ: 256 MB",
                "Dòng đầu tiên in tổng A + B\n" +
                        "Dòng thứ hai in tích A × B",
                "5 3",
                "8\n15"
        ));

        // Bài 9: Đảo xâu
        exercises.add(new Exercises(
                9,
                "Bài 15. Đảo xâu",
                "String",
                "Cơ Bản",
                1.0,
                "73.4%",
                1876,
                "Cho một xâu ký tự S.\n" +
                        "Hãy in ra xâu đảo ngược của S.\n\n" +
                        "Ví dụ: S = \"hello\" thì xâu đảo ngược là \"olleh\"",
                "Một dòng chứa xâu ký tự S (1 ≤ độ dài S ≤ 10^6)",
                "Thời gian: 1 giây\nBộ nhớ: 256 MB",
                "In ra xâu đảo ngược của S",
                "hello",
                "olleh"
        ));

        // Bài 10: Kiểm tra palindrome
        exercises.add(new Exercises(
                10,
                "Bài 16. Kiểm tra palindrome",
                "String",
                "Nâng Cao",
                2.0,
                "68.9%",
                1432,
                "Cho một xâu ký tự S.\n" +
                        "Hãy kiểm tra xem S có phải là xâu palindrome hay không.\n\n" +
                        "Xâu palindrome là xâu đọc xuôi và đọc ngược đều giống nhau.\n" +
                        "Ví dụ: \"madam\", \"racecar\", \"level\" là các xâu palindrome.",
                "Một dòng chứa xâu ký tự S (1 ≤ độ dài S ≤ 10^6, chỉ chứa chữ cái thường)",
                "Thời gian: 1 giây\nBộ nhớ: 256 MB",
                "In ra YES nếu S là palindrome, ngược lại in NO",
                "racecar",
                "YES"
        ));

        return exercises;
    }

    /**
     * Thêm dữ liệu mẫu vào panel BodyCodingExercises
     *
     * @param panel Panel cần thêm dữ liệu
     */
    public static void addSampleData(BodyCodingExercises panel) {
        List<Exercises> exercises = getSampleExercises();
        for (Exercises exercise : exercises) {
            panel.addExercise(exercise);
        }
    }

    /**
     * Lấy một bài tập theo ID
     *
     * @param id ID của bài tập
     * @return Bài tập hoặc null nếu không tìm thấy
     */
    public static Exercises getExerciseById(int id) {
        List<Exercises> exercises = getSampleExercises();
        for (Exercises exercise : exercises) {
            if (exercise.getId() == id) {
                return exercise;
            }
        }
        return null;
    }

    /**
     * Lọc bài tập theo nhóm
     *
     * @param nhom Tên nhóm (VD: "Lý Thuyết Số", "Mảng 1 Chiều", "String")
     * @return Danh sách bài tập thuộc nhóm đó
     */
    public static List<Exercises> getExercisesByNhom(String nhom) {
        List<Exercises> result = new ArrayList<>();
        List<Exercises> exercises = getSampleExercises();

        for (Exercises exercise : exercises) {
            if (exercise.getNhom().equalsIgnoreCase(nhom)) {
                result.add(exercise);
            }
        }
        return result;
    }

    /**
     * Lọc bài tập theo dạng
     *
     * @param dang Dạng bài (VD: "Toán Học", "Cơ Bản", "Nâng Cao")
     * @return Danh sách bài tập thuộc dạng đó
     */
    public static List<Exercises> getExercisesByDang(String dang) {
        List<Exercises> result = new ArrayList<>();
        List<Exercises> exercises = getSampleExercises();

        for (Exercises exercise : exercises) {
            if (exercise.getDang().equalsIgnoreCase(dang)) {
                result.add(exercise);
            }
        }
        return result;
    }
}