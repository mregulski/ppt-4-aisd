<?php
$Header =<<<EOT
<!DOCTYPE html>
<html lang="pl">
<head>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=content-width, initial-scale=1" />
	<title>{{Title}}</title>
	<link rel="stylesheet" href="styles/reset.css"/>
	<link rel="stylesheet" href="styles/grid.css"/>
	<link rel="stylesheet" href="styles/algorytm.css"/>
	{{Styles}}
	<script src="scripts/menu.js"></script>
	{{Scripts}}

</head>
<body>
<header>
	<div class="row space-after">
		<div id="logo">
			<img src="res/infppt.png" alt="logo wydziału"/>

			<h1> Podstawowe algorytmy</h1>
		</div>
	</div>
	<p class="space-after">Na tych stronach omówione są najciekawsze algorytmy omawiane na wykładzie ze Wstępu do Informatyki
	na pierwszym roku studiów informatycznych na Wydziale PPT PWr.</p>
</header>
<div id="menuToggle">&#9776; Menu</div>
EOT;
?>
