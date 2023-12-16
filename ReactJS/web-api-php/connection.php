<?php
$databaseHost = '127.0.0.1:3306';
$databaseName = 'web_api_php_MD18101';
$databaseUsername = 'root';
$databasePassword = 'WASERE12345@';

try {
	$dbConn = new PDO("mysql:host={$databaseHost};dbname={$databaseName}", 
						$databaseUsername, $databasePassword);
	$dbConn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
	echo $e->getMessage();
	/* 
		cd web-api-php
	 	php -S 127.0.0.1:8686 
			*/
}