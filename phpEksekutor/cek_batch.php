<?php
include("koneksiDB.php");

$batch=$_GET["batch"];

$q = mysql_query( "SELECT * FROM batch_area WHERE batch='$batch'");

$arrayLoc= array();
$list=array();
	while($row=mysql_fetch_array($q)){
		$arrayLoc["location"]=$row["location"];
		$list[]=$arrayLoc;
	}
	 $data = "{data:".json_encode($arrayLoc)."}";
	echo $data;

?>