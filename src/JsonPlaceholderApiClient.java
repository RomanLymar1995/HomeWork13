

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class JsonPlaceholderApiClient {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";


    public String createUser(String userDataJson) {
        return executePostRequest(BASE_URL + "/users", userDataJson);
    }


    public String updateUser(String userDataJson) {
        int userId = getUserIdFromJson(userDataJson);
        return executePutRequest(BASE_URL + "/users/" + userId, userDataJson);
    }


    public void deleteUser(int userId) {
        executeDeleteRequest(BASE_URL + "/users/" + userId);
    }


    public String getAllUsers() {
        return executeGetRequest(BASE_URL + "/users");
    }


    public String getUserById(int userId) {
        return executeGetRequest(BASE_URL + "/users/" + userId);
    }


    public String getUserByUsername(String username) {
        return executeGetRequest(BASE_URL + "/users?username=" + username);
    }


    public String getCommentsForLastPostOfUser(int userId) {
        String userPostsUrl = BASE_URL + "/users/" + userId + "/posts";
        String userPostsJson = executeGetRequest(userPostsUrl);

        // Assuming the JSON response is an array of posts with "id" field
        int postId = -1;
        int lastPostId = -1;
        int lastIndex = -1;
        while ((lastIndex = userPostsJson.indexOf("\"id\":", lastIndex + 1)) != -1) {
            int startIndex = userPostsJson.indexOf(":", lastIndex) + 1;
            int endIndex = userPostsJson.indexOf(",", lastIndex);
            postId = Integer.parseInt(userPostsJson.substring(startIndex, endIndex).trim());
            if (postId > lastPostId) {
                lastPostId = postId;
            }
        }

        if (lastPostId == -1) {
            return "No posts found for user " + userId;
        }

        String commentsUrl = BASE_URL + "/posts/" + lastPostId + "/comments";
        return executeGetRequest(commentsUrl);
    }


    public String getOpenTasksForUser(int userId) {
        String tasksUrl = BASE_URL + "/users/" + userId + "/todos";
        String tasksJson = executeGetRequest(tasksUrl);

        if (tasksJson.isEmpty()) {
            return "No tasks found for user " + userId;
        }


        StringBuilder openTasks = new StringBuilder();
        int lastIndex = -1;
        while ((lastIndex = tasksJson.indexOf("\"completed\":", lastIndex + 1)) != -1) {
            int startIndex = tasksJson.indexOf(":", lastIndex) + 1;
            int endIndex = tasksJson.indexOf(",", lastIndex);
            if (endIndex == -1) {
                endIndex = tasksJson.indexOf("}", lastIndex);
            }
            boolean completed = Boolean.parseBoolean(tasksJson.substring(startIndex, endIndex).trim());
            if (!completed) {
                int taskIdStart = tasksJson.lastIndexOf("\"id\":", lastIndex);
                int taskIdEnd = tasksJson.indexOf(",", taskIdStart);
                if (taskIdEnd == -1) {
                    taskIdEnd = tasksJson.indexOf("}", taskIdStart);
                }
                int taskId = Integer.parseInt(tasksJson.substring(taskIdStart + 5, taskIdEnd).trim());
                openTasks.append("Task ID: ").append(taskId).append("\n");
            }
        }

        if (openTasks.length() == 0) {
            return "No open tasks found for user " + userId;
        }

        return "Open tasks for user " + userId + ":\n" + openTasks.toString();
    }



    private String executeGetRequest(String url) {
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readResponse(connection);
            } else {
                return "GET request failed with response code: " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "GET request failed: " + e.getMessage();
        }
    }


    private String executePostRequest(String url, String data) {
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.writeBytes(data);
                outputStream.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                return readResponse(connection);
            } else {
                return "POST request failed with response code: " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "POST request failed: " + e.getMessage();
        }
    }

    private String executePutRequest(String url, String data) {
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.writeBytes(data);
                outputStream.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readResponse(connection);
            } else {
                return "PUT request failed with response code: " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "PUT request failed: " + e.getMessage();
        }
    }


    private void executeDeleteRequest(String url) {
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("DELETE request successful.");
            } else {
                System.out.println("DELETE request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("DELETE request failed: " + e.getMessage());
        }
    }

    private String readResponse(HttpURLConnection conn) throws IOException {
        try (Scanner scanner = new Scanner(conn.getInputStream(), "utf-8")) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
    private int getUserIdFromJson(String userDataJson) {
        try {

            int start = userDataJson.indexOf("\"id\":") + 5;
            int end = userDataJson.indexOf(",", start);
            if (end == -1) {
                end = userDataJson.indexOf("}", start);
            }
            String idStr = userDataJson.substring(start, end).trim();
            return Integer.parseInt(idStr);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            e.printStackTrace();

            return -1;
        }
    }
}
