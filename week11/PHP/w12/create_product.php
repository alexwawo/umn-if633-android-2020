<?php

// array for JSON response
$response = array();

// check for required fields
if(isset($_POST['name']) && isset($_POST['price']) && isset($_POST['description'])){
	// buat koneksi
	$koneksi = mysqli_connect("localhost", "root", "", "android_w12");

	$name = $_POST['name'];
	$price = $_POST['price'];
	$description = $_POST['description'];

	$sql = "INSERT INTO products(name, price, description)
			VALUES('$name', '$price', '$description')";
	$result = mysqli_query($koneksi, $sql);

	// check if row inserted or not
	if($result){
		// successfully inserted into database
		$response['success'] = 1;
		$response['message'] = "Product successfully created.";
		// echoing JSON response
		echo json_encode($response);
	}else{
		// failed to insert row
		$response['success'] = 0;
		$response['message'] = "Oops! An error occured!";
		// echoing JSON response
		echo json_encode($response);
	}
}else{
	// required field is missing
	$response['success'] = 0;
	$response['message'] = "Required field(s) is missing!";
	// echoing JSON response
	echo json_encode($response);
}