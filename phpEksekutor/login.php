<?php
    include("koneksiDB.php");
    
    $user=$_GET["user"];
    $pass=$_GET["pass"];
    
    $query = "SELECT * FROM login WHERE username = '$user' AND password='$pass'";
    $result = mysql_query($query) or die("Unable to verify user because : " . mysql_error());
    if (mysql_num_rows($result) == 1){
        echo 1;
    }else if (mysql_num_rows($result) > 1){
		echo "duplikasi user";
	}else {
        echo 0;
    }
?>