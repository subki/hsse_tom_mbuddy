<?php
include("koneksiDB.php");

$nik=$_GET["nik"];

$q = mysql_query( "SELECT a.kode, b.training, a.tgl, a. trainer as verify, a.nilai, b.trainer, b.level
FROM karyawan_training a
LEFT JOIN training_license b ON b.kode= a.kode
WHERE a.nik= '$nik'");

$arr= array();
$list=array();
	while($row=mysql_fetch_array($q)){
		$arr["kode"]=$row["kode"];
		$arr["training"]=$row["training"];
		$arr["tgl"]=$row["tgl"];
		$arr["trainer"]=$row["trainer"];
		$arr["level"]=$row["level"];
		$arr["verify"]=$row["verify"];
		$arr["nilai"]=$row["nilai"];
		$list[]=$arr;
	}
	 $data = "{data:".json_encode($list)."}";
	echo $data;

?>