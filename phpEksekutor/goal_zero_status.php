<?php
include("koneksiDB.php");

$nik=$_GET["nik"];
$batch=$_GET["batch"];
$tahun=$_GET["tahun"];

$q = mysql_query( "SELECT SUM(day) as total, batch_area.location as location, max(tgl) as last
FROM incident 
LEFT JOIN batch_area on batch_area.batch=incident.batch
WHERE incident.batch = '$batch' and nik='$nik' and tgl like '$tahun'");

$arrayGoal= array();
$list=array();
	while($row=mysql_fetch_array($q)){
            if($row["total"]==null){
               $a1 = 0;
            }else{
               $a1 = $row["total"];
            }
            if($row["location"]==null){
               $a2 = "unknown";
            }else{
               $a2 = $row["location"];
            }
            if($row["last"]==null){
               $a3 = "0000-00-00";
            }else{
               $a3 = $row["last"];
            }
		$arrayGoal["total"]=$a1;
		$arrayGoal["location"]=$a2;
		$arrayGoal["last_in"]=$a3;
		$list[]=$arrayGoal;
	}
	 $data = "{data:".json_encode($arrayGoal)."}";
	echo $data;
?>