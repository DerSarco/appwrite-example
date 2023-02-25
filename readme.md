# AppWrite App example

Welcome to this little [AppWrite](https://appwrite.io/) Example, i'm going to try to give you a few
code snippets focused on the most used features of any project. For example:

 - Login
 - User Creation
 - Database Usage
 - Image Storage
 - And many others...

# Programs
 
 - [AppWrite](https://appwrite.io/) (Obviously)
 - [Docker](https://www.docker.com/products/docker-desktop/)

# Application dependencies

 - AppWrite SDK (in the example below we will explain this)
 - [Jetpack Compose Navigation](https://developer.android.com/jetpack/compose/navigation)


# First steps

To start using this example we are going to need some programs, first of all please install [Docker](https://www.docker.com/products/docker-desktop/)
why this? AppWrite recommends to use this because they provide a complete container image ready for use.

When Docker installation is ready, please use this command line:

## For Unix based systems:
```
docker run -it --rm \
    --volume /var/run/docker.sock:/var/run/docker.sock \
    --volume "$(pwd)"/appwrite:/usr/src/code/appwrite:rw \
    --entrypoint="install" \
    appwrite/appwrite:1.2.1
```

## For Windows:

### CMD
```
docker run -it --rm ^
    --volume //var/run/docker.sock:/var/run/docker.sock ^
    --volume "%cd%"/appwrite:/usr/src/code/appwrite:rw ^
    --entrypoint="install" ^
    appwrite/appwrite:1.2.1
```

### PowerShell
```
docker run -it --rm `
    --volume /var/run/docker.sock:/var/run/docker.sock `
    --volume ${pwd}/appwrite:/usr/src/code/appwrite:rw `
    --entrypoint="install" `
    appwrite/appwrite:1.2.1
```

When docker installation finish, the command line will ask you for some basic configuration, in my personal case
i used the standard but i changed the HTTPS port from 443 to 4433, this is important because as we know, Android doesn't
make http request to not https endpoints.


![image](./readmeimg/1.png)


After the installation if everything is ok you can access to the website on your localhost service:

```https://localhost:YOUR HTTPS PORT HERE]/login```

> At my example i have an local ip address because i mounted this in an external pc.

If everything goes well you will see this landpage:

![image](./readmeimg/2.png)

Now we need to create an account to start developing in this platform, click on **Sign Up**

![image](./readmeimg/3.png)

Fill everything, is not mandatory to create an account with real data, you could use something like:

- User: foo
- Email: foo@bar.com
- Password: foobarbaz

(Just for developing purposes, if you want to use your container in production use something more clever xD)

In first instace, AppWrite will ask to us the name of our project. in this case we will name it as **appwriteExample**

![image](./readmeimg/4.png)

When the project is created we will see this dashboard:

![image](./readmeimg/5.png)

And now we can start our Android Project, but first! we need to add our platform, in our case we need to add the **Android Platform**

![image](./readmeimg/6.png)

What i recommend on this point? simple, create an Android project because AppWrite will ask some info about our project, in my example i will use the same data of this repository.

![image](./readmeimg/7.png)

Click on **Next** and start with the recommended config on our Android project

![image](./readmeimg/8.png)

Add this dependencies in your Android project (mavenCentral in the most of the cases is declared so maybe is not necessary to redeclare. If you have doubts you can check it in settings.graddle)

![image](./readmeimg/9.png)

add the dependency in build.gradle and sync your project

![image](./readmeimg/10.png)

AppWrite recommend us this piece of code to start building our application but we are going to skip this because in this repository we will work in a different way with this piece of code.

![image](./readmeimg/11.png)

Click on next and we are going to see this screen.

![image](./readmeimg/12.png)

And that's it!, press on **Take me to my Dashboard**.

Once we finish with this configuration our AppWrite backend is ready to go with our Android app. BUT first we need to make some little configurations in our Android app.

As I mention before we added the SDK dependency but we need to add this piece of XML code in our **manifest.xml** inside of **application tag**

```
  <!--This is from documentation, on scheme add android:scheme add your project id from AppWrite-->
        <activity android:name="io.appwrite.views.CallbackActivity" android:exported="true">
            <intent-filter android:label="android_web_auth">
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <data android:scheme="appwrite-callback-REPLACETHIS" />
            </intent-filter>
        </activity>
```

We need to change a little this code, on **REPLACETHIS** we need to add our project id from AppWrite, and where is that thing? well is very easy to find, in our AppWrite project dashboard we will see this little button where we can't see directly the id but it automatically copy the id to our clipboard.

![image](./readmeimg/13.png)

paste that id in **REPLACETHIS** and **manifest file** will be ready.

forget to mention, but is important, **add internet permissions in manifest as well**

>If you are forking this repository, just replace the project id and you will be ready to go 🙂

and with this config you are ready to start using this repo to watch how everything works.
