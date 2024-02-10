# Swipe-N-Borrow Android App

Swipe-N-Borrow is an Android app designed to streamline the process of borrowing and lending books, offering a convenient solution for both users and administrators. This app facilitates various operations such as adding, removing, updating books, searching for books, issuing, re-issuing, and returning books. The underlying Cloud Firestore database ensures efficient storage of book and user details. Firebase Cloud Messaging is utilized for real-time notifications, such as approaching return deadlines, fine increments, or the addition of new books. Cloud Functions are employed for continuous monitoring of the database, updating fines, and triggering relevant notifications. The app features a user-friendly and interactive interface.

For a detailed project description and screenshots, please refer to the [Project Description and Screenshots](#).

## Usage

**Note:** The app is not live currently and requires setup.

1. Create a new Firebase Project (with Firestore Database) on [Firebase Console](https://console.firebase.google.com/) and configure it with the app.
2. Set up a Node.js and Firebase CLI environment to deploy the Cloud Functions.
3. Set up a cron job to trigger the "updateFine" function daily through an HTTPS request.

Refer to YouTube tutorials, online guides, and official documentation for assistance in project setup. A recommended starting point is the [Cloud Firestore Android Tutorial](https://firebase.google.com/docs/firestore/quickstart).

## Tools Used

- **Android Studio:** Primary IDE for Android app development using Java.
- **Cloud Firestore:** The database used for storing data in the form of collections and documents.
- **Firebase Cloud Messaging:** Used for sending push notifications to specified users using FCM tokens.
- **Cloud Functions:** Used for real-time monitoring of the database and listening to specific triggers for required actions.
- **Firebase Authentication:** Used to maintain accounts and perform login and signup actions.
- **cron-job.org:** Used for scheduling Cloud Functions through HTTPS requests.

## Contributing

Contributions are welcome! Follow these steps:

1. Fork it ([https://github.com/your-username/Swipe-N-Borrow/fork](https://github.com/your-username/Swipe-N-Borrow/fork))
2. Create a new branch: `git checkout -b new_feature`
3. Commit your changes: `git commit -am 'Added new_feature'`
4. Push to the branch: `git push origin new_feature`
5. Submit a pull request!

## Future Work

- UI improvement and optimization.
- Addition of new features and functionalities.
- Generalization for flexible usage. The app was initially implemented for a specific use case and may require adjustments for broader applications.

If you have new ideas or suggestions to improve this project, feel free to contact me. Your valuable feedback is highly appreciated!

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Project Description and Screenshots

### Features

- Simple and minimal layout designs.
- Interlinked activities for different functions.
- Text views and toasts for displaying info.
- Interaction with the user via edit text views, buttons, checkboxes, alert dialogs, card views, etc.
- Updates using push notifications.
- Real-time synchronization with an online database.
- Auto-login on app launch if the user/admin is logged in.
- Security rules to protect the database from malicious activities.

### Functionalities

**Admin Account:**

- Add new book to the library.
- Update details of an existing book.
- Remove a book from the library.
- Issue a book to a user.
- Return a book from user.
- Re-issue a book to a user.
- Collect fine from a user.
- Search for a particular book.

**User Account:**

- See issued books with due dates.
- Re-issue a book one time.
- Search for a particular book.

**Push Notifications to Users when:**

- New book added to the library.
- Fine of the user increases.
- Deadline for a particular book is nearby (3 days).

**Cloud Functions to:**

- Increase fine of the user if the deadline is crossed, once a day.
- Trigger notifications based on events.

### Screenshots

![Log In Page](screenshots/login_page.png) ![User Registration Page](screenshots/user_registration_page.png) ![Admin Registration Page](screenshots/admin_registration_page.png)

![User Home Page](screenshots/user_home_page.png) ![Admin Home Page](screenshots/admin_home_page.png) ![Add Book Page](screenshots/add_book_page.png)

![Remove Book Page](screenshots/remove_book_page.png) ![Update Book Page](screenshots/update_book_page.png) ![Issue Book Page](screenshots/issue_book_page.png)

![Return Book Page](screenshots/return_book_page.png) ![Reissue Book Page](screenshots/reissue_book_page.png) ![Collect Fine Page](screenshots/collect_fine_page.png)

![Collect Fine Confirmation](screenshots/collect_fine_confirmation.png) ![Search Book Page](screenshots/search_book_page.png) ![Search Book Results](screenshots/search_book_results.png)

![See Issued Books Page](screenshots/see_issued_books_page.png) ![User Reissue Book Page](screenshots/user_reissue_book_page.png) ![New Book Added Notification](screenshots/new_book_added_notification.png)

![Deadline Approaching Notification](screenshots/deadline_approaching_notification.png) ![Fine Increased Notification](screenshots/fine_increased_notification.png)

## Final Notes

Thank you for exploring this repository! Have a great day.

If you have any queries, feel
