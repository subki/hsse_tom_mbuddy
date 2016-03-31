<?php
include("koneksiDB.php");

$q = mysql_query("SELECT * FROM karyawan order by nik");
$arrayPegawai = array();
$list=array();
	while($row=mysql_fetch_array($q)){
		$arrayPegawai["nik"]=$row["nik"];
		$arrayPegawai["nama"]=$row["nama"];
		$arrayPegawai["tmp_lhr"]=$row["tmp_lhr"];
		$arrayPegawai["tgl_lhr"]=$row["tgl_lhr"];
		$arrayPegawai["jenkel"]=$row["jenkel"];
		$arrayPegawai["agama"]=$row["agama"];
		$arrayPegawai["status"]=$row["status"];
		$arrayPegawai["alamat"]=$row["alamat"];
		$arrayPegawai["nohp"]=$row["nohp"];
		$arrayPegawai["pendidikan"]=$row["pendidikan"];
		$arrayPegawai["jabatan"]=$row["jabatan"];
		$arrayPegawai["tgl_masuk"]=$row["tgl_masuk"];
		$arrayPegawai["profile"]="http://employees.esy.es/profile_images/".$row["profile_images"];
		$list[]=$arrayPegawai;
	}
	echo json_encode($list);

?>