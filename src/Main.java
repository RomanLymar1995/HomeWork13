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


        int userIdForComments = 3;
        String commentsResult = apiClient.getCommentsForLastPost(userIdForComments);
        System.out.println(commentsResult);


        int userIdForOpenTasks = 4;
        String openTasksResult = apiClient.getOpenTasksForUser(userIdForOpenTasks);
        System.out.println(openTasksResult);


        boolean savedComments = apiClient.saveCommentsToFile(userIdForComments);
        if (savedComments) {
            System.out.println("Comments were saved to a file.");
        } else {
            System.out.println("No comments to save or there was an error.");
        }
    }
}