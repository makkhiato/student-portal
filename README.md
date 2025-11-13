## GROUP 2 STUDENT PORTAL PROJECT REPOSITORY

### Prerequisites
Make sure you have installed the following:
1. Git 2.40+
2. Java JDK 17+
3. Apache Maven 3.9.x [https://maven.apache.org/download.cgi]
4. NetBeans IDE [https://netbeans.apache.org/front/main/download/index.html]
5. Oracle
<br>

### Cloning the Project
***NOTE: You only need to do this one time***
1. Copy the link of the repository
2. Open cmd
   ```cmd
   git clone <github-repo-url>
   cd <~\project\path>
   ```
3. In Netbeans, open the project and click Run > Clean and Build *(Shift+F11)*

If you ever need to reinstall ojdbc11.jar locally, go to cmd and run:
```cmd
mvn install:install-file ^
 -Dfile="<path\to\project>\studentportal\lib\ojdbc11.jar" ^
 -DgroupId=com.oracle.database.jdbc ^
 -DartifactId=ojdbc11 ^
 -Dversion=23.3.0.0 ^
 -Dpackaging=jar
```
<br>

### Group Git/Github Workflow
**Step 1.** Before starting to work or add a feature, pull the latest version of the main repository
```cmd
git checkout main
git pull origin main
```

**Step 2.** Always create a new branch before you start working
```cmd
git checkout -b <branch-name>
```
**NOTE: DON'T WORK ON THE MAIN BRANCH!!**

**Step 3.** You can now work on the project in your computer
***NOTE: Make sure you are working on your own branch not the main branch***
```cmd
git checkout <branch-name>
```

**Step 4.** Stage and commit changes
```cmd
git add .
git commit -m "commit message: what did you change/update"
```
***NOTE: Do small commits than one large commit***

**Step 5.** Push branch to Github
```cmd
git push origin <branch-name>
```

**Step 6.** Create Pull Request (PR)
```
Open GitHub -> go to your branch -> click Compare & Pull Request
Add description of what was done
```

**Step 7.** Teammates will review the PR and either approve it or request changes

**Step 8.** After approval, merge the branch into main

**Step 9.** Update your local main by pulling the latest version from the remote repository
```cmd
git checkout main
git pull origin main
```
<br>

### Folder Structure
```
student-portal/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/group2/studentportal/
│       │       ├── Main.java                  # Main entry point
│       │       ├── frontend/                  # GUI classes (Swing)
│       │       └── backend/                   # Database and logic
│       │           ├── DBConnection.java      # Connects Java app to Oracle database
│       │           ├── models/                # Java classes representing database tables
│       │           └── dao/                   # Data Access Objects (handles database queries)
│       │
│       └── resources/                         # Non-code resources
│           └── database/                      # SQL scripts
│               ├── schema.sql                 # Creates tables
│               ├── views.sql                  # Creates database views 
│               └── sample_data.sql            # inserts sample data
│ 
├── lib/                                       # External JARs (if needed)
|   └── ojdbc11.jar                            # Oracle JDBC Driver
├── pom.xml                                    # Maven configuration
├── README.md                                  # Project setup guide
└── .gitignore                                 # Ignore files for Git
```
























