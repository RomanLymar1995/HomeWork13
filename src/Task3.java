public class Task3 {
    public static void main(String[] args) {
        JsonPlaceholderApiClient apiClient = new JsonPlaceholderApiClient();

        int userIdForOpenTasks = 4;
        String openTasksResult = apiClient.getOpenTasksForUser(userIdForOpenTasks);
        System.out.println(openTasksResult);
    }
}