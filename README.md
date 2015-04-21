# hoyapp
Hoy app let you meet random people all over the world. It is already published on app market. But if you want to add some feature, you are free to steal anything. 
Note : It is not fully completed project. Just a hobby project which created couple days. There are many shortcoming.

Try out the application [on the Android Market][1].

#SETUP

#step 1
App is using Parse api to push notification. First, you need to get client key and app id from Parse and replace it with below code APP_ID and CLIENT_KEY.

```java
public class HoyApp extends Application {
    ...
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this,"APP_ID","CLIENT_KEY");
    }
    ...
}
```
#step 2
Second, this app is simply use two web services which are add new user to db and get random people from db. I will not share my db info with you but you can simply copy php files, edit with your db info and put them to your server.

Simply fetch 15 user from db.

```php
<?php
		//Fill here with your info
		$hostname ="";
		$database ="";
		$username ="";
		$password ="";

		mysql_connect($hostname,$username,$password);

		mysql_select_db($database);

		$sql=mysql_query("SELECT * FROM user ORDER BY RAND() LIMIT 15");
		while($row=mysql_fetch_assoc($sql))
		$output['user'][]=$row;
		print(json_encode($output));
		mysql_close();
	
?>
```
Add new user to db.
```php
<?php
		//Fill here with your info
		$hostname ="";
		$database ="";
		$username ="";
		$password ="";

		$con=mysqli_connect($hostname,$username,$password,$database);
		mysql_query("SET NAMES UTF8");
		// Check connection
		if (mysqli_connect_errno())
		{
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
		}

		$sql="INSERT INTO user (user_id,image_url,name_surname,age,mac_adress)
		VALUES
			('$_POST[user_id]','$_POST[image_url]','$_POST[name_surname]','$_POST[age]','$_POST[mac_adress]')";

			if (!mysqli_query($con,$sql))
			{
				die('Error: ' . mysqli_error());
			}
	
	
	mysqli_close($con);
	?>
```
Better that i give you user table mapping because you need to create table on your mysql db.
```
//user_id primary key(INT), not auto increment.
user(user_id, image_url, name_surname, age, mac_adress)
```
#step 3

After you create you user table in your mysql db and put add_user.php, get_users.php files to FTP, your webservices seems ready to use.

```java
public class ServerConstant {

    public static String URL_ADD_NEW = "add_new_person_to_db_url";
    public static String URL_GET_ALL = "get_random_people_from_db_url";

    ...

}
```

#step 4

This app uses facebook information of users. For this reason you need to create facebook app and put your app_id in string.xml file.

```xml
<string name="app_id">facebook_app_id_here</string>
```

[1]:https://play.google.com/store/apps/details?id=akilliyazilim.justhoy
