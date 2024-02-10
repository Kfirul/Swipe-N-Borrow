# Swipe-N-Borrow Android App

Swipe-N-Borrow is an Android app designed to streamline the process of borrowing and lending books, offering a convenient solution for both users and administrators. This app facilitates various operations such as adding, removing, updating books, searching for books, issuing, re-issuing, and returning books. The underlying Cloud Firestore database ensures efficient storage of book and user details. Firebase Cloud Messaging is utilized for real-time notifications, such as approaching return deadlines, fine increments, or the addition of new books. Cloud Functions are employed for continuous monitoring of the database, updating fines, and triggering relevant notifications. The app features a user-friendly and interactive interface.

For a detailed project description and screenshots, please refer to the [project documentation](#project-description-and-screenshots).

## Usage

**Note:** The app is not live currently and requires setup.

1. Create a new Firebase Project (with Firestore Database) on [Firebase Console](https://console.firebase.google.com/) and configure it with the app.
2. Set up a Node.js and Firebase CLI environment to deploy the Cloud Functions.
3. Set up a cron job to trigger the "updateFine" function daily through an HTTPS request.

Refer to YouTube tutorials, online guides, and official documentation for assistance in project setup. A recommended starting point is the [Cloud Firestore Android Tutorial](https://youtube.com/playlist?list=PLrnPJCHvNZuDrSqu-dKdDi3Q6nM-VUyxD).

## Tools Used

* [Android Studio](https://developer.android.com/studio) : Primary IDE for Android App Development using Java.
* [Cloud Firestore](https://firebase.google.com/products/firestore) : The Database used for storing data in the form of Collections and Documents.
* [Firebase Cloud Messaging](https://firebase.google.com/products/cloud-messaging) : Used for sending Push Notifications to specified users using FCM Tokens.
* [Cloud Functions](https://firebase.google.com/products/functions) : Used for real-time monitoring of the database and listening to certain triggers for taking required actions.
* [Firebase Authentication](https://firebase.google.com/products/auth) : Used to maintain Accounts and perform Login and Signup Actions.
* [cron-job.org](https://cron-job.org/en/): Used for scheduling Cloud Functions through HTTPS Requests.

## Contributing
You are welcome to contribute :

1. Fork it ([https://github.com/rohanrao619/Library_Management_Android_App/fork](https://github.com/rohanrao619/Library_Management_Android_App/fork))
2. Create new branch : `git checkout -b new_feature`
3. Commit your changes : `git commit -am 'Added new_feature'`
4. Push to the branch : `git push origin new_feature`
5. Submit a pull request !

## Future Work
* UI Improvement and Optimization
* New Features/Functionalities
* Generalization for Flexible Usage. This App was implemented specifically for my College's Library System:
  * Books and Users are identified through unique Book IDs and Card No. respectively.
  * While issuing/re-issuing/returning a Book, 2 digits need to be added at end of its Book ID (to specify the unit number). For example, copies of a Book with ID 14532 having 25 units would have the IDs 1453201, 1453202, 1453203 ... 1453225. Book IDs are displayed in the same way in User Account's end.
  * Email ID for registration needs to be of the domain @iiitnr.edu.in.
  * Books are issued for a duration of 14 days, after which a fine of Rs.1 per day is incurred (if the book is not re-issued or returned).
  * A book can be re-issued only 1 time from the User's end.

If you have any new ideas or suggestions to improve this project, feel free to contact me. Your valuable feedback is highly appreciated!

## License
This Project is licensed under the MIT License, see the [LICENSE](LICENSE) file for details.

## Project Description and Screenshots
### Features
* Simple and minimal Layout Designs.
* Interlinked Activities for different functions.
* Text Views and Toasts for displaying info.
* Interaction with the User with the help of Edit Text Views, Buttons, Checkboxes, Alert Dialogs, Card Views, etc.
* Updates using Push Notifications.
* Real-time Synchronization with Online Database.
* Auto Login on App launch if the user/admin is logged in.
* Security Rules to protect the database from malicious activities.

### Functionalities
**Admin Account :**

* Add new Book to the Library.
* Update details of an existing Book.
* Remove a Book from the Library.
* Issue a Book to a User.
* Return a Book from User.
* Re-Issue a Book to a User.
* Collect Fine from a User.
* Search for a particular Book.

**User Account :**

* See Issued Books with Due Dates.
* Re-issue a Book one time.
* Search for a particular Book.

**Push Notifications to Users when :**

* New Book added to the Library.
* Fine of the User increases.
* Deadline for a particular Book is nearby (3 days).

**Cloud Functions to :**

* Increase Fine of the user if the deadline is crossed, once in a day.
* Trigger Notifications based on events.

### Screenshots

|![Log In Page](Screenshots/Log_In_Page.png)|![User Registration Page](Screenshots/User_Registration_Page.png)|![Admin Registration Page](Screenshots/Admin_Registration_Page.png)|
|:---:|:---:|:---:|
|**Log In Page**|**User Registration Page**|**Admin Registration Page**|

|![User Home Page](Screenshots/User_Home_Page.png)|![Admin Home Page](Screenshots/Admin_Home_Page.png)|![Add Book Page](Screenshots/Add_Book_Page.png)|
|:---:|:---:|:---:|
|**User Home Page**|**Admin Home Page**|**Add Book Page**|

|![Remove Book Page](Screenshots/Remove_Book_Page.png)|![Update Book Page](Screenshots/Update_Book_Page.png)|![Issue Book Page](Screenshots/Issue_Book_Page.png)|
|:---:|:---:|:---:|
|**Remove Book Page**|**Update Book Page**|**Issue Book Page**|

|![Return Book Page](Screenshots/Return_Book_Page.png)|![Reissue Book Page](Screenshots/Reissue_Book_Page.png)|![Collect Fine Page](Screenshots/Collect_Fine_Page.png)|
|:---:|:---:|:---:|
|**Return Book Page**|**Reissue Book Page**|**Collect Fine Page**|

|![Collect Fine Confirmation Page](Screenshots/Collect_Fine_Confirmation_Page.png)|![Search Book Page](Screenshots/Search_Book_Page.png)|![Search Book Results](Screenshots/Search_Book_Results.png)|
|:---:|:---:|:---:|
|**Collect Fine Confirmation**|**Search Book Page**|**Search Book Results**|

|![See Issued Books Page](Screenshots/See_Issued_Books_Page.png)|![User Reissue Book Page](Screenshots/User_Reissue_Book_Page.png)|![New Book Added Notification](Screenshots/New_Book_Added_Notification.png)|
|:---:|:---:|:---:|
|**See Issued Books Page**|**User Reissue Book Page**|**New Book Added Notification**|

|![Deadline Approaching Notification](Screenshots/Deadline_Approaching_Notification.png)|![Fine Increased Notification](Screenshots/Fine_Increased_Notification.png)|
|:---:|:---:|
|**Deadline Approaching Notification**|**Fine Increased Notification**|

## Final Notes
**Thanks for exploring this repository! Have a great day.**

If you have any queries, feel free to contact me.

Saini Rohan Rao
- [Email](mailto:rohanrao619@gmail.com)
- [GitHub](https://github.com/rohanrao619)
- [LinkedIn](https://www.linkedin.com/in/rohanrao619)
- [Portfolio Website](https://rohanrao619.github.io/)
