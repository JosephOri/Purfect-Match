# BookWorms App

Welcome to the BookWorms app repository! This project is a comprehensive book-sharing platform that allows users to create and manage their profiles, share book posts, and interact with other users' posts. The project was developed following the best practices and requirements outlined for our graduation project.

## Features and Enhancements

### User Profile Management <img src="https://img.icons8.com/?size=100&id=86305&format=png&color=FFFFFF" alt="Person" width="25" height="25">
- **Profile Page**: View and edit profile information, including name and profile photo.
- **Joined Date**: Display the date when the user joined.
- **Profile Image**: Upload and display a profile image.
- **Navigation**: Navigate between different fragments using NavGraph.

### Book Sharing <img src="https://img.icons8.com/?size=100&id=42763&format=png&color=FFFFFF" alt="Person" width="25" height="25">
- **Create Book Post**: Create a post with book details, a short review, and an optional image.
- **View Book Posts**: See a feed of all book posts shared by other users.
- **View Book Details**: Tap on a book post to see its details, including the full review and poster's profile.

### Social Interaction <img src="https://img.icons8.com/?size=100&id=123782&format=png&color=FFFFFF" alt="Person" width="25" height="25">
- **Like a Book Post**: Like book posts to show appreciation.
- **See Likes on a Post**: View the number of likes a book post has received.

### Additional Features
- **Search for Books**: Search for specific books by title or author.

## Layout Enhancements
- **Profile Page**: Added phone TextView, icons, and fields.
- **Layout Order**: Changed names and order of elements for better user experience.

## Fixes and Refactoring
- **Bug Fixes**: Resolved issues related to navigation, layout, and data synchronization.
- **Code Refactoring**: Applied changes to improve code readability, structure, and maintainability.

## Logging and Debugging
- **Logs**: Added logs to ensure correct data fetching and UID printing.

## User Stories
### User Management (Must Have) <img src="https://img.icons8.com/?size=100&id=86305&format=png&color=FFFFFF" alt="Person" width="25" height="25">
- **US-1 (Login/Signup)**: Register or login using email and password.
- **US-2 (Profile)**: View and edit profile information.
- **US-3 (Logout)**: Log out and return to the login screen.

### Book Sharing (Must Have) <img src="https://img.icons8.com/?size=100&id=42763&format=png&color=FFFFFF" alt="Person" width="25" height="25">
- **US-4 (Create Book Post)**: Create a post about a book with title, author, review, and optional image.
- **US-5 (View Book Posts)**: See a feed of all shared book posts.
- **US-6 (View Book Details)**: View detailed information about a book post.

### Social Interaction (Must Have) <img src="https://img.icons8.com/?size=100&id=123782&format=png&color=FFFFFF" alt="Person" width="25" height="25">
- **US-7 (Like a Book Post)**: Like a book post.
- **US-8 (See Likes on a Post)**: See the number of likes on a book post.

### Additional Features (Should Have) <img src="https://img.icons8.com/?size=100&id=132&format=png&color=FFFFFF" alt="Person" width="25" height="25">
- **US-9 (Search for Books)**: Search for books by title or author.


## Project Structure
The project follows the MVVM (Model-View-ViewModel) architecture to ensure modularity, maintainability, and ease of testing.

### Directory Structure
```
/activities      - Contains Android Activities
/adapters        - Contains adapters
/fragments       - Contains fragments
/models          - Contains entities, firebaseModel, roomModel
/services        - Contains utils
/viewModels      - Contains ViewModel classes
/views           - Contains view components
```

## Installation and Setup
1. **Clone the repository**:
   ```sh
   git clone https://github.com/yourusername/BookWorms.git
   ```
2. **Open the project in Android Studio**.
3. **Sync the project with Gradle files**.
4. **Run the project on an emulator or a physical device**.

## Contributions
Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.









