## Web
In `/web` folder

Install dependencies
```
npm install
```

Run
```
npm run dev
```

## Server & Client
We use Intellij IDEA IDE to build and run our code (so we are not quite sure how to build with commands)
All java dependencies are declared in the pom.xml, which will be downloaded by Maven automatically (using the IDE)
Also, we use Firebase for the database, therefore you may need to create a new Firebase project and set the service account file to the environment variable correctly before running the server
https://firebase.google.com/
https://firebase.google.com/docs/admin/setup
