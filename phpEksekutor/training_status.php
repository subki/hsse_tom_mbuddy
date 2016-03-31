<?php
include("koneksiDB.php");

$nik=$_GET["nik"];
$kode=$_GET["kode"];

$q = mysql_query( "SELECT distinct kode, nik, tgl, nilai, trainer as verify
FROM karyawan_training 
WHERE nik= '$nik' AND kode='$kode'
ORDER BY tgl ASC");

$arr= array();
if(mysql_fetch_row($q)>0){
	while($row=mysql_fetch_array($q)){
		$arr["kode"]=$row["kode"];
		$arr["tgl"]=$row["tgl"];
		$arr["status"]="cek";
		$arr["nik"]=$row["nik"];
		$arr["nilai"]=$row["nilai"];
		$arr["verify"]=$row["verify"];
	}
	 $data ["data"] = $arr;
        // $data ["pesan"]= 1;
	echo json_encode($data);
}else{
        $arr["kode"]="null";
	$arr["tgl"]="null";
	$arr["status"]="Not Yet";
	$arr["nik"]="null";
	$arr["nilai"]=$row["nilai"];
	$arr["verify"]=$row["verify"];
        
         $data ["data"] = $arr;
         //$data ["pesan"]= 0;
	echo json_encode($data);
}

?>