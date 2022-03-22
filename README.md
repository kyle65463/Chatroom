# Chatroom
The final project of NTU Computer Network 2021 Fall
It contains three parts, the server, the console client and the web client.
Demo link: https://www.youtube.com/watch?v=6TdwkyZdIr4&ab_channel=%E8%98%87%E6%B5%9A%E7%AC%99
Homework slide link: https://docs.google.com/presentation/d/18NSrp3BokNhquQS3Gt9L9wOR21S9uMLpmXfe4JeZVFo/edit#slide=id.gfa25e13531_0_41

### Author
kyle65463, misterSu514

## Web
In `/web` folder

Install dependencies
```
npm install
```

Run locally
```
npm run dev
```

## Server & Client
We use Intellij IDEA IDE to build and run our code
All java dependencies are declared in the pom.xml, which will be downloaded by Maven automatically (using the IDE)
Also, we use Firebase for the database, therefore you may need to create a new Firebase project and set the service account file to the environment variable correctly before running the server
https://firebase.google.com/
https://firebase.google.com/docs/admin/setup
