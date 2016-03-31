<?php
    include("koneksiDB.php");

   // array for JSON response
$response = array();
 
    $nik= $_POST['nik'];
    $kode= $_POST['kode'];
    $nilai= $_POST['nilai'];
    $tgl= $_POST['tgl'];
    $trainer= $_POST['trainer'];
 
        	$update = mysql_query("Update karyawan_training set nilai='$nilai', trainer='$trainer', tgl='$tgl' 
        		WHERE nik = '$nik' and kode='$kode'");
        		if($update){
        			$response["success"] = 1;
        			$response["message"] = "successfully update.";
 
			        // echoing JSON response
			        echo json_encode($response);
    			} else {
        			// failed to insert row
			        $response["success"] = 0;
			        $response["message"] = "Oops! An error occurred.";
			 
			        // echoing JSON response
			        echo json_encode($response);
    			}
    	?>	
