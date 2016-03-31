<?php
include("koneksiDB.php");

//$level=$_GET["level"];

$q = mysql_query("SELECT kode, training, level
from training_license order by kode ASC");

$arr= array();
$list=array();
	while($row=mysql_fetch_array($q)){
                   $arr["kode"]    = $row["kode"];
		   $arr["training"]= $row["training"];
		   $arr["level"] = $row["level"];
		   $list[]=$arr;
	}
	 $data = "{data:".json_encode($list)."}";
	echo $data;

?>