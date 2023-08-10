import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        JsonPlaceholderApiClient apiClient = new JsonPlaceholderApiClient();

        int userIdForComments = 3;
        String commentsResult = apiClient.getCommentsForLastPostOfUser(userIdForComments);
        System.out.println(commentsResult);
    }
}