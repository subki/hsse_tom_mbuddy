<?php
include("koneksiDB.php");

$nik=$_GET["nik"];
$tahun=$_GET["tahun"];

$q = mysql_query( "SELECT tgl, problem, uraian, tindakan
FROM incident
WHERE incident.nik='$nik' and tgl like '$tahun'");

$arr= array();
$list=array();
	while($row=mysql_fetch_array($q)){
		$arr["tanggal"]=$row["tgl"];
		$arr["problem"]=$row["problem"];
		$arr["uraian"]=$row["uraian"];
		$arr["tindakan"]=$row["tindakan"];
		$list[]=$arr;
	}
	 $data = "{data:".json_encode($list)."}";
	echo $data;

?>