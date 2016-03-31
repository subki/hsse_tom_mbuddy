<?php
    include("koneksiDB.php");

    $nik=$_GET["nik"];

    $query = "SELECT * FROM karyawan WHERE nik= '$nik'";
    $result = mysql_query($query) or die("Unable to verify user because : " . mysql_error());
    $arrayLoc= array();
    if (mysql_num_rows($result) == 0){
        $arrayLoc["result"]="0";
    }else{
        $arrayLoc["result"]="1";
    }
    $data = "{data:".json_encode($arrayLoc)."}";
    echo $data;
?>