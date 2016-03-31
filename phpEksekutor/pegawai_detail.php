<?php
include("koneksiDB.php");

$nik=$_GET["nik"];

$q = mysql_query( "SELECT 
          a.nik, a.nama, a.tmp_lhr, a.tgl_lhr, a.jenkel, a.agama, a.status, a.alamat, a.alamat_now, 
          a.nohp, a.phone, a.pendidikan, a.jabatan, a.tgl_masuk, a.nama_darurat, a.no_darurat, a.hubungan, a.batch, 
          b.location, a.profile_images, c.level, c.username, c.password
     FROM karyawan a LEFT JOIN batch_area b on a.batch = b.batch 
     LEFT JOIN login c on a.nik = c.username WHERE a.nik='$nik'");

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
		$arrayPegawai["alamat_now"]=$row["alamat_now"];
		$arrayPegawai["nohp"]=$row["nohp"];
		$arrayPegawai["phone"]=$row["phone"];
		$arrayPegawai["pendidikan"]=$row["pendidikan"];
		$arrayPegawai["jabatan"]=$row["jabatan"];
		$arrayPegawai["tgl_masuk"]=$row["tgl_masuk"];
		$arrayPegawai["nama_darurat"]=$row["nama_darurat"];
		$arrayPegawai["nomor_darurat"]=$row["no_darurat"];
		$arrayPegawai["hubungan"]=$row["hubungan"];
		$arrayPegawai["batch"]=$row["batch"];
                $arrayPegawai["location"]=$row["location"];
                $arrayPegawai["level"]=$row["level"];
                $arrayPegawai["username"]=$row["username"];
                $arrayPegawai["password"]=$row["password"];
		$arrayPegawai["profile"]="http://employees.esy.es/profile_images/".$row["profile_images"];
		$list[]=$arrayPegawai;
	}
	 $data = "{data:".json_encode($arrayPegawai)."}";
	echo $data;

?>