<?php
include("koneksiDB.php");

$level=$_GET["level"];
$nik = $_GET["nik"];

$q = mysql_query("SELECT kode, training, trainer, level
from training_license where level='$level'");


$arr= array();
		   $arr2=array();
		   $list2=array();
$list=array();
$data="";
$data2="";
	while($row=mysql_fetch_array($q)){
                   $arr["kode"]    = $row["kode"];
		   $arr["training"]= $row["training"];
		   $arr["trainer"] = $row["trainer"];
		   $arr["level"]   = $row["level"];
		   
		   
		   $kode = $row["kode"];
		   $q2 = mysql_query( "SELECT distinct kode, nik, tgl, nilai, trainer as verify
			FROM karyawan_training 
			WHERE nik= '$nik' AND kode='$kode'
			ORDER BY tgl ASC");
		   //var_dump($q2);
		   $row2=mysql_fetch_row($q2);
		   	//if($row2[0] != null){
		   	//	$arr2["kode"]=$row2[0];
		   	//}else{
		   	//	$arr2["kode"]=$kode;
		   	//}
		   	if($row2[1] != null){
		   		$arr["nik"]=$row2[1];
		   	}else{
		   		$arr["nik"]=$nik;
		   	}
		   	if($row2[2] != null){
		   		$arr["tgl"]=$row2[2];
		   	}else{
		   		$arr["tgl"]="0000-00-00";
		   	}
		   	if($row2[3] != null){
		   		$arr["nilai"]=$row2[3];
		   	}else{
		   		$arr["nilai"]="0";
		   	}
		   	if($row2[4] != null){
		   		$arr["verify"]=$row2[4];
		   	}else{
		   		$arr["verify"]="NA";
		   	}
		   	$list[]=$arr;
			//array_push($list, $arr2);
			//$data2 = "{pesan:".json_encode($list2)."}";
		   

	}
	 $data = "{data:".json_encode($list)."}";
	 //$data2 = "{pesan:".json_encode($list2)."}";
	echo $data;
	//echo $data2;

?>