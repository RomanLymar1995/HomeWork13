public class Main {
    public static void main(String[] args) {
        JsonPlaceholderApiClient apiClient = new JsonPlaceholderApiClient();

        // Завдання 1
        String newUserJson = "{\"name\": \"John Doe\", \"username\": \"johndoe\"}";
        String createdUserJson = apiClient.createUser(newUserJson);
        System.out.println("Created user: " + createdUserJson);

        String updateUserJson = "{\"id\": 1, \"name\": \"Updated User\", \"username\": \"updateduser\"}";
        String updatedUserJson = apiClient.updateUser(updateUserJson);
        System.out.println("Updated user: " + updatedUserJson);

        int userIdToDelete = 1;
        apiClient.deleteUser(userIdToDelete);
        System.out.println("User with ID " + userIdToDelete + " deleted.");

        String allUsersJson = apiClient.getAllUsers();
        System.out.println("All users: " + allUsersJson);

        int userIdToRetrieve = 2;
        String userByIdJson = apiClient.getUserById(userIdToRetrieve);
        System.out.println("User by ID " + userIdToRetrieve + ": " + userByIdJson);

        String usernameToRetrieve = "updateduser";
        String userByUsernameJson = apiClient.getUserByUsername(usernameToRetrieve);
        System.out.println("User by username " + usernameToRetrieve + ": " + userByUsernameJson);

        // Завдання 2
        int userIdForComments = 3; // Задаємо ID користувача для отримання коментарів до останнього його посту
        String commentsResult = apiClient.getCommentsForLastPostOfUser(userIdForComments);
        System.out.println(commentsResult);

        // Завдання 3
        int userIdForOpenTasks = 4; // Задаємо ID користувача для отримання відкритих задач
        String openTasksResult = apiClient.getOpenTasksForUser(userIdForOpenTasks);
        System.out.println(openTasksResult);
    }
}