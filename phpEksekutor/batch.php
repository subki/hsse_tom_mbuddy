<?php
include("koneksiDB.php");

//$level=$_GET["level"];

$q = mysql_query("SELECT batch, location, no_telp
from batch_area order by batch ASC");

$arr= array();
$list=array();
	while($row=mysql_fetch_array($q)){
                   $arr["batch"]    = $row["batch"];
		   $arr["location"]= $row["location"];
		   $arr["no_telp"] = $row["no_telp"];
		   $list[]=$arr;
	}
	 $data = "{data:".json_encode($list)."}";
	echo $data;

?>